package com.dongyu.company.web.quality.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.quality.dto.AddQualityDTO;
import com.dongyu.company.quality.dto.EditQualityDTO;
import com.dongyu.company.quality.dto.QualityDetailDTO;
import com.dongyu.company.quality.dto.QualityListDTO;
import com.dongyu.company.quality.dto.QualityQueryDTO;
import com.dongyu.company.quality.service.QualityService;
import com.dongyu.company.web.quality.form.AddQualityForm;
import com.dongyu.company.web.quality.form.EditQualityForm;
import com.dongyu.company.web.quality.form.QualityQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 品质问题相关
 *
 * @author TYF
 * @date 2019/1/6
 * @since 1.0.0
 */
@RestController
@Api(tags = "QualityController", description = "品质问题相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/quality")
public class QualityController {

    @Autowired
    private QualityService qualityService;

    @ApiOperation("新增品质问题")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddQualityForm addQualityForm) {
        AddQualityDTO addQualityDTO = new AddQualityDTO();
        BeanUtils.copyProperties(addQualityForm, addQualityDTO);
        addQualityDTO.setProblemTime(DateUtil.parseStrToDate(addQualityForm.getProblemTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        qualityService.add(addQualityDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询品质问题")
    @GetMapping
    public ResponseVo<PageDTO<QualityListDTO>> get(@ModelAttribute QualityQueryForm qualityQueryForm) {
        QualityQueryDTO qualityQueryDTO = new QualityQueryDTO();
        BeanUtils.copyProperties(qualityQueryForm, qualityQueryDTO);
        PageDTO<QualityListDTO> pageDTO = qualityService.getlist(qualityQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑品质问题")
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditQualityForm editQualityForm) {
        EditQualityDTO editQualityDTO = new EditQualityDTO();
        BeanUtils.copyProperties(editQualityForm, editQualityDTO);
        editQualityDTO.setProblemTime(DateUtil.parseStrToDate(editQualityForm.getProblemTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        qualityService.edit(editQualityDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除品质问题")
    @DeleteMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "品质问题id") @RequestParam("id") Long id) {
        qualityService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复删除品质问题")
    @GetMapping(value = "/recovery")
    public ResponseVo recovery(@ApiParam(name = "id", value = "品质问题id") @RequestParam("id") Long id) {
        qualityService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("品质问题详情")
    @GetMapping(value = "/detail")
    public ResponseVo<QualityDetailDTO> detail(@ApiParam(name = "id", value = "品质问题id") @RequestParam("id") Long id) {
        QualityDetailDTO detail = qualityService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

}
