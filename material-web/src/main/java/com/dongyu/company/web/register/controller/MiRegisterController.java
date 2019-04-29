package com.dongyu.company.web.register.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.register.dto.AddRegisterDTO;
import com.dongyu.company.register.dto.RegisterDetailDTO;
import com.dongyu.company.register.dto.RegisterListDTO;
import com.dongyu.company.register.dto.RegisterQueryDTO;
import com.dongyu.company.register.service.RegisterService;
import com.dongyu.company.register.view.RegisterExcelView;
import com.dongyu.company.web.register.form.AddRegisterForm;
import com.dongyu.company.web.register.form.EditRegisterForm;
import com.dongyu.company.web.register.form.ExportRegisterQueryForm;
import com.dongyu.company.web.register.form.RegisterQueryForm;
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
import org.springframework.web.bind.annotation.PathVariable;
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
 * MI登记相关
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@RestController
@Api(tags = "MiRegisterController", description = " MI登记相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/engineering/register")
public class MiRegisterController {
    @Autowired
    private RegisterService registerService;

    @ApiOperation("新增MI登记")
    @PostMapping(value = "/add")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo add(@Valid @RequestBody AddRegisterForm addRegisterForm) {
        AddRegisterDTO addRegisterDTO = new AddRegisterDTO();
        BeanUtils.copyProperties(addRegisterForm, addRegisterDTO);
        registerService.add(addRegisterDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询MI登记")
    @GetMapping
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PageDTO<RegisterListDTO>> get(@ModelAttribute RegisterQueryForm queryForm) {
        RegisterQueryDTO registerQueryDTO = new RegisterQueryDTO();
        BeanUtils.copyProperties(queryForm, registerQueryDTO);
        PageDTO<RegisterListDTO> pageDTO = registerService.getlist(registerQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("编辑MI登记")
    @PostMapping(value = "/edit")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo edite(@Valid @RequestBody EditRegisterForm editRegisterForm) {
        RegisterDetailDTO editRegisterDTO = new RegisterDetailDTO();
        BeanUtils.copyProperties(editRegisterForm, editRegisterDTO);
        registerService.edit(editRegisterDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除MI登记")
    @DeleteMapping(value = "/deleted")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo deleted(@ApiParam(name = "id", value = "MI登记ID") @RequestParam("id") Long id) {
        registerService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复MI登记")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo recovery(@ApiParam(name = "id", value = "MI登记ID") @RequestParam("id") Long id) {
        registerService.recovery(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("MI登记详情")
    @GetMapping(value = "/detail")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<RegisterDetailDTO> detail(@ApiParam(name = "id", value = "MI登记ID") @RequestParam("id") Long id) {
        RegisterDetailDTO detail = registerService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("MI登记导出")
    @GetMapping(value = "/export")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ModelAndView exportExcel(@ModelAttribute ExportRegisterQueryForm form) {
        RegisterQueryDTO registerQueryDTO = new RegisterQueryDTO();
        BeanUtils.copyProperties(form, registerQueryDTO);
        List<RegisterDetailDTO> registerDetailDTOS = registerService.getExportList(registerQueryDTO);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "MI登记" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("registerListDTO", registerDetailDTOS);
        map.put("fileName", fileName);
        RegisterExcelView excelView = new RegisterExcelView();
        return new ModelAndView(excelView, map);
    }

    @ApiOperation("获取客户名称下拉数据")
    @GetMapping(value = "/name")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<List<String>> getCustomerName(@ApiParam(name = "customerName", value = "客户名称(查询所有可传空字符串)") @RequestParam(value="customerName",required=false) String customerName) {
        List<String> list = registerService.getCustomerName(customerName);
        return ResponseVo.successResponse(list);
    }

}
