package com.dongyu.company.web.mould.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.mould.dto.AddMouldDTO;
import com.dongyu.company.mould.dto.EditMouldDTO;
import com.dongyu.company.mould.dto.MouldDetailDTO;
import com.dongyu.company.mould.dto.MouldListDTO;
import com.dongyu.company.mould.dto.MouldQueryDTO;
import com.dongyu.company.mould.service.PurchaseMouldService;
import com.dongyu.company.mould.view.MouldExcelView;
import com.dongyu.company.web.mould.form.AddMouldForm;
import com.dongyu.company.web.mould.form.EditMouldForm;
import com.dongyu.company.web.mould.form.ExportMouldQueryForm;
import com.dongyu.company.web.mould.form.MouldQueryForm;
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
 * 模具采购相关
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */

@RestController
@Api(tags = "MouldController", description = "模具采购相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/engineering/mould")
public class MouldController {

    @Autowired
    private PurchaseMouldService purchaseMouldService;


    @ApiOperation("新增模具采购")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddMouldForm addMouldForm) {
        AddMouldDTO addMouldDTO = new AddMouldDTO();
        BeanUtils.copyProperties(addMouldForm, addMouldDTO);
        addMouldDTO.setPurchaseDate(DateUtil.parseStrToDate(addMouldForm.getPurchaseDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        purchaseMouldService.add(addMouldDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询模具采购")
    @PostMapping
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PageDTO<MouldListDTO>> get(@RequestBody MouldQueryForm mouldQueryForm) {
        MouldQueryDTO mouldQueryDTO = new MouldQueryDTO();
        BeanUtils.copyProperties(mouldQueryForm, mouldQueryDTO);
        PageDTO<MouldListDTO> pageDTO = purchaseMouldService.getlist(mouldQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑模具采购")
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditMouldForm editMouldForm) {
        EditMouldDTO editMouldDTO = new EditMouldDTO();
        BeanUtils.copyProperties(editMouldForm, editMouldDTO);
        editMouldDTO.setPurchaseDate(DateUtil.parseStrToDate(editMouldForm.getPurchaseDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        purchaseMouldService.edit(editMouldDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除模具采购")
    @DeleteMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "模具采购id") @RequestParam("id") Long id) {
        purchaseMouldService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复删除模具采购")
    @GetMapping(value = "/recovery")
    public ResponseVo recovery(@ApiParam(name = "id", value = "模具采购id") @RequestParam("id") Long id) {
        purchaseMouldService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("模具采购详情")
    @GetMapping(value = "/detail")
    public ResponseVo<MouldDetailDTO> detail(@ApiParam(name = "id", value = "模具采购id") @RequestParam("id") Long id) {
        MouldDetailDTO detail = purchaseMouldService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("模具采购导出")
    @GetMapping(value = "/export")
    public ModelAndView exportExcel(@ModelAttribute ExportMouldQueryForm form) {
        MouldQueryDTO mouldQueryDTO = new MouldQueryDTO();
        BeanUtils.copyProperties(form, mouldQueryDTO);
        List<MouldDetailDTO> mouldDetailDTOS = purchaseMouldService.getExportList(mouldQueryDTO);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "模具采购" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("mouldListDTO", mouldDetailDTOS);
        map.put("fileName", fileName);
        MouldExcelView excelView = new MouldExcelView();
        return new ModelAndView(excelView, map);
    }
}
