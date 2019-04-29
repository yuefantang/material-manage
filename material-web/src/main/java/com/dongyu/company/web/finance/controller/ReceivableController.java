package com.dongyu.company.web.finance.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.finance.dto.AddReceivableDTO;
import com.dongyu.company.finance.dto.BillListDTO;
import com.dongyu.company.finance.dto.BillStatisticsDTO;
import com.dongyu.company.finance.dto.EditReceivableDTO;
import com.dongyu.company.finance.dto.ReceivableListDTO;
import com.dongyu.company.finance.dto.ReceivableQueryDTO;
import com.dongyu.company.finance.service.FinanceService;
import com.dongyu.company.finance.service.ReceivableService;
import com.dongyu.company.finance.view.BillExcelView;
import com.dongyu.company.finance.view.ReceivableExcelView;
import com.dongyu.company.web.finance.form.AddReceivableForm;
import com.dongyu.company.web.finance.form.BillQueryForm;
import com.dongyu.company.web.finance.form.EditReceivableForm;
import com.dongyu.company.web.finance.form.ExportBillQueryForm;
import com.dongyu.company.web.finance.form.ExportReceivableQueryForm;
import com.dongyu.company.web.finance.form.ReceivableQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 财务收款相关管理
 *
 * @author TYF
 * @date 2019/1/8
 * @since 1.0.0
 */

@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/finance/receive")
@Api(tags = "ReceivableController", description = "财务收款相关管理")
public class ReceivableController {

    @Autowired
    private ReceivableService receivableService;
    @Autowired
    private FinanceService financeService;

    @ApiOperation("新增收款")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddReceivableForm addReceivableForm) {
        AddReceivableDTO addReceivableDTO = new AddReceivableDTO();
        BeanUtils.copyProperties(addReceivableForm, addReceivableDTO);
        addReceivableDTO.setReceivableDate(DateUtil.parseStrToDate(addReceivableForm.getReceivableDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        receivableService.add(addReceivableDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("财务收款信息查询")
    @GetMapping
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo<PageDTO<ReceivableListDTO>> get(@ModelAttribute ReceivableQueryForm form) {
        ReceivableQueryDTO dto = new ReceivableQueryDTO();
        BeanUtils.copyProperties(form, dto);
        PageDTO<ReceivableListDTO> pageDTO = receivableService.getlist(dto);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑收款信息")
    @PostMapping(value = "/edit")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo edite(@Valid @RequestBody EditReceivableForm editReceivableForm) {
        EditReceivableDTO editReceivableDTO = new EditReceivableDTO();
        BeanUtils.copyProperties(editReceivableForm, editReceivableDTO);
        editReceivableDTO.setReceivableDate(DateUtil.parseStrToDate(editReceivableForm.getReceivableDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        receivableService.edit(editReceivableDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除收款记录")
    @DeleteMapping(value = "/deleted")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo deleted(@ApiParam(name = "id", value = "收款ID") @RequestParam("id") Long id) {
        receivableService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复收款记录")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo recovery(@ApiParam(name = "id", value = "收款ID") @RequestParam("id") Long id) {
        receivableService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("收款记录详情")
    @GetMapping(value = "/detail")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo<ReceivableListDTO> detail(@ApiParam(name = "id", value = "收款ID") @RequestParam("id") Long id) {
        ReceivableListDTO detail = receivableService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("收款记录导出")
    @GetMapping(value = "/export")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ModelAndView exportExcel(@ModelAttribute ExportReceivableQueryForm form) {
        ReceivableQueryDTO dto = new ReceivableQueryDTO();
        BeanUtils.copyProperties(form, dto);
        List<ReceivableListDTO> exportList = receivableService.getExportList(dto);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "收款" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("receivableListDTO", exportList);
        map.put("fileName", fileName);
        ReceivableExcelView excelView = new ReceivableExcelView();
        return new ModelAndView(excelView, map);
    }


    @ApiOperation("财务账单明细查询")
    @GetMapping(value = "/bill")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo<PageDTO<BillListDTO>> getBill(@ModelAttribute BillQueryForm form) {
        DeliveryQueryDTO dto = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, dto);
        PageDTO<BillListDTO> billList = financeService.getBillList(dto);
        return ResponseVo.successResponse(billList);
    }

    @ApiOperation("财务账单查询统计金额")
    @GetMapping(value = "/bill/count")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo<BillStatisticsDTO> count(@ModelAttribute ExportBillQueryForm form) {
        DeliveryQueryDTO dto = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, dto);
        BillStatisticsDTO count = financeService.count(dto);
        return ResponseVo.successResponse(count);
    }

    @ApiOperation("财务账单核实")
    @PostMapping(value = "/verify")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo verify(@RequestBody List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BizException("未选择要核实取消的数据，请勾选!");
        }
        List<Long> listId = ids.stream().map(str -> {
            return Long.valueOf(str);
        }).collect(Collectors.toList());
        financeService.verify(listId);
        return ResponseVo.successResponse();
    }

    @ApiOperation("财务账单核实取消")
    @PostMapping(value = "/unverify")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ResponseVo unverify(@RequestBody List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BizException("未选择要核实取消的数据，请勾选!");
        }
        List<Long> listId = ids.stream().map(str -> {
            return Long.valueOf(str);
        }).collect(Collectors.toList());
        financeService.unverify(listId);
        return ResponseVo.successResponse();
    }

//    @ApiOperation("财务账单打印")
//    @GetMapping(value = "/print")
//    @RequiresRoles(value = {"admin"})
//    public ResponseVo<List<BillListDTO>> getPrint(@ModelAttribute ExportBillQueryForm form) {
//        DeliveryQueryDTO dto = new DeliveryQueryDTO();
//        BeanUtils.copyProperties(form, dto);
//        List<BillListDTO> list = financeService.getBillExportList(dto);
//        return ResponseVo.successResponse(list);
//    }

    @ApiOperation("财务账单导出")
    @GetMapping(value = "/bill/export")
    @RequiresRoles(value = {"admin", "finance"}, logical = Logical.OR)
    public ModelAndView exportExcel(@ModelAttribute ExportBillQueryForm form) {
        DeliveryQueryDTO dto = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, dto);
        List<BillListDTO> billExportList = financeService.getBillExportList(dto);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "账单明细" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("billExportList", billExportList);
        map.put("fileName", fileName);
        BillExcelView excelView = new BillExcelView();
        return new ModelAndView(excelView, map);
    }


}
