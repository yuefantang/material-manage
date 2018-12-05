package com.dongyu.company.web.deliverynote.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;
import com.dongyu.company.deliverynote.service.DeliveryNoteService;
import com.dongyu.company.web.deliverynote.form.AddDeliveryNoteForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

//    @ApiOperation("新增其它收费开单")
//    @PostMapping(value = "/add/surplus")
//    public ResponseVo addSurplus(@Valid @RequestBody AddSurplusForm addSurplusForm) {
//        AddSurplusDTO addSurplusDTO = new AddSurplusDTO();
//        BeanUtils.copyProperties(addSurplusForm, addSurplusDTO);
//        orderService.addSurplus(addSurplusDTO);
//        return ResponseVo.successResponse();
//    }
//
//
//    @ApiOperation("查询下单")
//    @GetMapping
//    public ResponseVo<PageDTO<OrderListDTO>> get(@ModelAttribute OrderQueryForm orderQueryForm) {
//        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
//        BeanUtils.copyProperties(orderQueryForm, orderQueryDTO);
//        PageDTO<OrderListDTO> pageDTO = orderService.getlist(orderQueryDTO);
//        return ResponseVo.successResponse(pageDTO);
//    }
//
//    @ApiOperation("下单详情")
//    @GetMapping(value = "/detail")
//    public ResponseVo<OrderDetailDTO> detail(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
//        OrderDetailDTO detail = orderService.getDetail(id);
//        return ResponseVo.successResponse(detail);
//    }
//
//
//    @ApiOperation("删除下单")
//    @DeleteMapping(value = "/deleted")
//    public ResponseVo deleted(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
//        orderService.deleted(id);
//        return ResponseVo.successResponse();
//    }
//
//    @ApiOperation("下单打印")
//    @GetMapping(value = "/print")
//    public ResponseVo<OrderDetailDTO> print(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
//        OrderDetailDTO printOrder = orderService.getPrintOrder(id);
//        return ResponseVo.successResponse(printOrder);
//    }
//
//
//    @ApiOperation("编辑下单")
//    @PostMapping(value = "/edit")
//    public ResponseVo<AddOrderResultDTO> edite(@Valid @RequestBody EditOrderForm editOrderForm) {
//        EditOrderDTO editOrderDTO = new EditOrderDTO();
//        BeanUtils.copyProperties(editOrderForm, editOrderDTO);
//        AddOrderResultDTO edit = orderService.edit(editOrderDTO);
//        return ResponseVo.successResponse(edit);
//    }


}
