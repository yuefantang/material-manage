package com.dongyu.company.deliverynote.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.CompleteStateEnum;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.deliverynote.dao.DeliveryNoteDao;
import com.dongyu.company.deliverynote.dao.DeliverySpecs;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.dto.AddDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.AddOtherDeliveryNoteDTO;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.deliverynote.service.DeliveryNoteService;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.register.domain.MiRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void add(AddDeliveryNoteDTO dto) {
        log.info("DeliveryNoteServiceImpl add method start Parm:" + JSONObject.toJSONString(dto));
        Order order = orderDao.findByCommissioningCode(dto.getCommissioningCode());
        if (order == null) {
            throw new BizException("改投产单号不存在下单，请核实填写！");
        }

        //送货数量
        Integer deliveryNum = Integer.valueOf(dto.getDeliveryNum());
        //未完成数量
        Integer uncompletedNum = Integer.valueOf(order.getUncompletedNum());
        if (uncompletedNum < deliveryNum) {
            throw new BizException("送货数量超出下单未完成数量，请核实填写！");
        }
        //mi登记记录
        MiRegister miRegister = order.getMiRegister();

        DeliveryNote deliveryNote = new DeliveryNote();
        BeanUtils.copyProperties(dto, deliveryNote);
        //赋值客户信息
        BeanUtils.copyProperties(miRegister, deliveryNote);
        //送货日期时间转换
        deliveryNote.setDeliveryDate(DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //客户订单号
        deliveryNote.setCustomerOrderCode(order.getCustomerOrderCode());
        //单价（单位分）
        //deliveryNote.setDeliveryPrice();
        //金额(单位分)
        // deliveryNote.setDeliveryAmount();
        //单位
        //deliveryNote.setDeliveryUnit();
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
        orderDao.save(order);
        deliveryNoteDao.save(deliveryNote);
        log.info("DeliveryNoteServiceImpl add method end;");
    }

    @Override
    @Transactional
    public void addOtherDelivery(AddOtherDeliveryNoteDTO dto) {
        log.info("DeliveryNoteServiceImpl addOtherDelivery method start Parm:" + JSONObject.toJSONString(dto));
        DeliveryNote deliveryNote = new DeliveryNote();
        BeanUtils.copyProperties(dto, deliveryNote);
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
                //送货数量
                Integer deliveryNum = Integer.valueOf(deliveryNote.getDeliveryNum());
                //回退下单已完成数量记录
                Integer completedNum = Integer.valueOf(order.getCompletedNum()) - deliveryNum;
                order.setCompletedNum(String.valueOf(completedNum));
                //回退下单未完成数量记录
                Integer uncompletedNum = Integer.valueOf(order.getUncompletedNum()) + deliveryNum;
                order.setUncompletedNum(String.valueOf(uncompletedNum));
                //下单完成更新下单是否完成状态为完成
                if (order.getUncompletedNum().equals(Constants.COMPLETED_NUM)) {
                    order.setCompleteState(CompleteStateEnum.COMPLETE.getValue());
                } else {
                    order.setCompleteState(CompleteStateEnum.UNCOMPLETE.getValue());
                }
                orderDao.save(order);
            }
        }
        //将该送货单记录设置为作废状态
        deliveryNote.setDeleted(DeletedEnum.DELETED.getValue());
        deliveryNoteDao.save(deliveryNote);
        log.info("DeliveryNoteServiceImpl deleted method end;");
    }
}
