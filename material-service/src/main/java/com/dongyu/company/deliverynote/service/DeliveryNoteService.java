package com.dongyu.company.deliverynote.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.AddOtherDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.DeliveryDetailDTO;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.deliverynote.dto.EditDeliveryDTO;

import java.util.List;

/**
 * 货款单Service
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
public interface DeliveryNoteService {

    /**
     * 货款单新增
     *
     * @param dtos
     */
    List<DeliveryListDTO> add(List<AddDeliveryNoteDTO> dtos);

    /**
     * 新增其它收费开单
     *
     * @param dto
     */
    void addOtherDelivery(List<AddOtherDeliveryNoteDTO> dto);

    /**
     * 查询货款单
     *
     * @param deliveryQueryDTO
     * @return
     */
    PageDTO<DeliveryListDTO> getlist(DeliveryQueryDTO deliveryQueryDTO);

    /**
     * 送货单作废
     *
     * @param id
     */
    void deleted(Long id);

    /**
     * 货款单返回详情
     *
     * @param id
     * @return
     */
    DeliveryDetailDTO getDetail(Long id);

    /**
     * 货款单编辑
     *
     * @param dto
     */
    void edit(EditDeliveryDTO dto);

    /**
     * 导出下单
     *
     * @param deliveryQueryDTO
     * @return
     */
    List<DeliveryDetailDTO> getExportList(DeliveryQueryDTO deliveryQueryDTO);


    /**
     * 送货单打印
     *
     * @return
     */
    List<DeliveryListDTO> getPrintDeliveryNote(List<String> ids);
}
