package com.dongyu.company.register.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.file.dao.FileDao;
import com.dongyu.company.file.domian.CommonFile;
import com.dongyu.company.file.service.FileService;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.register.dao.ProcessDao;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.dao.RegisterSpecs;
import com.dongyu.company.register.domain.MiProcess;
import com.dongyu.company.register.domain.MiRegister;
import com.dongyu.company.register.dto.AddProcessDTO;
import com.dongyu.company.register.dto.AddRegisterDTO;
import com.dongyu.company.register.dto.EditProcessDTO;
import com.dongyu.company.register.dto.RegisterDetailDTO;
import com.dongyu.company.register.dto.RegisterListDTO;
import com.dongyu.company.register.dto.RegisterQueryDTO;
import com.dongyu.company.register.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MI登记ServiceImpl
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private ProcessDao processDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional
    public void add(AddRegisterDTO dto) {
        log.info("RegisterServiceImpl add method start Parm:" + JSONObject.toJSONString(dto));
        MiRegister byMiDyCode = registerDao.findByMiDyCode(dto.getMiDyCode());
        //根据DY编号去重
        if (byMiDyCode != null) {
            throw new BizException("已存在该DY编号的MI");
        }
        //存储MI登记
        MiRegister miRegister = new MiRegister();
        BeanUtils.copyProperties(dto, miRegister);
        miRegister.setProduction(Integer.valueOf(dto.getProduction()));
        //开模日期
        miRegister.setOpenMoldDate(DateUtil.parseStrToDate(dto.getOpenMoldDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //样板确认日期
        miRegister.setConfirmDate(DateUtil.parseStrToDate(dto.getConfirmDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //建档日期
        miRegister.setRecordDate(DateUtil.parseStrToDate(dto.getRecordDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //更改日期
        miRegister.setChangeDate(DateUtil.parseStrToDate(dto.getChangeDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //获取上传图片
        if (dto.getCommonFileId() != null) {
            //关联图片
            miRegister.setCommonFileId(dto.getCommonFileId());
        }else{
            throw new BizException("图片不能为空");
        }
        MiRegister save = registerDao.save(miRegister);

        //存储MI登记的工序
        List<AddProcessDTO> processDTOS = dto.getProcessDTOS();
        if (CollectionUtils.isEmpty(processDTOS)) {
            throw new BizException("工序不能为空");
        }
        List<MiProcess> processList = processDTOS.stream().map(addProcessDTO -> {
            MiProcess miProcess = new MiProcess();
            BeanUtils.copyProperties(addProcessDTO, miProcess);
            miProcess.setMiRegisterId(save.getId());
            return miProcess;
        }).collect(Collectors.toList());
        processDao.save(processList);
    }

    @Override
    public PageDTO<RegisterListDTO> getlist(RegisterQueryDTO dto) {
        log.info("RegisterServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<MiRegister> page = registerDao.findAll(RegisterSpecs.registerQuerySpec(dto), pageRequest);

        PageDTO<RegisterListDTO> pageDTO = PageDTO.of(page, item -> {
            RegisterListDTO registerListDTO = new RegisterListDTO();
            BeanUtils.copyProperties(item, registerListDTO);
            //开模日期
            registerListDTO.setOpenMoldDate(DateUtil.parseDateToStr(item.getOpenMoldDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //样板确认日期
            registerListDTO.setConfirmDate(DateUtil.parseDateToStr(item.getConfirmDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //建档日期
            registerListDTO.setRecordDate(DateUtil.parseDateToStr(item.getRecordDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return registerListDTO;
        });
        return pageDTO;
    }

    @Override
    public List<RegisterDetailDTO> getExportList(RegisterQueryDTO registerQueryDTO) {
        log.info("RegisterServiceImpl getExportList method start Parm:" + JSONObject.toJSONString(registerQueryDTO));
        List<MiRegister> miRegisterList = registerDao.findAll(RegisterSpecs.registerQuerySpec(registerQueryDTO));
        if (CollectionUtils.isEmpty(miRegisterList)) {
            return null;
        }
        List<RegisterDetailDTO> detailDTOList = miRegisterList.stream().map(miRegister -> {
            RegisterDetailDTO registerDetailDTO = new RegisterDetailDTO();
            BeanUtils.copyProperties(miRegister, registerDetailDTO);
            //开模日期
            registerDetailDTO.setOpenMoldDate(DateUtil.parseDateToStr(miRegister.getOpenMoldDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //样板确认日期
            registerDetailDTO.setConfirmDate(DateUtil.parseDateToStr(miRegister.getConfirmDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //建档日期
            registerDetailDTO.setRecordDate(DateUtil.parseDateToStr(miRegister.getRecordDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return registerDetailDTO;
        }).collect(Collectors.toList());
        return detailDTOList;
    }

    @Override
    @Transactional//当加入事务的时候，日志记录获取原数据有问题
    public void edit(RegisterDetailDTO editRegisterDTO) {
        log.info("RegisterServiceImpl edit method start Parm:" + JSONObject.toJSONString(editRegisterDTO));
        MiRegister miRegister = registerDao.findById(editRegisterDTO.getId());
        if (miRegister == null) {
            throw new BizException("不存在该MI登记记录");
        }
        //判断DY编号是否修改
        if (!miRegister.getMiDyCode().equals(editRegisterDTO.getMiDyCode())) {
            MiRegister byMiDyCode = registerDao.findByMiDyCode(editRegisterDTO.getMiDyCode());
            //根据DY编号去重
            if (byMiDyCode != null) {
                throw new BizException("已存在该DY编号的MI");
            }
        }
        //修改MI登记表数据
        BeanUtils.copyProperties(editRegisterDTO, miRegister);
        entityManager.detach(miRegister);//从持久性上下文中移除给定的实体，导致托管实体分离
        //开模日期
        miRegister.setOpenMoldDate(DateUtil.parseStrToDate(editRegisterDTO.getOpenMoldDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //样板确认日期
        miRegister.setConfirmDate(DateUtil.parseStrToDate(editRegisterDTO.getConfirmDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //建档日期
        miRegister.setRecordDate(DateUtil.parseStrToDate(editRegisterDTO.getRecordDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        miRegister.setProduction(Integer.valueOf(editRegisterDTO.getProduction()));
        //更改日期
        miRegister.setChangeDate(DateUtil.parseStrToDate(editRegisterDTO.getChangeDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //获取上传图片
        if (editRegisterDTO.getCommonFileId() != null) {
            //关联图片
            miRegister.setCommonFileId(editRegisterDTO.getCommonFileId());
        }else{
            throw new BizException("图片不能为空");
        }
        registerDao.save(miRegister);

        //修改MI登记下的工序
        List<EditProcessDTO> processDTOS = editRegisterDTO.getProcessDTOS();
        if (CollectionUtils.isEmpty(processDTOS)) {
            throw new BizException("工序不能为空");
        }
        processDao.deletedByMiRegisterId(miRegister.getId());

        List<MiProcess> processList = processDTOS.stream().map(editProcessDTO -> {
            MiProcess miProcess = new MiProcess();
            miProcess.setMiRegisterId(miRegister.getId());
            miProcess.setProcess(editProcessDTO.getProcess());
            miProcess.setRemark(editProcessDTO.getRemark());
            miProcess.setOrderNumber(editProcessDTO.getOrderNumber());
            return miProcess;
        }).collect(Collectors.toList());
        processDao.save(processList);

    }

    @Override
    @Transactional
    public void deleted(Long id) {
        log.info("RegisterServiceImpl deleted method start Parm:" + id);
        //已经下单的数据不能删除
        List<Order> orderList = orderDao.findByMiRegisterIdAndDeleted(id, DeletedEnum.UNDELETED.getValue());
        if (CollectionUtils.isNotEmpty(orderList)) {
            throw new BizException("该MI已经下单，无法删除");
        }
        MiRegister miRegister = registerDao.findOne(id);
        if (miRegister == null) {
            throw new BizException("不存在该MI登记，无法删除");
        }

        //判段是否是第二次删除，如果是再次删除为物理删除
        Integer deleted = miRegister.getDeleted();
        if (deleted == DeletedEnum.UNDELETED.getValue()) {
            //删除MI登记
            miRegister.setDeleted(DeletedEnum.DELETED.getValue());
            registerDao.save(miRegister);
        } else {
            //删除MI登记相关的工序
            processDao.deletedByMiRegisterId(miRegister.getId());
            registerDao.delete(id);
        }
        //删除图片
//        if (miRegister.getCommonFileId() != null) {
//            CommonFile commonFile = fileDao.findOne(miRegister.getCommonFileId());
//            if (commonFile != null) {
//                if (commonFile.getId() != null) {
//                    Boolean delfile = fileService.delfile(commonFile.getId());
//                    if (!delfile) {
//                        throw new BizException("图片删除失败！");
//                    }
//                }
//            }
//        }

        log.info("RegisterServiceImpl deleted method end;");
    }

    @Override
    @Transactional
    public void recovery(Long id) {
        log.info("RegisterServiceImpl recovery method start Parm:" + id);
        MiRegister miRegister = registerDao.findOne(id);
        if (miRegister == null) {
            throw new BizException("不存在该MI登记，无法恢复");
        }
        miRegister.setDeleted(DeletedEnum.UNDELETED.getValue());
        registerDao.save(miRegister);
        log.info("RegisterServiceImpl recovery method end;");
    }

    @Override
    public RegisterDetailDTO getDetail(Long id) {
        log.info("RegisterServiceImpl getDetail method start Parm:" + id);
        MiRegister miRegister = registerDao.findOne(id);
        if (miRegister == null) {
            throw new BizException("不存在该MI登记");
        }

        RegisterDetailDTO registerDetailDTO = new RegisterDetailDTO();
        BeanUtils.copyProperties(miRegister, registerDetailDTO);
        registerDetailDTO.setProduction(miRegister.getProduction().toString());
        //处理时间格式
        //开模日期
        registerDetailDTO.setOpenMoldDate(DateUtil.parseDateToStr(miRegister.getOpenMoldDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //样板确认日期
        registerDetailDTO.setConfirmDate(DateUtil.parseDateToStr(miRegister.getConfirmDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //建档日期
        registerDetailDTO.setRecordDate(DateUtil.parseDateToStr(miRegister.getRecordDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //更改日期
        if (miRegister.getChangeDate() != null) {
            registerDetailDTO.setChangeDate(DateUtil.parseDateToStr(miRegister.getChangeDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        }
        //单片尺寸
       // registerDetailDTO.setSingleSize(String.valueOf(miRegister.getSingleSizeLength()) + "*" + String.valueOf(miRegister.getSingleSizeWide()));
        //模片尺寸
        //registerDetailDTO.setDieSize(String.valueOf(miRegister.getDieSizeLength()) + "*" + String.valueOf(miRegister.getDieSizeWide()));
        //返回图片信息
        if (miRegister.getCommonFileId() != null) {
            CommonFile commonFile = fileDao.findOne(miRegister.getCommonFileId());
            if (commonFile != null) {
                //registerDetailDTO.setFilePath(commonFile.getFilePath());
                registerDetailDTO.setFileName(commonFile.getFileName());
                registerDetailDTO.setCommonFileId(commonFile.getId());
            }
        }
        //返回MI登记相关从工序
        List<MiProcess> processList = processDao.findByMiRegisterId(miRegister.getId());
        List<EditProcessDTO> processDTOS = processList.stream().map(miProcess -> {
            EditProcessDTO processDTO = new EditProcessDTO();
            BeanUtils.copyProperties(miProcess, processDTO);
            return processDTO;
        }).collect(Collectors.toList());
        registerDetailDTO.setProcessDTOS(processDTOS);
        return registerDetailDTO;
    }

    @Override
    public RegisterDetailDTO getRegisterByDyCode(String miDyCode) {
        log.info("RegisterServiceImpl getRegisterByDyCode method start Parm:" + miDyCode);
        MiRegister miRegister = registerDao.findByMiDyCodeAndDeleted(miDyCode, DeletedEnum.UNDELETED.getValue());
        if (miRegister == null) {
            throw new BizException("不存在该MI登记");
        }
        RegisterDetailDTO registerDetailDTO = new RegisterDetailDTO();
        BeanUtils.copyProperties(miRegister, registerDetailDTO);


        log.info("RegisterServiceImpl getRegisterByDyCode method end ");
        return registerDetailDTO;

    }
}
