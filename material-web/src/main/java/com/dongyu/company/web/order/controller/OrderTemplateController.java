package com.dongyu.company.web.order.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.order.dto.OrderTemplateDTO;
import com.dongyu.company.order.dto.OrderTemplateListDTO;
import com.dongyu.company.order.dto.OrderTemplateQueryDTO;
import com.dongyu.company.order.service.OrderTemplateService;
import com.dongyu.company.order.view.OrderTemplateExcelView;
import com.dongyu.company.web.order.form.AddOrderTemplateForm;
import com.dongyu.company.web.order.form.EditOrderTemplateForm;
import com.dongyu.company.web.order.form.ExportOrderTemplateQueryForm;
import com.dongyu.company.web.order.form.OrderTemplateQueryForm;
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
 * 样板下单管理相关
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
@RestController
@Api(tags = "OrderTemplateController", description = "样板下单管理相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/orderTemplate")
public class OrderTemplateController {

    @Autowired
    private OrderTemplateService orderTemplateService;

    @ApiOperation("新增样板")
    @PostMapping(value = "/add")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo add(@Valid @RequestBody AddOrderTemplateForm form) {
        OrderTemplateDTO dto = new OrderTemplateDTO();
        BeanUtils.copyProperties(form, dto);
        orderTemplateService.add(dto);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询样板")
    @PostMapping
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PageDTO<OrderTemplateListDTO>> get(@RequestBody OrderTemplateQueryForm form) {
        OrderTemplateQueryDTO queryDTO = new OrderTemplateQueryDTO();
        BeanUtils.copyProperties(form, queryDTO);
        PageDTO<OrderTemplateListDTO> pageDTO = orderTemplateService.getlist(queryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑样板")
    @PostMapping(value = "/edit")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo edite(@Valid @RequestBody EditOrderTemplateForm form) {
        OrderTemplateListDTO dto = new OrderTemplateListDTO();
        BeanUtils.copyProperties(form, dto);
        orderTemplateService.edit(dto);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除样板")
    @DeleteMapping(value = "/deleted")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo deleted(@ApiParam(name = "id", value = "样板id") @RequestParam("id") Long id) {
        orderTemplateService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复删除样板")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo recovery(@ApiParam(name = "id", value = "样板id") @RequestParam("id") Long id) {
        orderTemplateService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("样板详情")
    @GetMapping(value = "/detail")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<OrderTemplateListDTO> detail(@ApiParam(name = "id", value = "样板id") @RequestParam("id") Long id) {
        OrderTemplateListDTO detail = orderTemplateService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("样板导出")
    @GetMapping(value = "/export")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ModelAndView exportExcel(@ModelAttribute ExportOrderTemplateQueryForm form) {
        OrderTemplateQueryDTO queryDTO = new OrderTemplateQueryDTO();
        BeanUtils.copyProperties(form, queryDTO);
        List<OrderTemplateDTO> dtos = orderTemplateService.getExportList(queryDTO);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "样板" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("orderTemplateListDTO", dtos);
        map.put("fileName", fileName);
        OrderTemplateExcelView excelView = new OrderTemplateExcelView();
        return new ModelAndView(excelView, map);
    }

}
