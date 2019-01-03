package com.dongyu.company.web.dict.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.dict.dto.DictResultDTO;
import com.dongyu.company.dict.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("相关字典列表")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo<DictResultDTO> dict() {
        DictResultDTO dictList = dictService.getDictList();
        return ResponseVo.successResponse(dictList);
    }
}
