package com.dongyu.company.web.deliverynote.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.AddOtherDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.DeliveryDetailDTO;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.deliverynote.dto.EditDeliveryDTO;
import com.dongyu.company.deliverynote.service.DeliveryNoteService;
import com.dongyu.company.web.deliverynote.form.AddDeliveryNoteForm;
import com.dongyu.company.web.deliverynote.form.AddOtherDeliveryNoteForm;
import com.dongyu.company.web.deliverynote.form.DeliveryQueryForm;
import com.dongyu.company.web.deliverynote.form.EditDeliveryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 货款单相关管理
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/delivery")
@Api(tags = "DeliveryNoteController", description = "货款单相关管理")
public class DeliveryNoteController {
    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @ApiOperation("新增货款开单")
    @RequiresRoles(value = {"admin"})
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddDeliveryNoteForm addDeliveryNoteForm) {
        AddDeliveryNoteDTO addDeliveryNoteDTO = new AddDeliveryNoteDTO();
        BeanUtils.copyProperties(addDeliveryNoteForm, addDeliveryNoteDTO);
        deliveryNoteService.add(addDeliveryNoteDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("新增其它收费开单")
    @RequiresRoles(value = {"admin"})
    @PostMapping(value = "/add/otherDeliver")
    public ResponseVo addOtherDelivery(@Valid @RequestBody AddOtherDeliveryNoteForm form) {
        AddOtherDeliveryNoteDTO addOtherDeliveryNoteDTO = new AddOtherDeliveryNoteDTO();
        BeanUtils.copyProperties(form, addOtherDeliveryNoteDTO);
        deliveryNoteService.addOtherDelivery(addOtherDeliveryNoteDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询货款单")
    @GetMapping
    @RequiresRoles(value = {"admin"})
    public ResponseVo<PageDTO<DeliveryListDTO>> get(@ModelAttribute DeliveryQueryForm form) {
        DeliveryQueryDTO deliveryQueryDTO = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, deliveryQueryDTO);
        PageDTO<DeliveryListDTO> pageDTO = deliveryNoteService.getlist(deliveryQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("送货单作废")
    @RequiresRoles(value = {"admin"})
    @GetMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "货款单id") @RequestParam("id") Long id) {
        deliveryNoteService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("货款详情")
    @RequiresRoles(value = {"admin"})
    @GetMapping(value = "/detail")
    public ResponseVo<DeliveryDetailDTO> detail(@ApiParam(name = "id", value = "货款单id") @RequestParam("id") Long id) {
        DeliveryDetailDTO detail = deliveryNoteService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("编辑货款单")
    @RequiresRoles(value = {"admin"})
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditDeliveryForm form) {
        EditDeliveryDTO editDeliveryDTO = new EditDeliveryDTO();
        BeanUtils.copyProperties(form, editDeliveryDTO);
        deliveryNoteService.edit(editDeliveryDTO);
        return ResponseVo.successResponse();
    }


//
//    @ApiOperation("送货单打印")
    //    @RequiresRoles(value = {"admin"})
//    @GetMapping(value = "/print")
//    public ResponseVo<OrderDetailDTO> print(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
//        OrderDetailDTO printOrder = orderService.getPrintOrder(id);
//        return ResponseVo.successResponse(printOrder);
//    }
//

}
