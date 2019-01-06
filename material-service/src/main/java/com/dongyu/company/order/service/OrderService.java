package com.dongyu.company.order.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.order.dto.AddOrderDTO;
import com.dongyu.company.order.dto.AddOrderResultDTO;
import com.dongyu.company.order.dto.AddSurplusDTO;
import com.dongyu.company.order.dto.EditOrderDTO;
import com.dongyu.company.order.dto.OrderDetailDTO;
import com.dongyu.company.order.dto.OrderListDTO;
import com.dongyu.company.order.dto.OrderQueryDTO;

import java.util.List;

/**
 * 下单Service
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
public interface OrderService {

    /**
     * 新增下单
     *
     * @param addOrderDTO
     */
    AddOrderResultDTO add(AddOrderDTO addOrderDTO);

    /**
     * 新增下单后余料处理
     *
     * @param addSurplusDTO
     */
    void addSurplus(AddSurplusDTO addSurplusDTO);


    /**
     * 下单分页查询
     *
     * @param orderQueryDTO
     * @return
     */
    PageDTO<OrderListDTO> getlist(OrderQueryDTO orderQueryDTO);

    /**
     * 删除下单
     *
     * @param id 下单id
     */
    void deleted(Long id);

    /**
     * 下单详情
     *
     * @param id 下单id
     * @return
     */
    OrderDetailDTO getDetail(Long id);

    /**
     * 打印详情
     *
     * @param id 下单id
     * @return
     */
    OrderDetailDTO getPrintOrder(Long id);

    /**
     * 编辑下单
     *
     * @param editOrderDTO
     */
    AddOrderResultDTO edit(EditOrderDTO editOrderDTO);

    /**
     * 恢复删除下单
     *
     * @param id 下单id
     */
    void recovery(Long id);

    /**
     * 导出下单
     *
     * @param orderQueryDTO
     * @return
     */
    List<OrderDetailDTO> getExportList(OrderQueryDTO orderQueryDTO);
}
