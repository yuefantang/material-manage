package com.dongyu.company.web.warehouse.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.warehouse.dto.AddStockDTO;
import com.dongyu.company.warehouse.dto.StockListDTO;
import com.dongyu.company.warehouse.dto.StockQueryDTO;
import com.dongyu.company.warehouse.service.StockService;
import com.dongyu.company.web.warehouse.form.AddStockForm;
import com.dongyu.company.web.warehouse.form.EditStockForm;
import com.dongyu.company.web.warehouse.form.StockQueryForm;
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

/**
 * 仓库相关管理
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/warehouse")
@Api(tags = "WarehouseController", description = "仓库相关管理")
public class WarehouseController {

    @Autowired
    private StockService stockService;

    @ApiOperation("新增库存登记")
    @PostMapping(value = "/add")
    @RequiresRoles(value = {"admin","warehouse"},logical = Logical.OR)
    public ResponseVo add(@Valid @RequestBody AddStockForm form) {
        AddStockDTO addStockDTO = new AddStockDTO();
        BeanUtils.copyProperties(form, addStockDTO);
        stockService.add(addStockDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询库存")
    @GetMapping
    @RequiresRoles(value = {"admin","warehouse"},logical = Logical.OR)
    public ResponseVo<PageDTO<StockListDTO>> get(@ModelAttribute StockQueryForm queryForm) {
        StockQueryDTO stockQueryDTO = new StockQueryDTO();
        BeanUtils.copyProperties(queryForm, stockQueryDTO);
        PageDTO<StockListDTO> pageDTO = stockService.getlist(stockQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑库存")
    @PostMapping(value = "/edit")
    @RequiresRoles(value = {"admin","warehouse"},logical = Logical.OR)
    public ResponseVo edite(@Valid @RequestBody EditStockForm form) {
        AddStockDTO addStockDTO = new AddStockDTO();
        BeanUtils.copyProperties(form, addStockDTO);
        stockService.edit(addStockDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除库存")
    @RequiresRoles(value = {"admin","warehouse"},logical = Logical.OR)
    @DeleteMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "库存ID") @RequestParam("id") Long id) {
        stockService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复库存")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin","warehouse"},logical = Logical.OR)
    public ResponseVo recovery(@ApiParam(name = "id", value = "库存ID") @RequestParam("id") Long id) {
        stockService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("库存详情")
    @GetMapping(value = "/detail")
    @RequiresRoles(value = {"admin","warehouse"},logical = Logical.OR)
    public ResponseVo<StockListDTO> detail(@ApiParam(name = "id", value = "库存ID") @RequestParam("id") Long id) {
        StockListDTO detail = stockService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

}
