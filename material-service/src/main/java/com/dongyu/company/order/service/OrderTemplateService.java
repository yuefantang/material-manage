package com.dongyu.company.order.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.mould.dto.AddMouldDTO;
import com.dongyu.company.mould.dto.EditMouldDTO;
import com.dongyu.company.mould.dto.MouldDetailDTO;
import com.dongyu.company.mould.dto.MouldListDTO;
import com.dongyu.company.mould.dto.MouldQueryDTO;
import com.dongyu.company.order.dto.OrderTemplateDTO;
import com.dongyu.company.order.dto.OrderTemplateListDTO;
import com.dongyu.company.order.dto.OrderTemplateQueryDTO;

import java.util.List;

/**
 * 样板Service
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
public interface OrderTemplateService {

    //新增样板
    void add(OrderTemplateDTO orderTemplateDTO);

    //分页查询样板
    PageDTO<OrderTemplateListDTO> getlist(OrderTemplateQueryDTO queryDTO);

    //导出查询样板
    List<OrderTemplateDTO> getExportList(OrderTemplateQueryDTO queryDTO);

    //修改样板
    void edit(OrderTemplateListDTO dto);

    /**
     * 删除样板
     *
     * @param id 样板id
     */
    void deleted(Long id);

    /**
     * 恢复删除样板
     *
     * @param id 样板id
     */
    void recovery(Long id);

    /**
     * 样板详情
     *
     * @param id 样板id
     * @return
     */
    OrderTemplateListDTO getDetail(Long id);
}
