package com.dongyu.company.common.aspect;

import com.dongyu.company.common.annotation.AttributeOpName;
import com.dongyu.company.common.annotation.AttributeOpRecord;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.operation.dao.OperationRecordDao;
import com.dongyu.company.operation.domain.OperationRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 记录修改属性操作日志切面
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
@Aspect
@Configuration
@Slf4j
public class AttributeOpAspect {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OperationRecordDao operationRecordDao;

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.dongyu.company.*.dao.*Dao.save(..))")
    public void insertCell() {
    }

    @Around("insertCell()")
    public Object addOperationRecordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("AttributeOpAspect addOperationRecordLog method start:");
        if (joinPoint.getArgs().length > 1) {
            return null;
        }
        Object updateEntity = joinPoint.getArgs()[0];
        Class<?> updateEntityClass = updateEntity.getClass();
        AttributeOpRecord annotation = updateEntity.getClass().getAnnotation(AttributeOpRecord.class);
        if (annotation == null) {
            return joinPoint.proceed(joinPoint.getArgs());
        }

        Long entityId = null;//要保存的表数据id
        Object oldObject = null;
        Object newObject;

        Object entity = PropertyUtils.getProperty(updateEntity, Constants.ID);
        if (entity != null) {//不为空代表数据库已经存在该数据，查找出数据库原数据
            entityId = Long.valueOf(entity.toString());
            oldObject = this.getObjectById(joinPoint.getTarget(), entityId);
        }
        newObject = joinPoint.proceed(joinPoint.getArgs());//环绕通知执行proceed方法的作用是让目标方法执行
        if (entityId == null) {//为新增操作
            entityId = Long.valueOf(PropertyUtils.getProperty(updateEntity, Constants.ID).toString());//得到新数据入口返回的id
            this.addOperation(updateEntityClass, entityId);
        } else {//为编辑，删除，或恢复操作
            Field[] fields = updateEntityClass.getDeclaredFields();

            List<String> newValueList = new ArrayList();
            List<String> oldValueList = new ArrayList();
            List<String> attributeList = new ArrayList();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), updateEntityClass);
                // 获取对应属性值
                Method getMethod = pd.getReadMethod();
                String oldValue = getValue(getMethod.invoke(oldObject));
                String newValue = getValue(getMethod.invoke(newObject));
                if (oldValue == null || newValue == null) {
                    continue;
                }
                if (!oldValue.equals(newValue)) {
                    AttributeOpName annotation1 = field.getAnnotation(AttributeOpName.class);
                    if (annotation1 != null) {
                        String value = annotation1.value();//属性中文名称
                        attributeList.add(value);
                    }
                    newValueList.add(newValue);
                    oldValueList.add(oldValue);
                }
            }
            String attribute = String.join(",", attributeList);
            String newValue = String.join(",", newValueList);
            String oldValue = String.join(",", oldValueList);
            this.editOperation(updateEntityClass, entityId, attribute, newValue, oldValue);
        }
        log.info("AttributeOpAspect addOperationRecordLog method end;");
        return null;
    }

    /**
     * 通过反射执行findOne操作获取原数据
     *
     * @param target save方法所属类
     * @param id     查询数据id
     * @return
     * @throws Exception
     */
    public Object getObjectById(Object target, Serializable id) throws Exception {
        log.info("AttributeOpAspect getObjectById method start:");
        entityManager.getEntityManagerFactory().getCache().evictAll();
        Class<?> targetClass = target.getClass();
        Method findOne = target.getClass().getDeclaredMethod("getOne", Serializable.class);
        Object result = findOne.invoke(target, id);
        if (result instanceof Optional) {
            return ((Optional) result).get();
        }
        log.info("AttributeOpAspect getObjectById method end;");
        return result;
    }

    /**
     * 新增操作只需直接记录操作表，类型，id
     *
     * @param entityClass
     * @param entityId
     */
    private void addOperation(Class entityClass, Long entityId) {
        log.info("AttributeOpAspect addOperation method start:");
        OperationRecord record = new OperationRecord();
        record.setEntity(entityClass.getSimpleName());
        record.setOperationType(Constants.SAVE);
        record.setEntityId(entityId);
        operationRecordDao.save(record);
        log.info("AttributeOpAspect addOperation method end;");
    }

    /**
     * 编辑操作只需直接记录操作表，类型，id
     *
     * @param entityClass
     * @param entityId
     */
    private void editOperation(Class entityClass, Long entityId, String attribute, String newValue, String oldValue) {
        log.info("AttributeOpAspect editOperation method start:");
        OperationRecord record = new OperationRecord();
        record.setEntity(entityClass.getSimpleName());
        record.setEntityId(entityId);
        if (attribute.equals("删除")) {//判断是否是删除或恢复操作
            if (oldValue.equals("0")) {//为删除操作
                record.setOperationType(Constants.DELETE);
            } else {//为恢复操作
                record.setOperationType(Constants.RECOVERY);
            }

        } else {
            record.setOperationType(Constants.UPDATE);
            record.setNewValue(newValue);
            record.setOldValue(oldValue);
            record.setAttribute(attribute);
        }
        operationRecordDao.save(record);
        log.info("AttributeOpAspect editOperation method end;");
    }

    /**
     * 处理时间数据
     *
     * @param obj
     * @return
     */
    private String getValue(Object obj) {
        if (obj != null) {
            if (obj instanceof Date) {
                return DateUtil.parseDateToStr((Date) obj, DateUtil.DATE_FORMAT_YYYY_MM_DD);
            } else {
                return obj.toString();
            }
        } else {
            return StringUtils.EMPTY;
        }
    }

}
