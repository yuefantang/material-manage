package com.dongyu.company.register.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.file.dao.FileDao;
import com.dongyu.company.file.domian.CommonFile;
import com.dongyu.company.register.dao.ProcessDao;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.domain.MiProcess;
import com.dongyu.company.register.domain.MiRegister;
import com.dongyu.company.register.dto.AddProcessDTO;
import com.dongyu.company.register.dto.AddRegisterDTO;
import com.dongyu.company.register.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void add(AddRegisterDTO dto) {
        log.info("RegisterServiceImpl add method start Parm:" + JSONObject.toJSONString(dto));
        MiRegister byMiDyCode = registerDao.findByMiDyCode(dto.getMiDyCode());
        //根据DY编号去重
        if (byMiDyCode != null) {
            throw new BizException("已存在该DY编号的MI");
        }
        //获取上传图片
        CommonFile commonFile = fileDao.findOne(dto.getCommonFileId());
        //存储MI登记
        MiRegister miRegister = new MiRegister();
        BeanUtils.copyProperties(dto, miRegister);
        //开模日期
        miRegister.setOpenMoldDate(DateUtil.parseStrToDate(dto.getOpenMoldDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //样板确认日期
        miRegister.setConfirmDate(DateUtil.parseStrToDate(dto.getConfirmDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //建档日期
        miRegister.setRecordDate(DateUtil.parseStrToDate(dto.getRecordDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //更改日期
        miRegister.setChangeDate(DateUtil.parseStrToDate(dto.getChangeDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //关联图片
        miRegister.setCommonFile(commonFile);
        MiRegister save = registerDao.save(miRegister);

        //存储MI登记的工序
        List<AddProcessDTO> processDTOS = dto.getProcessDTOS();
        List<MiProcess> processList = processDTOS.stream().map(addProcessDTO -> {
            MiProcess miProcess = new MiProcess();
            BeanUtils.copyProperties(addProcessDTO, miProcess);
            miProcess.setMiRegister(save);
            return miProcess;
        }).collect(Collectors.toList());
        processDao.save(processList);
    }


}
