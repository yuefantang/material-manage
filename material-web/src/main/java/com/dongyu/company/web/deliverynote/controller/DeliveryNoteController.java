package com.dongyu.company.web.deliverynote.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.AddOtherDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.DeliveryDetailDTO;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.deliverynote.dto.EditDeliveryDTO;
import com.dongyu.company.deliverynote.service.DeliveryNoteService;
import com.dongyu.company.deliverynote.view.DeliveryNoteView;
import com.dongyu.company.web.deliverynote.form.AddDeliveryNoteForm;
import com.dongyu.company.web.deliverynote.form.AddOtherDeliveryNoteForm;
import com.dongyu.company.web.deliverynote.form.DeliveryQueryForm;
import com.dongyu.company.web.deliverynote.form.EditDeliveryForm;
import com.dongyu.company.web.deliverynote.form.ExportDeliveryQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

/**
 * 货款单相关管理
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = Constants.WEB_PREFIX + "/engineering/delivery")
@Api(tags = "DeliveryNoteController", description = "货款单相关管理")
public class DeliveryNoteController {
    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @ApiOperation("新增货款开单")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    @PostMapping(value = "/add")
    public ResponseVo<List<DeliveryListDTO>> add(@Valid @RequestBody List<AddDeliveryNoteForm> addDeliveryNoteForm) {
        if (CollectionUtils.isEmpty(addDeliveryNoteForm)) {
            throw new BizException("未选择开单数据，请选择再开单！");
        }
        List<AddDeliveryNoteDTO> deliveryNoteDTOS = addDeliveryNoteForm.stream().map(form -> {
            AddDeliveryNoteDTO addDeliveryNoteDTO = new AddDeliveryNoteDTO();
            BeanUtils.copyProperties(form, addDeliveryNoteDTO);
            return addDeliveryNoteDTO;
        }).collect(Collectors.toList());
        List<DeliveryListDTO> list = deliveryNoteService.add(deliveryNoteDTOS);
        return ResponseVo.successResponse(list);
    }

    @ApiOperation("新增其它收费开单")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    @PostMapping(value = "/add/otherDeliver")
    public ResponseVo addOtherDelivery(@Valid @RequestBody List<AddOtherDeliveryNoteForm> forms) {
        if (CollectionUtils.isEmpty(forms)) {
            throw new BizException("未选择开单数据，请选择再开单！");
        }
        List<AddOtherDeliveryNoteDTO> collect = forms.stream().map(form -> {
            AddOtherDeliveryNoteDTO addOtherDeliveryNoteDTO = new AddOtherDeliveryNoteDTO();
            BeanUtils.copyProperties(form, addOtherDeliveryNoteDTO);
            return addOtherDeliveryNoteDTO;
        }).collect(Collectors.toList());
        deliveryNoteService.addOtherDelivery(collect);
        return ResponseVo.successResponse();
    }

    @ApiOperation("查询货款单")
    @GetMapping
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ResponseVo<PageDTO<DeliveryListDTO>> get(@ModelAttribute DeliveryQueryForm form) {
        DeliveryQueryDTO deliveryQueryDTO = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, deliveryQueryDTO);
        PageDTO<DeliveryListDTO> pageDTO = deliveryNoteService.getlist(deliveryQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @ApiOperation("送货单作废")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    @GetMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "id", value = "货款单id") @RequestParam("id") Long id) {
        deliveryNoteService.deleted(id);
        return ResponseVo.successResponse();
    }

    @ApiOperation("货款详情")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    @GetMapping(value = "/detail")
    public ResponseVo<DeliveryDetailDTO> detail(@ApiParam(name = "id", value = "货款单id") @RequestParam("id") Long id) {
        DeliveryDetailDTO detail = deliveryNoteService.getDetail(id);
        return ResponseVo.successResponse(detail);
    }

    @ApiOperation("编辑货款单")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    @PostMapping(value = "/edit")
    public ResponseVo edite(@Valid @RequestBody EditDeliveryForm form) {
        EditDeliveryDTO editDeliveryDTO = new EditDeliveryDTO();
        BeanUtils.copyProperties(form, editDeliveryDTO);
        deliveryNoteService.edit(editDeliveryDTO);
        return ResponseVo.successResponse();
    }

    @ApiOperation("货款单导出")
    @GetMapping(value = "/export")
    @RequiresRoles(value = {"admin", "engineering"}, logical = Logical.OR)
    public ModelAndView exportExcel(@ModelAttribute ExportDeliveryQueryForm form) {
        DeliveryQueryDTO deliveryQueryDTO = new DeliveryQueryDTO();
        BeanUtils.copyProperties(form, deliveryQueryDTO);
        List<DeliveryDetailDTO> deliveryDTOList = deliveryNoteService.getExportList(deliveryQueryDTO);
        String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD);
        String fileName = "货款单" + date + ".xlsx";
        Map<String, Object> map = new HashMap<>();
        map.put("deliveryDTOList", deliveryDTOList);
        map.put("fileName", fileName);
        DeliveryNoteView excelView = new DeliveryNoteView();
        return new ModelAndView(excelView, map);
    }


    @ApiOperation("送货单打印")
    @RequiresRoles(value = {"admin"})
    @PostMapping(value = "/print")
    public ResponseVo<List<DeliveryListDTO>> print(@ApiParam(name = "id", value = "下单id") @RequestBody List<String> ids) {
        List<DeliveryListDTO> printOrder = deliveryNoteService.getPrintDeliveryNote(ids);
        return ResponseVo.successResponse(printOrder);
    }


}
