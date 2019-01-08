package com.dongyu.company.web.finance.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.finance.service.FinanceService;
import com.dongyu.company.web.deliverynote.form.DeliveryQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private FinanceService financeService;

    @ApiOperation("财务收款信息查询")
    @GetMapping
    @RequiresRoles(value = {"admin"})
    public ResponseVo<PageDTO<DeliveryListDTO>> get(@ModelAttribute DeliveryQueryForm form) {
        DeliveryQueryDTO deliveryQueryDTO = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, deliveryQueryDTO);
//        PageDTO<DeliveryListDTO> pageDTO = financeService.getlist(deliveryQueryDTO);
//        return ResponseVo.successResponse(pageDTO);
        return null;
    }




}
