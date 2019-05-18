package com.dongyu.company.dict.service.impl;

import com.dongyu.company.common.constants.ChargeTypeEnum;
import com.dongyu.company.common.constants.ProcurementTypeEnum;
import com.dongyu.company.common.constants.UsageStateEnum;
import com.dongyu.company.dict.dao.StaticDataDao;
import com.dongyu.company.dict.domain.StaticData;
import com.dongyu.company.dict.dto.DictDTO;
import com.dongyu.company.dict.dto.DictResultDTO;
import com.dongyu.company.dict.dto.StaticDataDTO;
import com.dongyu.company.dict.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典相关Service实现类
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private StaticDataDao staticDataDao;

    @Override
    public DictResultDTO getDictList() {
        log.info("DictServiceImpl getDictList methond start:");
        DictResultDTO dictResultDTO = new DictResultDTO();

        //采购种类
        List<DictDTO> procurementTypeList = new ArrayList<>();
        for (ProcurementTypeEnum procurementTypeEnum : ProcurementTypeEnum.values()) {
            DictDTO dictDTO = new DictDTO();
            dictDTO.setKey(procurementTypeEnum.getValue());
            dictDTO.setValue(procurementTypeEnum.getDesc());
            procurementTypeList.add(dictDTO);
        }
        dictResultDTO.setProcurementTypeEnum(procurementTypeList);

        //模具使用状态
        List<DictDTO> usageStateList = new ArrayList<>();
        for (UsageStateEnum usageStateEnum : UsageStateEnum.values()) {
            DictDTO dictDTO = new DictDTO();
            dictDTO.setKey(usageStateEnum.getValue());
            dictDTO.setValue(usageStateEnum.getDesc());
            usageStateList.add(dictDTO);
        }
        dictResultDTO.setUsageStateEnum(usageStateList);

        //其它收费开单的类型
        List<DictDTO> chargeTypeList = new ArrayList<>();
        for (ChargeTypeEnum chargeTypeEnum : ChargeTypeEnum.values()) {
            DictDTO dictDTO = new DictDTO();
            dictDTO.setKey(chargeTypeEnum.getValue());
            dictDTO.setValue(chargeTypeEnum.getDesc());
            chargeTypeList.add(dictDTO);
        }
        dictResultDTO.setChargeTypeEnum(chargeTypeList);

        log.info("DictServiceImpl getDictList methond end");
        return dictResultDTO;
    }

    @Override
    public Map<String, List<StaticDataDTO>> getStaticData() {
        log.info("DictServiceImpl getStaticData methond start:");
        List<StaticData> staticDataList = staticDataDao.findAll();
        List<StaticDataDTO> dtoList = staticDataList.stream().map(staticData -> {
            StaticDataDTO staticDataDTO = new StaticDataDTO();
            BeanUtils.copyProperties(staticData, staticDataDTO);
            return staticDataDTO;
        }).collect(Collectors.toList());
        Map<String, List<StaticDataDTO>> map = dtoList.stream().collect(Collectors.groupingBy(StaticDataDTO::getCodeType));
        log.info("DictServiceImpl getStaticData methond end");
        return map;
    }
}
