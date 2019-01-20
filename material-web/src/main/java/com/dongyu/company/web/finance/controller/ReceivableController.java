package com.dongyu.company.web.finance.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.finance.dto.AddReceivableDTO;
import com.dongyu.company.finance.dto.EditReceivableDTO;
import com.dongyu.company.finance.dto.ReceivableListDTO;
import com.dongyu.company.finance.dto.ReceivableQueryDTO;
import com.dongyu.company.finance.service.ReceivableService;
import com.dongyu.company.finance.view.ReceivableExcelView;
import com.dongyu.company.web.finance.form.AddReceivableForm;
import com.dongyu.company.web.finance.form.EditReceivableForm;
import com.dongyu.company.web.finance.form.ExportReceivableQueryForm;
import com.dongyu.company.web.finance.form.ReceivableQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 财务收款相关管理
 *
 * @author TYF
 * @date 2019/1/8
 * @since 1.0.0
 */

@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/receive")
@Api(tags = "ReceivableController", description = "财务收款相关管理")
public class ReceivableController {

    @Autowired
    private ReceivableService receivableService;

    @ApiOperation("新增收款")
    @RequiresRoles(value = {"admin"})
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
    @RequiresRoles(value = {"admin"})
    public ResponseVo<PageDTO<ReceivableListDTO>> get(@ModelAttribute ReceivableQueryForm form) {
        ReceivableQueryDTO dto = new ReceivableQueryDTO();
        BeanUtils.copyProperties(form, dto);
        PageDTO<ReceivableListDTO> pageDTO = receivableService.getlist(dto);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑收款信息")
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditReceivableForm editReceivableForm) {
        EditReceivableDTO editReceivableDTO = new EditReceivableDTO();
        BeanUtils.copyProperties(editReceivableForm, editReceivableDTO);
        editReceivableDTO.setReceivableDate(DateUtil.parseStrToDate(editReceivableForm.getReceivableDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        receivableService.edit(editReceivableDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除收款记录")
    @DeleteMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "收款ID") @RequestParam("id") Long id) {
        receivableService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复收款记录")
    @GetMapping(value = "/recovery")
    public ResponseVo recovery(@ApiParam(name = "id", value = "收款ID") @RequestParam("id") Long id) {
        receivableService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("收款记录详情")
    @GetMapping(value = "/detail")
    public ResponseVo<ReceivableListDTO> detail(@ApiParam(name = "id", value = "收款ID") @RequestParam("id") Long id) {
        ReceivableListDTO detail = receivableService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("收款记录导出")
    @GetMapping(value = "/export")
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

}
