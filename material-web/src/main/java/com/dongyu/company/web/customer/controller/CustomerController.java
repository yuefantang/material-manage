package com.dongyu.company.web.customer.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.customer.dto.AddCustomerDTO;
import com.dongyu.company.customer.dto.CustomerListDTO;
import com.dongyu.company.customer.dto.CustomerQueryDTO;
import com.dongyu.company.customer.dto.EditCustomerDTO;
import com.dongyu.company.customer.service.CustomerService;
import com.dongyu.company.order.dto.AddOrderResultDTO;
import com.dongyu.company.web.customer.form.AddCustomerForm;
import com.dongyu.company.web.customer.form.CustomerQueryForm;
import com.dongyu.company.web.customer.form.EditCustomerForm;
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

import javax.validation.Valid;
import java.util.List;

/**
 * 客户和供应商Controller
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
@RestController
@Api(tags = "CustomerController", description = "客户和供应商管理相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    @ApiOperation("新增客户")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddCustomerForm addCustomerForm) {
        AddCustomerDTO addCustomerDTO = new AddCustomerDTO();
        BeanUtils.copyProperties(addCustomerForm, addCustomerDTO);
        customerService.add(addCustomerDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("编辑客户")
    @PostMapping(value = "/edit")
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseVo<AddOrderResultDTO> edite(@Valid @RequestBody EditCustomerForm editCustomerForm) {
        EditCustomerDTO editCustomerDTO = new EditCustomerDTO();
        BeanUtils.copyProperties(editCustomerForm, editCustomerDTO);
        customerService.edit(editCustomerDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除客户")
    @DeleteMapping(value = "/deleted")
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseVo deleted(@ApiParam(name = "id", value = "客户id") @RequestParam("id") Long id) {
        customerService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复客户")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseVo recovery(@ApiParam(name = "id", value = "客户ID") @RequestParam("id") Long id) {
        customerService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("客户详情")
    @GetMapping(value = "/detail")
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseVo<CustomerListDTO> detail(@ApiParam(name = "id", value = "客户id") @RequestParam("id") Long id) {
        CustomerListDTO detail = customerService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("分页查询客户")
    @GetMapping
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseVo<PageDTO<CustomerListDTO>> get(@ModelAttribute CustomerQueryForm customerQueryForm) {
        CustomerQueryDTO customerQueryDTO = new CustomerQueryDTO();
        BeanUtils.copyProperties(customerQueryForm, customerQueryDTO);
        PageDTO<CustomerListDTO> pageList = customerService.getPageList(customerQueryDTO);
        return ResponseVo.successResponse(pageList);
    }


    @ApiOperation("获取客户名称下拉数据")
    @GetMapping(value = "/name")
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseVo<List<String>> getCustomerName(@ApiParam(name = "customerName", value = "客户名称(查询所有可不传)") @RequestParam(value = "customerName", required = false) String customerName, @ApiParam(name = "customerType", value = "客户类型(supplier:供应商，customer：客户)必传") @RequestParam(value = "customerType") String customerType) {
        List<String> list = customerService.getList(customerName, customerType);
        return ResponseVo.successResponse(list);
    }
}
