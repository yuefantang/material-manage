package com.dongyu.company.web.operation.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.operation.dto.OperationQueryDTO;
import com.dongyu.company.operation.dto.OperationRecordTDO;
import com.dongyu.company.operation.service.OperationRecordService;
import com.dongyu.company.web.operation.form.OperationQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志相关管理
 *
 * @author TYF
 * @date 2019/1/7
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/operation")
@Api(tags = "OperationController", description = "日志相关管理")
public class OperationController {

    @Autowired
    private OperationRecordService operationRecordService;

    @ApiOperation("日志查询")
    @GetMapping
    public ResponseVo<List<OperationRecordTDO>> get(@ModelAttribute OperationQueryForm form) {
        OperationQueryDTO operationQueryDTO = new OperationQueryDTO();
        BeanUtils.copyProperties(form, operationQueryDTO);
        List<OperationRecordTDO> operation = operationRecordService.findOperation(operationQueryDTO);
        return ResponseVo.successResponse(operation);
    }

}
