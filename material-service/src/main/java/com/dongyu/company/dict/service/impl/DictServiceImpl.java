package com.dongyu.company.dict.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.ChargeTypeEnum;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.constants.ProcurementTypeEnum;
import com.dongyu.company.common.constants.UsageStateEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.dict.dao.StaticDataDao;
import com.dongyu.company.dict.dao.StaticDataSpecs;
import com.dongyu.company.dict.domain.StaticData;
import com.dongyu.company.dict.dto.DictDTO;
import com.dongyu.company.dict.dto.DictResultDTO;
import com.dongyu.company.dict.dto.StaticDataDTO;
import com.dongyu.company.dict.dto.StaticQueryDTO;
import com.dongyu.company.dict.service.DictService;
import com.dongyu.company.order.dao.OrderTemplateSpecs;
import com.dongyu.company.order.domain.OrderTemplate;
import com.dongyu.company.order.dto.OrderTemplateListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public void add(StaticDataDTO dto) {
        log.info("DictServiceImpl add method start：" + JSONObject.toJSONString(dto));
        StaticData oldStatic = staticDataDao.findByCodeTypeAndCodeKeyAndDeleted(dto.getCodeType(), dto.getCodeKey(), DeletedEnum.DELETED.getValue());
        if (oldStatic != null) {
            throw new BizException("该列表数据已存在！");
        }
        StaticData staticData = new StaticData();
        BeanUtils.copyProperties(dto, staticData);
        staticDataDao.save(staticData);
        log.info("DictServiceImpl add method end");
    }

    @Override
    public PageDTO<StaticDataDTO> getlist(StaticQueryDTO queryDTO) {
        log.info("DictServiceImpl getlist method start：" + JSONObject.toJSONString(queryDTO));
        PageRequest pageRequest = new PageRequest(queryDTO.getPageNo() - 1, queryDTO.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<StaticData> page = staticDataDao.findAll(StaticDataSpecs.staticDataSpec(queryDTO), pageRequest);
        PageDTO<StaticDataDTO> pageDTO = PageDTO.of(page, item -> {
            StaticDataDTO staticDataDTO = new StaticDataDTO();
            BeanUtils.copyProperties(item, staticDataDTO);
            return staticDataDTO;
        });
        log.info("DictServiceImpl getlist method end");
        return pageDTO;
    }

    @Override
    public void deleted(Long id) {
        log.info("DictServiceImpl method deleted start:" + id);
        StaticData staticData = staticDataDao.findOne(id);
        if (staticData == null) {
            throw new BizException("不存在该列表数据id");
        }
        staticData.setDeleted(DeletedEnum.DELETED.getValue());
        staticDataDao.save(staticData);
        log.info("DictServiceImpl method deleted end;");
    }

    @Override
    public void recovery(Long id) {
        log.info("DictServiceImpl method recovery start:" + id);
        StaticData staticData = staticDataDao.findOne(id);
        if (staticData == null) {
            throw new BizException("不存在该列表数据id");
        }
        StaticData oldStatic = staticDataDao.findByCodeTypeAndCodeKeyAndDeleted(staticData.getCodeType(), staticData.getCodeKey(), DeletedEnum.DELETED.getValue());
        if (oldStatic != null) {
            throw new BizException("该列表数据已存在！");
        }
        staticData.setDeleted(DeletedEnum.UNDELETED.getValue());
        staticDataDao.save(staticData);
        log.info("DictServiceImpl method recovery end;");
    }


}
