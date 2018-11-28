package com.dongyu.company.web.order.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.order.dto.AddOrderDTO;
import com.dongyu.company.order.dto.AddOrderResultDTO;
import com.dongyu.company.order.dto.AddSurplusDTO;
import com.dongyu.company.order.dto.OrderListDTO;
import com.dongyu.company.order.dto.OrderQueryDTO;
import com.dongyu.company.order.service.OrderService;
import com.dongyu.company.web.order.form.AddOrderForm;
import com.dongyu.company.web.order.form.AddSurplusForm;
import com.dongyu.company.web.order.form.OrderQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 下单管理相关
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@RestController
@Api(tags = "OrderController", description = "下单管理相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("新增下单")
    @PostMapping(value = "/add")
    public ResponseVo<AddOrderResultDTO> add(@Valid @RequestBody AddOrderForm addOrderForm) {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        BeanUtils.copyProperties(addOrderForm, addOrderDTO);
        AddOrderResultDTO resultDTO = orderService.add(addOrderDTO);
        return ResponseVo.successResponse(resultDTO);
    }

    @ApiOperation("新增下单后余料处理")
    @PostMapping(value = "/add/surplus")
    public ResponseVo addSurplus(@Valid @RequestBody AddSurplusForm addSurplusForm) {
        AddSurplusDTO addSurplusDTO = new AddSurplusDTO();
        BeanUtils.copyProperties(addSurplusForm, addSurplusDTO);
        orderService.addSurplus(addSurplusDTO);
        return ResponseVo.successResponse();
    }


    @ApiOperation("查询下单")
    @GetMapping
    public ResponseVo<PageDTO<OrderListDTO>> get(@ModelAttribute OrderQueryForm orderQueryForm) {
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        BeanUtils.copyProperties(orderQueryForm, orderQueryDTO);
        PageDTO<OrderListDTO> pageDTO = orderService.getlist(orderQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }
//
//    @ApiOperation("编辑模具采购")
//    @PostMapping(value = "/edit")
//    public ResponseVo edite(@Valid @RequestBody EditMouldForm editMouldForm) {
//        EditMouldDTO editMouldDTO = new EditMouldDTO();
//        BeanUtils.copyProperties(editMouldForm, editMouldDTO);
//        editMouldDTO.setPurchaseDate(DateUtil.parseStrToDate(editMouldForm.getPurchaseDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
//        purchaseMouldService.edit(editMouldDTO);
//        return ResponseVo.successResponse();
//    }
//
//    @ApiOperation("删除模具采购")
//    @DeleteMapping(value = "/deleted")
//    public ResponseVo deleted(@ApiParam(name = "id", value = "模具采购id") @RequestParam("id") Long id) {
//        purchaseMouldService.deleted(id);
//        return ResponseVo.successResponse();
//    }
//
//    @ApiOperation("模具采购详情")
//    @GetMapping(value = "/detail")
//    public ResponseVo<MouldDetailDTO> detail(@ApiParam(name = "id", value = "模具采购id") @RequestParam("id") Long id) {
//        MouldDetailDTO detail = purchaseMouldService.getDetail(id);
//        return ResponseVo.successResponse(detail);
//    }
}
