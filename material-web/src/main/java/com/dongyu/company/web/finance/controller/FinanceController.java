package com.dongyu.company.web.finance.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.finance.dto.AddMiPriceDTO;
import com.dongyu.company.finance.dto.EditMiPriceDTO;
import com.dongyu.company.finance.dto.MiPriceDetailDTO;
import com.dongyu.company.finance.dto.MiPriceListDTO;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;
import com.dongyu.company.finance.service.FinanceService;
import com.dongyu.company.web.finance.form.AddMiPriceForm;
import com.dongyu.company.web.finance.form.EditMiPriceForm;
import com.dongyu.company.web.finance.form.MiPriceQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
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
 * 财务相关管理
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/finance")
@Api(tags = "FinanceController", description = "财务相关管理")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @ApiOperation("新增MI登记价格")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddMiPriceForm addMiPriceForm) {
        AddMiPriceDTO addMiPriceDTO = new AddMiPriceDTO();
        BeanUtils.copyProperties(addMiPriceForm, addMiPriceDTO);
        financeService.add(addMiPriceDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询MI登记价格")
    @GetMapping
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo<PageDTO<MiPriceListDTO>> get(@ModelAttribute MiPriceQueryForm form) {
        MiPriceQueryDTO miPriceQueryDTO = new MiPriceQueryDTO();
        BeanUtils.copyProperties(form, miPriceQueryDTO);
        PageDTO<MiPriceListDTO> pageDTO = financeService.getlist(miPriceQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("MI登记价格删除")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    @GetMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "MI登记价格id") @RequestParam("id") Long id) {
        financeService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复删除MI登记价格")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo recovery(@ApiParam(name = "id", value = "MI登记价格id") @RequestParam("id") Long id) {
        financeService.recovery(id);
        return ResponseVo.successResponse();
    }


    @ApiOperation("编辑MI登记价格")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditMiPriceForm form) {
        EditMiPriceDTO editMiPriceDTO = new EditMiPriceDTO();
        BeanUtils.copyProperties(form, editMiPriceDTO);
        financeService.edit(editMiPriceDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("MI登记价格详情")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    @GetMapping(value = "/detail")
    public ResponseVo<MiPriceDetailDTO> detail(@ApiParam(name = "id", value = "MI登记价格id") @RequestParam("id") Long id) {
        MiPriceDetailDTO detail = financeService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

}
