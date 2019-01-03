package com.dongyu.company.dict.service.impl;

import com.dongyu.company.common.constants.ProcurementTypeEnum;
import com.dongyu.company.common.constants.UsageStateEnum;
import com.dongyu.company.dict.dto.DictDTO;
import com.dongyu.company.dict.dto.DictResultDTO;
import com.dongyu.company.dict.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<DictDTO> UsageStateList = new ArrayList<>();
        for (UsageStateEnum usageStateEnum : UsageStateEnum.values()) {
            DictDTO dictDTO = new DictDTO();
            dictDTO.setKey(usageStateEnum.getValue());
            dictDTO.setValue(usageStateEnum.getDesc());
            UsageStateList.add(dictDTO);
        }
        dictResultDTO.setProcurementTypeEnum(UsageStateList);
        log.info("DictServiceImpl getDictList methond end");
        return dictResultDTO;
    }
}
