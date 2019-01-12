package com.dongyu.company.order.service.impl;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.order.dao.OrderTemplateDao;
import com.dongyu.company.order.dao.OrderTemplateSpecs;
import com.dongyu.company.order.domain.OrderTemplate;
import com.dongyu.company.order.dto.OrderTemplateDTO;
import com.dongyu.company.order.dto.OrderTemplateListDTO;
import com.dongyu.company.order.dto.OrderTemplateQueryDTO;
import com.dongyu.company.order.service.OrderTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 样板Service实现
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@Slf4j
@Service
public class OrderTemplateServiceImpl implements OrderTemplateService {

    @Autowired
    private OrderTemplateDao orderTemplateDao;

    @Override
    public void add(OrderTemplateDTO orderTemplateDTO) {
        log.info("OrderTemplateServiceImpl method add start:");
        //根据DY编号去重
        OrderTemplate byTemplateCode = orderTemplateDao.findByDyCode(orderTemplateDTO.getDyCode());
        if (byTemplateCode != null) {
            throw new BizException("DY编号已存在,请重新输入");
        }
        OrderTemplate orderTemplate = new OrderTemplate();
        BeanUtils.copyProperties(orderTemplateDTO, orderTemplate);
        orderTemplate.setTemplateDeliveryDate(DateUtil.parseStrToDate(orderTemplateDTO.getTemplateDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        orderTemplateDao.save(orderTemplate);
        log.info("OrderTemplateServiceImpl method add end;");
    }

    @Override
    public PageDTO<OrderTemplateListDTO> getlist(OrderTemplateQueryDTO queryDTO) {
        log.info("OrderTemplateServiceImpl method getlist start:");
        PageRequest pageRequest = new PageRequest(queryDTO.getPageNo() - 1, queryDTO.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<OrderTemplate> page = orderTemplateDao.findAll(OrderTemplateSpecs.orederTemplateSpec(queryDTO), pageRequest);
        PageDTO<OrderTemplateListDTO> pageDTO = PageDTO.of(page, item -> {
            OrderTemplateListDTO templateListDTO = new OrderTemplateListDTO();
            BeanUtils.copyProperties(item, templateListDTO);
            templateListDTO.setTemplateDeliveryDate(DateUtil.parseDateToStr(item.getTemplateDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return templateListDTO;
        });
        log.info("OrderTemplateServiceImpl method getlist end;");
        return pageDTO;
    }

    @Override
    public List<OrderTemplateDTO> getExportList(OrderTemplateQueryDTO queryDTO) {
        log.info("OrderTemplateServiceImpl method getExportList start:");
        List<OrderTemplate> orderTemplates = orderTemplateDao.findAll(OrderTemplateSpecs.orederTemplateSpec(queryDTO));
        if (CollectionUtils.isEmpty(orderTemplates)) {
            return null;
        }
        List<OrderTemplateDTO> detailDTOList = orderTemplates.stream().map(orderTemplate -> {
            OrderTemplateDTO orderTemplateDTO = new OrderTemplateDTO();
            BeanUtils.copyProperties(orderTemplate, orderTemplateDTO);
            orderTemplateDTO.setTemplateDeliveryDate(DateUtil.parseDateToStr(orderTemplate.getTemplateDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return orderTemplateDTO;
        }).collect(Collectors.toList());
        log.info("OrderTemplateServiceImpl method getExportList end;");
        return detailDTOList;
    }

    @Override
    public void edit(OrderTemplateListDTO dto) {
        log.info("OrderTemplateServiceImpl method edit start:");
        OrderTemplate oldOrderTemplate = orderTemplateDao.findOne(dto.getId());
        //DY编号修改则根据DY编号去重
        String oldDyCode = oldOrderTemplate.getDyCode();
        if (!dto.getDyCode().equals(oldDyCode)) {
            OrderTemplate orderTemplate = orderTemplateDao.findByDyCode(dto.getDyCode());
            if (orderTemplate != null) {
                throw new BizException("DY编号已存在,请重新输入");
            }
        }
        BeanUtils.copyProperties(dto, oldOrderTemplate);
        orderTemplateDao.save(oldOrderTemplate);
        log.info("OrderTemplateServiceImpl method edit end;");

    }

    @Override
    public void deleted(Long id) {
        log.info("OrderTemplateServiceImpl method deleted start:" + id);
        OrderTemplate orderTemplate = orderTemplateDao.findOne(id);
        if (orderTemplate == null) {
            throw new BizException("不存在该样板id");
        }
        orderTemplate.setDeleted(DeletedEnum.DELETED.getValue());
        orderTemplateDao.save(orderTemplate);
        log.info("OrderTemplateServiceImpl method deleted end;");
    }

    @Override
    public void recovery(Long id) {
        log.info("OrderTemplateServiceImpl method recovery start:" + id);
        OrderTemplate orderTemplate = orderTemplateDao.findOne(id);
        if (orderTemplate == null) {
            throw new BizException("不存在该样板id");
        }
        orderTemplate.setDeleted(DeletedEnum.UNDELETED.getValue());
        orderTemplateDao.save(orderTemplate);
        log.info("OrderTemplateServiceImpl method recovery end;");
    }

    @Override
    public OrderTemplateListDTO getDetail(Long id) {
        log.info("OrderTemplateServiceImpl method getDetail start:" + id);
        OrderTemplate orderTemplate = orderTemplateDao.findOne(id);
        if (orderTemplate == null) {
            throw new BizException("不存在该样板id");
        }
        OrderTemplateListDTO detailDTO = new OrderTemplateListDTO();
        BeanUtils.copyProperties(orderTemplate, detailDTO);
        detailDTO.setTemplateDeliveryDate(DateUtil.parseDateToStr(orderTemplate.getTemplateDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        log.info("OrderTemplateServiceImpl method getDetail end;");
        return null;
    }
}
