package com.dongyu.company.web.dict.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.dict.dto.DictResultDTO;
import com.dongyu.company.dict.dto.StaticDataDTO;
import com.dongyu.company.dict.dto.StaticQueryDTO;
import com.dongyu.company.dict.service.DictService;
import com.dongyu.company.web.dict.controller.form.AddStaticDataForm;
import com.dongyu.company.web.dict.controller.form.StaticQueryForm;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 字典相关
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
@RestController
@Api(tags = "DictController", description = "下拉选择列表相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/dict")
public class DictController {
    @Autowired
    private DictService dictService;

//    @ApiOperation("相关字典列表")
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo<DictResultDTO> dict() {
//        DictResultDTO dictList = dictService.getDictList();
//        return ResponseVo.successResponse(dictList);
//    }

    @ApiOperation("静态数据获取")
    @GetMapping(value = "/static")
    public ResponseVo<Map<String, List<StaticDataDTO>>> staticData() {
        Map<String, List<StaticDataDTO>> staticData = dictService.getStaticData();
        return ResponseVo.successResponse(staticData);
    }

    @ApiOperation("查询下拉列表数据")
    @GetMapping
    @RequiresRoles(value = {"admin"})
    public ResponseVo<PageDTO<StaticDataDTO>> get(@ModelAttribute StaticQueryForm form) {
        StaticQueryDTO queryDTO = new StaticQueryDTO();
        BeanUtils.copyProperties(form, queryDTO);
        PageDTO<StaticDataDTO> pageDTO = dictService.getlist(queryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("新增下拉列表数据")
    @PostMapping(value = "/add")
    @RequiresRoles(value = {"admin"})
    public ResponseVo add(@Valid @RequestBody AddStaticDataForm form) {
        StaticDataDTO dto = new StaticDataDTO();
        BeanUtils.copyProperties(form, dto);
        dictService.add(dto);
        return ResponseVo.successResponse();
    }

    @ApiOperation("删除列表数据")
    @DeleteMapping(value = "/deleted")
    @RequiresRoles(value = {"admin"})
    public ResponseVo deleted(@ApiParam(name = "id", value = "列表数据id") @RequestParam("id") Long id) {
        dictService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("恢复删除列表数据")
    @GetMapping(value = "/recovery")
    @RequiresRoles(value = {"admin"})
    public ResponseVo recovery(@ApiParam(name = "id", value = "列表数据id") @RequestParam("id") Long id) {
        dictService.recovery(id);
        return ResponseVo.successResponse();
    }

}
