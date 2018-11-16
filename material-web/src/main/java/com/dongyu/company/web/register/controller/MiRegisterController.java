package com.dongyu.company.web.register.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.mould.dto.MouldListDTO;
import com.dongyu.company.register.dto.AddRegisterDTO;
import com.dongyu.company.register.dto.RegisterDetailDTO;
import com.dongyu.company.register.dto.RegisterListDTO;
import com.dongyu.company.register.dto.RegisterQueryDTO;
import com.dongyu.company.register.service.RegisterService;
import com.dongyu.company.web.mould.form.ExportMouldQueryForm;
import com.dongyu.company.web.mould.form.MouldQueryForm;
import com.dongyu.company.web.register.form.AddRegisterForm;
import com.dongyu.company.web.register.form.EditRegisterForm;
import com.dongyu.company.web.register.form.RegisterQueryForm;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * MI登记相关
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@RestController
@Api(tags = "MiRegisterController", description = " MI登记相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/register")
public class MiRegisterController {
    @Autowired
    private RegisterService registerService;

    @ApiOperation("新增MI登记")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddRegisterForm addRegisterForm) {
        AddRegisterDTO addRegisterDTO = new AddRegisterDTO();
        BeanUtils.copyProperties(addRegisterForm, addRegisterDTO);
        registerService.add(addRegisterDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询MI登记")
    @GetMapping
    public ResponseVo<PageDTO<RegisterListDTO>> get(@ModelAttribute RegisterQueryForm queryForm) {
        RegisterQueryDTO registerQueryDTO = new RegisterQueryDTO();
        BeanUtils.copyProperties(queryForm, registerQueryDTO);
        PageDTO<RegisterListDTO> pageDTO = registerService.getlist(registerQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑MI登记")
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditRegisterForm editRegisterForm) {
        RegisterDetailDTO editRegisterDTO = new RegisterDetailDTO();
        BeanUtils.copyProperties(editRegisterForm, editRegisterDTO);
        registerService.edit(editRegisterDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除MI登记")
    @DeleteMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "MI登记ID") @RequestParam("id") Long id) {
        registerService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("MI登记详情")
    @GetMapping(value = "/detail")
    public ResponseVo<RegisterDetailDTO> detail(@ApiParam(name = "id", value = "MI登记ID") @RequestParam("id") Long id) {
        RegisterDetailDTO detail = registerService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("MI登记导出")
    @GetMapping(value = "/export")
    public ModelAndView exportExcel(@ModelAttribute ExportMouldQueryForm form) {
        return new ModelAndView();
    }
}
