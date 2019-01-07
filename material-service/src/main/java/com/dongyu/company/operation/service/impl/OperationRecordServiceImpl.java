package com.dongyu.company.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.operation.dao.OperationRecordDao;
import com.dongyu.company.operation.domain.OperationRecord;
import com.dongyu.company.operation.dto.OperationQueryDTO;
import com.dongyu.company.operation.dto.OperationRecordTDO;
import com.dongyu.company.operation.service.OperationRecordService;
import com.dongyu.company.user.dao.UserDao;
import com.dongyu.company.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志管理service实现类
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
@Slf4j
@Service
public class OperationRecordServiceImpl implements OperationRecordService {

    @Autowired
    private OperationRecordDao operationRecordDao;
    @Autowired
    private UserDao userDao;


    @Override
    public List<OperationRecordTDO> findOperation(OperationQueryDTO dto) {
        log.info("OperationRecordServiceImpl findOperation method start Parm:" + JSONObject.toJSONString(dto));
        //查询日志记录
        List<OperationRecord> recordList = operationRecordDao.findByEntityAndEntityIdOrderByCreateTimeDesc(dto.getEntity(), dto.getEntityId());
        if (CollectionUtils.isEmpty(recordList)) {
            return null;
        }
        List<OperationRecordTDO> collect = recordList.stream().map(operationRecord -> {
            OperationRecordTDO recordTDO = new OperationRecordTDO();
            BeanUtils.copyProperties(operationRecord, recordTDO);
            recordTDO.setUpdateTime(DateUtil.parseDateToStr(operationRecord.getUpdateTime(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            User user = userDao.findOneById(operationRecord.getUpdateBy());
            recordTDO.setOperationName(user.getUserName());
            return recordTDO;
        }).collect(Collectors.toList());
        log.info("OperationRecordServiceImpl findOperation method end;");
        return collect;
    }
}
