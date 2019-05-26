package com.dongyu.company.web.order.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.order.dto.AddOrderDTO;
import com.dongyu.company.order.dto.AddOrderResultDTO;
import com.dongyu.company.order.dto.AddPlusOrderDTO;
import com.dongyu.company.order.dto.AddSurplusDTO;
import com.dongyu.company.order.dto.OrderDetailDTO;
import com.dongyu.company.order.dto.OrderListDTO;
import com.dongyu.company.order.dto.OrderQueryDTO;
import com.dongyu.company.order.dto.PlusOrderListDTO;
import com.dongyu.company.order.dto.PlusOrderQueryDTO;
import com.dongyu.company.order.service.OrderService;
import com.dongyu.company.order.view.OrderExcelView;
import com.dongyu.company.web.order.form.AddOrderForm;
import com.dongyu.company.web.order.form.AddPlusOrderForm;
import com.dongyu.company.web.order.form.AddSurplusForm;
import com.dongyu.company.web.order.form.EditPlusOrderForm;
import com.dongyu.company.web.order.form.ExportOrderQueryForm;
import com.dongyu.company.web.order.form.OrderQueryForm;
import com.dongyu.company.web.order.form.PlusOrderQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下单管理相关
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@RestController
@Api(tags = "OrderController", description = "下单管理相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/engineering/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("新增下单")
    @PostMapping(value = "/add")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<AddOrderResultDTO> add(@Valid @RequestBody AddOrderForm addOrderForm) {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        BeanUtils.copyProperties(addOrderForm, addOrderDTO);
        AddOrderResultDTO resultDTO = orderService.add(addOrderDTO);
        return ResponseVo.successResponse(resultDTO);
    }

    @ApiOperation("新增下单后余料处理")
    @PostMapping(value = "/add/surplus")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo addSurplus(@Valid @RequestBody AddSurplusForm addSurplusForm) {
        AddSurplusDTO addSurplusDTO = new AddSurplusDTO();
        BeanUtils.copyProperties(addSurplusForm, addSurplusDTO);
        orderService.addSurplus(addSurplusDTO);
        return ResponseVo.successResponse();
    }


    @ApiOperation("查询下单")
    @GetMapping
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PageDTO<OrderListDTO>> get(@ModelAttribute OrderQueryForm orderQueryForm) {
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        BeanUtils.copyProperties(orderQueryForm, orderQueryDTO);
        PageDTO<OrderListDTO> pageDTO = orderService.getlist(orderQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("下单详情")
    @GetMapping(value = "/detail")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<OrderDetailDTO> detail(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
        OrderDetailDTO detail = orderService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("删除下单")
    @DeleteMapping(value = "/deleted")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo deleted(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
        orderService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("下单打印")
    @GetMapping(value = "/print")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<OrderDetailDTO> print(@ApiParam(name = "id", value = "下单id") @RequestParam("id") Long id) {
        OrderDetailDTO printOrder = orderService.getPrintOrder(id);
        return ResponseVo.successResponse(printOrder);
    }

//    @ApiOperation("编辑下单")
//    @PostMapping(value = "/edit")
//    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
//    public ResponseVo<AddOrderResultDTO> edite(@Valid @RequestBody EditOrderForm editOrderForm) {
//        EditOrderDTO editOrderDTO = new EditOrderDTO();
//        BeanUtils.copyProperties(editOrderForm, editOrderDTO);
//        AddOrderResultDTO edit = orderService.edit(editOrderDTO);
//        return ResponseVo.successResponse(edit);
//    }

    @ApiOperation("下单导出")
    @GetMapping(value = "/export")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ModelAndView exportExcel(@ModelAttribute ExportOrderQueryForm form) {
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        BeanUtils.copyProperties(form, orderQueryDTO);
        List<OrderDetailDTO> orderDetailDTOS = orderService.getExportList(orderQueryDTO);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "下单" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("orderDetailDtoS", orderDetailDTOS);
        map.put("fileName", fileName);
        OrderExcelView excelView = new OrderExcelView();
        return new ModelAndView(excelView, map);
    }

    @ApiOperation("恢复下单")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo recovery(@ApiParam(name = "id", value = "下单ID") @RequestParam("id") Long id) {
        orderService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("新增补单")
    @PostMapping(value = "/add/plus/order")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo addPlusOrder(@Valid @RequestBody AddPlusOrderForm form) {
        AddPlusOrderDTO addPlusOrderDTO = new AddPlusOrderDTO();
        BeanUtils.copyProperties(form, addPlusOrderDTO);
        orderService.addPlusOrder(addPlusOrderDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询补单")
    @GetMapping(value = "plus/order")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PageDTO<PlusOrderListDTO>> getPlusOrderList(@ModelAttribute PlusOrderQueryForm form) {
        PlusOrderQueryDTO plusOrderQueryDTO = new PlusOrderQueryDTO();
        BeanUtils.copyProperties(form, plusOrderQueryDTO);
        PageDTO<PlusOrderListDTO> pageDTO = orderService.getPlusOrderList(plusOrderQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("补单详情")
    @GetMapping(value = "/detail/plus/order")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PlusOrderListDTO> getPlusOrderDetail(@ApiParam(name = "id", value = "补单id") @RequestParam("id") Long id) {
        PlusOrderListDTO plusOrderDetail = orderService.getPlusOrderDetail(id);
        return ResponseVo.successResponse(plusOrderDetail);
    }

    @ApiOperation("删除补单")
    @DeleteMapping(value = "/deleted/plus/order")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo deletedPlusOrder(@ApiParam(name = "id", value = "补单id") @RequestParam("id") Long id) {
        orderService.deletedPlusOrder(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复补单")
    @GetMapping(value = "/recovery/plus/order")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo recoveryPlusOrder(@ApiParam(name = "id", value = "补单ID") @RequestParam("id") Long id) {
        orderService.recoveryPlusOrder(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("编辑补单")
    @PostMapping(value = "/edit/plus/order")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo editPlusOrder(@Valid @RequestBody EditPlusOrderForm form) {
        AddPlusOrderDTO addPlusOrderDTO = new AddPlusOrderDTO();
        BeanUtils.copyProperties(form, addPlusOrderDTO);
        orderService.editPlusOrder(addPlusOrderDTO);
        return ResponseVo.successResponse();
    }
}
