package com.dongyu.company.deliverynote.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.BillingTypeEnum;
import com.dongyu.company.common.constants.CompleteStateEnum;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.CurrencyEunm;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.deliverynote.dao.DeliveryNoteDao;
import com.dongyu.company.deliverynote.dao.DeliverySpecs;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.AddOtherDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.DeliveryDetailDTO;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.deliverynote.dto.EditDeliveryDTO;
import com.dongyu.company.deliverynote.service.DeliveryNoteService;
import com.dongyu.company.finance.dao.MiPriceDao;
import com.dongyu.company.finance.domain.MiPrice;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.register.domain.MiRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 货款单Service实现类
 *
 * @author TYF
 * @date 2018/12/4
 * @since 1.0.0
 */
@Slf4j
@Service
public class DeliveryNoteServiceImpl implements DeliveryNoteService {
    @Autowired
    private DeliveryNoteDao deliveryNoteDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MiPriceDao miPriceDao;

    @Override
    @Transactional
    public DeliveryListDTO add(AddDeliveryNoteDTO dto) {
        log.info("DeliveryNoteServiceImpl add method start Parm:" + JSONObject.toJSONString(dto));
        Order order = orderDao.findByCommissioningCode(dto.getCommissioningCode());
        if (order == null) {
            throw new BizException("改投产单号不存在下单，请核实填写！");
        }
        //送货数量
        Integer deliveryNum = Integer.valueOf(dto.getDeliveryNum());
        //更新下单记录
        order = this.updateOrder(order, deliveryNum);
        DeliveryNote deliveryNote = new DeliveryNote();
        BeanUtils.copyProperties(dto, deliveryNote);
        //送货日期时间转换
        deliveryNote.setDeliveryDate(DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //将mi信息赋值给货款单
        deliveryNote = this.copy(order, deliveryNote, deliveryNum);
        orderDao.save(order);
        DeliveryNote save = deliveryNoteDao.save(deliveryNote);

        DeliveryListDTO deliveryListDTO = new DeliveryListDTO();
        BeanUtils.copyProperties(save, deliveryListDTO);
        //送货日期
        if (save.getDeliveryDate() != null) {
            deliveryListDTO.setDeliveryDate(DateUtil.parseDateToStr(save.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        }
        log.info("DeliveryNoteServiceImpl add method end;");
        return deliveryListDTO;
    }

    @Override
    @Transactional
    public void edit(EditDeliveryDTO dto) {
        log.info("DeliveryNoteServiceImpl edit method start Parm:" + JSONObject.toJSONString(dto));
        DeliveryNote deliveryNote = deliveryNoteDao.findOne(dto.getId());
        if (deliveryNote == null) {
            throw new BizException("该货款单不存在！");
        }
        //货款开单编辑逻辑
        if (deliveryNote.getBillingType() == BillingTypeEnum.PAYMENT_TYPE.getValue()) {
            if (StringUtils.isEmpty(dto.getCommissioningCode())) {
                throw new BizException("投产单号不能为空！");
            }
            //投产单号和送货数量没修改直接赋值
            if (dto.getCommissioningCode().equals(deliveryNote.getCommissioningCode()) &&
                    dto.getDeliveryNum().equals(deliveryNote.getDeliveryNum())) {
                deliveryNote.setDeliveryRemarks(dto.getDeliveryRemarks());
            }

            //投产单号修改（送货数量修改或没修改）
            if (!dto.getCommissioningCode().equals(deliveryNote.getCommissioningCode())) {
                //原来下单回退
                Order oldOrder = orderDao.findByCommissioningCode(deliveryNote.getCommissioningCode());
                oldOrder = this.backOrder(oldOrder, deliveryNote);
                orderDao.save(oldOrder);
                //新对应下单跟新
                Order newOrder = orderDao.findByCommissioningCode(dto.getCommissioningCode());
                //送货数量
                Integer deliveryNum = Integer.valueOf(dto.getDeliveryNum());
                newOrder = this.updateOrder(newOrder, deliveryNum);
                orderDao.save(newOrder);
                BeanUtils.copyProperties(dto, deliveryNote);
                deliveryNote = this.copy(newOrder, deliveryNote, deliveryNum);
            }
            //投产单号未修改但送货数量修改
            if (dto.getCommissioningCode().equals(deliveryNote.getCommissioningCode()) &&
                    !dto.getDeliveryNum().equals(deliveryNote.getDeliveryNum())) {
                //原来下单回退
                Order oldOrder = orderDao.findByCommissioningCode(deliveryNote.getCommissioningCode());
                oldOrder = this.backOrder(oldOrder, deliveryNote);
                orderDao.save(oldOrder);
                //新对应下单跟新
                Order newOrder = orderDao.findByCommissioningCode(dto.getCommissioningCode());

                deliveryNote.setDeliveryNum(dto.getDeliveryNum());
                deliveryNote.setDeliveryRemarks(dto.getDeliveryRemarks());
            }
        } else { //其它收费开单编辑逻辑
            BeanUtils.copyProperties(dto, deliveryNote);
        }
        //送货日期时间转换
        deliveryNote.setDeliveryDate(DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        deliveryNoteDao.save(deliveryNote);
        log.info("DeliveryNoteServiceImpl edit method end;");
    }

    @Override
    public List<DeliveryDetailDTO> getExportList(DeliveryQueryDTO deliveryQueryDTO) {
        log.info("DeliveryNoteServiceImpl getExportList method start Parm:" + JSONObject.toJSONString(deliveryQueryDTO));
        List<DeliveryNote> deliveryNotes = deliveryNoteDao.findAll(DeliverySpecs.orederQuerySpec(deliveryQueryDTO));
        if (CollectionUtils.isEmpty(deliveryNotes)) {
            return null;
        }
        List<DeliveryDetailDTO> detailDTOList = deliveryNotes.stream().map(deliveryNote -> {
            DeliveryDetailDTO deliveryDetailDTO = new DeliveryDetailDTO();
            BeanUtils.copyProperties(deliveryNote, deliveryDetailDTO);
            //送货日期
            deliveryDetailDTO.setDeliveryDate(DateUtil.parseDateToStr(deliveryNote.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return deliveryDetailDTO;
        }).collect(Collectors.toList());
        log.info("DeliveryNoteServiceImpl getExportList method end;");
        return detailDTOList;
    }

    @Override
    @Transactional
    public void addOtherDelivery(AddOtherDeliveryNoteDTO dto) {
        log.info("DeliveryNoteServiceImpl addOtherDelivery method start Parm:" + JSONObject.toJSONString(dto));
        DeliveryNote deliveryNote = new DeliveryNote();
        BeanUtils.copyProperties(dto, deliveryNote);
        //开单类型
        deliveryNote.setBillingType(BillingTypeEnum.OTHER_CHARGES_TYPE.getValue());
        //送货日期时间转换
        deliveryNote.setDeliveryDate(DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        deliveryNoteDao.save(deliveryNote);
        log.info("DeliveryNoteServiceImpl addOtherDelivery method end;");
    }

    @Override
    public PageDTO<DeliveryListDTO> getlist(DeliveryQueryDTO dto) {
        log.info("DeliveryNoteServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<DeliveryNote> page = deliveryNoteDao.findAll(DeliverySpecs.orederQuerySpec(dto), pageRequest);

        PageDTO<DeliveryListDTO> pageDTO = PageDTO.of(page, item -> {
            DeliveryListDTO deliveryListDTO = new DeliveryListDTO();
            BeanUtils.copyProperties(item, deliveryListDTO);
            //送货日期
            if (item.getDeliveryDate() != null) {
                deliveryListDTO.setDeliveryDate(DateUtil.parseDateToStr(item.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            }
            return deliveryListDTO;
        });
        log.info("DeliveryNoteServiceImpl getlist method end;");
        return pageDTO;
    }

    @Override
    @Transactional
    public void deleted(Long id) {
        log.info("DeliveryNoteServiceImpl deleted method start Parm:" + id);
        DeliveryNote deliveryNote = deliveryNoteDao.findOne(id);
        if (deliveryNote == null) {
            throw new BizException("该送货单已不存在！");
        }
        //先判断该送货单记录是货款开单还是其它收费开单，如果是货款开单，对应的下单记录完成和未完成数量需要回退，然后该送货单记录作废；
        // 如果是其它收费开单直接将该送货单记录作废；
        String commissioningCode = deliveryNote.getCommissioningCode();
        if (StringUtils.isNotBlank(commissioningCode)) {
            Order order = orderDao.findByCommissioningCode(commissioningCode);
            if (order != null) {
                order = this.backOrder(order, deliveryNote);
                orderDao.save(order);
            }
        }
        //将该送货单记录设置为作废状态
        deliveryNote.setDeleted(DeletedEnum.DELETED.getValue());
        deliveryNoteDao.save(deliveryNote);
        log.info("DeliveryNoteServiceImpl deleted method end;");
    }

    @Override
    public DeliveryDetailDTO getDetail(Long id) {
        log.info("DeliveryNoteServiceImpl getDetail method start Parm:" + id);
        DeliveryNote deliveryNote = deliveryNoteDao.findOne(id);
        if (deliveryNote == null) {
            throw new BizException("该货款单不存在！");
        }
        DeliveryDetailDTO deliveryDetailDTO = new DeliveryDetailDTO();
        BeanUtils.copyProperties(deliveryNote, deliveryDetailDTO);
        //返回不可编辑的字段
        if (deliveryNote.getBillingType() == BillingTypeEnum.PAYMENT_TYPE.getValue()) {
            List<String> nonEdit = new ArrayList<>();
            Field[] fields = DeliveryDetailDTO.class.getDeclaredFields();
            for (int i = 5; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                nonEdit.add(fieldName);
            }
            deliveryDetailDTO.setNonEditable(nonEdit);
        }
        log.info("DeliveryNoteServiceImpl getDetail method end;");
        return deliveryDetailDTO;
    }

    //下单记录完成数量、未完成数量、完成状态回退
    private Order backOrder(Order order, DeliveryNote deliveryNote) {
        log.info("DeliveryNoteServiceImpl backOrder method start :");
        //送货数量
        Integer deliveryNum = Integer.valueOf(deliveryNote.getDeliveryNum());
        //回退下单已完成数量记录
        Integer completedNum = Integer.valueOf(order.getCompletedNum()) - deliveryNum;
        order.setCompletedNum(String.valueOf(completedNum));
        //回退下单未完成数量记录
        Integer uncompletedNum = Integer.valueOf(order.getUncompletedNum()) + deliveryNum;
        order.setUncompletedNum(String.valueOf(uncompletedNum));
        //更新下单是否完成状态
        if (order.getUncompletedNum().equals(Constants.COMPLETED_NUM)) {
            order.setCompleteState(CompleteStateEnum.COMPLETE.getValue());
        } else {
            order.setCompleteState(CompleteStateEnum.UNCOMPLETE.getValue());
        }
        log.info("DeliveryNoteServiceImpl backOrder method end :");
        return order;
    }

    //下单记录完成数量、未完成数量、完成状态更新
    private Order updateOrder(Order order, Integer deliveryNum) {
        log.info("DeliveryNoteServiceImpl updateOrder method start :");
        //未完成数量
        Integer uncompletedNum = Integer.valueOf(order.getUncompletedNum());
        if (uncompletedNum < deliveryNum) {
            throw new BizException("送货数量超出下单未完成数量，请核实填写！");
        }
        //更新下单已完成数量记录
        Integer completedNum = Integer.valueOf(order.getCompletedNum()) + deliveryNum;
        order.setCompletedNum(String.valueOf(completedNum));
        //更新下单未完成数量记录
        uncompletedNum = uncompletedNum - deliveryNum;
        order.setUncompletedNum(String.valueOf(uncompletedNum));
        //下单完成更新下单是否完成状态为完成
        if (order.getUncompletedNum().equals(Constants.COMPLETED_NUM)) {
            order.setCompleteState(CompleteStateEnum.COMPLETE.getValue());
        }
        order.setChargeOpening(CurrencyEunm.YES.getValue());
        log.info("DeliveryNoteServiceImpl updateOrder method end :");
        return order;
    }

    //将MI信息赋值到下单数据上来
    private DeliveryNote copy(Order order, DeliveryNote deliveryNote, Integer deliveryNum) {
        log.info("DeliveryNoteServiceImpl copy method start :");
        //mi登记记录
        MiRegister miRegister = order.getMiRegister();
        //赋值客户信息
        BeanUtils.copyProperties(miRegister, deliveryNote);
        //客户订单号
        deliveryNote.setCustomerOrderCode(order.getCustomerOrderCode());
        MiPrice miPrice = miPriceDao.findOne(miRegister.getMiPriceId());
        if (miPrice != null) {
            //单价（单位分）
            deliveryNote.setPrice(miPrice.getPrice());
            //金额(单位分)
            deliveryNote.setAmount(String.valueOf(deliveryNum * Integer.valueOf(miPrice.getPrice())));
        }
        //开单类型
        deliveryNote.setBillingType(BillingTypeEnum.PAYMENT_TYPE.getValue());
        //单位
        //deliveryNote.setDeliveryUnit();
        log.info("DeliveryNoteServiceImpl copy method end :");
        return deliveryNote;
    }
}
