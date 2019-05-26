package com.dongyu.company.deliverynote.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.BillingTypeEnum;
import com.dongyu.company.common.constants.ChargeTypeEnum;
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
import com.dongyu.company.mould.dao.PurchaseMouldDao;
import com.dongyu.company.mould.domain.PurchaseMould;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.dao.OrderTemplateDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.order.domain.OrderTemplate;
import com.dongyu.company.register.dao.RegisterDao;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 货款单Service实现类
 * 警告：model类之间不要使用BeanUtils.copyProperties
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
    @Autowired
    private PurchaseMouldDao purchaseMouldDao;
    @Autowired
    private OrderTemplateDao orderTemplateDao;
    @Autowired
    private RegisterDao registerDao;

    @Override
    @Transactional
    public List<DeliveryListDTO> add(List<AddDeliveryNoteDTO> dtos) {
        log.info("DeliveryNoteServiceImpl add method start Parm:" + JSONObject.toJSONString(dtos));
        List<DeliveryListDTO> deliveryListDTOs = new ArrayList<>();
        //生成送货单号（同一批次开单送货单号一样）
        Long num = 1L;
        String deliveryCode = null;
        //DeliveryNote deliveryNote1 = deliveryNoteDao.findFirstByOrderByCreateTimeDesc();
        DeliveryNote deliveryNote1 = deliveryNoteDao.findNewest();
        if (deliveryNote1 == null) {
            deliveryCode = String.valueOf(num);
        } else {
            String deliveryCode1 = deliveryNote1.getDeliveryCode();
            if (StringUtils.isEmpty(deliveryCode1)) {
                deliveryCode = String.valueOf(num);
            } else {
                Long aLong = Long.valueOf(deliveryCode1);
                aLong = aLong + 1L;
                deliveryCode = String.valueOf(aLong);
            }
        }
        //根据投产单号查询未收费开单的下单
        for (AddDeliveryNoteDTO dto : dtos) {
            Order order = orderDao.findByCommissioningCodeAndChargeOpening(dto.getCommissioningCode(), CurrencyEunm.NO.getValue());
            if (order == null) {
                throw new BizException(dto.getDyCode() + "该DY编号不存在未收费开单的下单，请核实！");
            }
            //送货数量
            Long deliveryNum = Long.valueOf(dto.getDeliveryNum());
            //更新下单记录
            order = this.updateOrder(order, deliveryNum);
            DeliveryNote deliveryNote = new DeliveryNote();
            deliveryNote.setChargeType(ChargeTypeEnum.ORDER_TYPE.getValue());//订单收费
            BeanUtils.copyProperties(dto, deliveryNote);
            //送货日期时间转换
            Date deliveryDate = DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
            deliveryNote.setDeliveryDate(deliveryDate);
            //对账月份（为送货日期的下个月）
            deliveryNote.setBillMonth(DateUtil.getYearMonthDate(deliveryDate, DateUtil.DATE_FORMAT_YYYYMM));
            //将mi信息赋值给货款单
            deliveryNote = this.copy(order, deliveryNote, deliveryNum);
            orderDao.save(order);
            //送货单号
            deliveryNote.setDeliveryCode(deliveryCode);
            deliveryNoteDao.save(deliveryNote);
            DeliveryListDTO deliveryListDTO = new DeliveryListDTO();
            BeanUtils.copyProperties(deliveryNote, deliveryListDTO);
            //送货日期
            if (deliveryNote.getDeliveryDate() != null) {
                deliveryListDTO.setDeliveryDate(DateUtil.parseDateToStr(deliveryNote.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            }
            deliveryListDTOs.add(deliveryListDTO);
            log.info("DeliveryNoteServiceImpl add method end;");
        }
        String customerName = deliveryListDTOs.get(0).getCustomerName();
        for (DeliveryListDTO dto : deliveryListDTOs) {
            if (!dto.getCustomerName().equals(customerName)) {
                throw new BizException("不属于同一客户的数据不能一起开单，请核实！");
            }
        }
        return deliveryListDTOs;
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
            if (dto.getCommissioningCode().equals(deliveryNote.getCommissioningCode()) && dto.getDeliveryNum().equals(deliveryNote.getDeliveryNum())) {
                deliveryNote.setDeliveryRemarks(dto.getDeliveryRemarks());
            }

            //投产单号修改（送货数量修改或没修改）
            if (!dto.getCommissioningCode().equals(deliveryNote.getCommissioningCode())) {
                //根据投产单号查询未收费开单的下单
                Order order = orderDao.findByCommissioningCodeAndChargeOpening(dto.getCommissioningCode(), CurrencyEunm.NO.getValue());
                if (order == null) {
                    throw new BizException(dto.getCommissioningCode() + "该投产单号不存在未收费开单的下单，请核实！");
                }
                //原来下单回退
                Order oldOrder = orderDao.findByCommissioningCode(deliveryNote.getCommissioningCode());
                oldOrder = this.backOrder(oldOrder, deliveryNote);
                orderDao.save(oldOrder);
                //新对应下单跟新
                Order newOrder = orderDao.findByCommissioningCode(dto.getCommissioningCode());
                //送货数量
                Long deliveryNum = Long.valueOf(dto.getDeliveryNum());
                newOrder = this.updateOrder(newOrder, deliveryNum);
                orderDao.save(newOrder);
                BeanUtils.copyProperties(dto, deliveryNote);
                deliveryNote = this.copy(newOrder, deliveryNote, deliveryNum);
            }
            //投产单号未修改但送货数量修改
            if (dto.getCommissioningCode().equals(deliveryNote.getCommissioningCode()) && !dto.getDeliveryNum().equals(deliveryNote.getDeliveryNum())) {
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
        Date deliveryDate = DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
        deliveryNote.setDeliveryDate(deliveryDate);
        //对账月份（为送货日期的下个月）
        deliveryNote.setBillMonth(DateUtil.getYearMonthDate(deliveryDate, DateUtil.DATE_FORMAT_YYMM));
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
    public List<DeliveryListDTO> getPrintDeliveryNote(List<String> ids) {
        log.info("DeliveryNoteServiceImpl getPrintOrder method start Parm:" + JSONObject.toJSONString(ids));
        List<DeliveryListDTO> listDTOS = null;
        if (CollectionUtils.isEmpty(ids)) {
            throw new BizException("未选择要打印的数据！");
        }
        if (ids.size() > 10) {
            throw new BizException("一次打印不能超过十条！");
        }
        List<Long> collect = ids.stream().map(str -> {
            Long aLong = Long.valueOf(str);
            return aLong;
        }).collect(Collectors.toList());
        List<DeliveryNote> all = deliveryNoteDao.findAll(collect);
        if (CollectionUtils.isNotEmpty(all)) {
            listDTOS = all.stream().map(deliveryNote -> {
                DeliveryListDTO dto = new DeliveryListDTO();
                BeanUtils.copyProperties(deliveryNote, dto);
                dto.setDeliveryDate(DateUtil.parseDateToStr(deliveryNote.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
                return dto;
            }).collect(Collectors.toList());
            String deliveryCode = all.get(0).getDeliveryCode();
            for (DeliveryNote deliveryNote : all) {
                if (!deliveryNote.getDeliveryCode().equals(deliveryCode)) {
                    throw new BizException("不属于同一送货单的数据不能一起打印送货单，请核实！！");
                }
            }
        }
        log.info("DeliveryNoteServiceImpl getPrintOrder method end;");
        return listDTOS;
    }

    @Override
    @Transactional
    public void addOtherDelivery(AddOtherDeliveryNoteDTO dto) {
        log.info("DeliveryNoteServiceImpl addOtherDelivery method start Parm:" + JSONObject.toJSONString(dto));
        Integer chargeType = dto.getChargeType();
        String dyCode = dto.getMiDyCode();
        if (chargeType == ChargeTypeEnum.MOULD_TYPE.getValue()) {//模具收费
            if (StringUtils.isBlank(dyCode)) {
                throw new BizException("开单类型为模具开单时，DY编号不能为空!");
            }
            //根据DY编号查询收费且没有收费开单的模具
            PurchaseMould mould = purchaseMouldDao.findByDyCodeAndChargeAndChargeOpeningAndDeleted(dto.getMiDyCode(), CurrencyEunm.YES.getValue(), CurrencyEunm.NO.getValue(), DeletedEnum.UNDELETED.getValue());
            if (mould == null) {
                throw new BizException("该DY编号不存在收费且没有收费开单的模具");
            }
            mould.setChargeOpening(CurrencyEunm.YES.getValue());
        } else if (chargeType == ChargeTypeEnum.TEMPLATE_TYPE.getValue()) {//样板收费
            if (StringUtils.isBlank(dyCode)) {
                throw new BizException("开单类型为样板开单时，DY编号不能为空!");
            }
            //根据DY编号查询未收费开单的样板
            OrderTemplate orderTemplate = orderTemplateDao.findByDyCodeAndChargeOpening(dyCode, CurrencyEunm.NO.getValue());
            if (orderTemplate == null) {
                throw new BizException("该DY编号不存在未收费开单的样板");
            }
            orderTemplate.setChargeOpening(CurrencyEunm.YES.getValue());
        } else {//其它收费

        }

        DeliveryNote deliveryNote = new DeliveryNote();
        BeanUtils.copyProperties(dto, deliveryNote);
        //设置为其它收费开单类型
        deliveryNote.setBillingType(BillingTypeEnum.OTHER_CHARGES_TYPE.getValue());
        //送货日期时间转换
        Date deliveryDate = DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
        deliveryNote.setDeliveryDate(deliveryDate);
        //对账月份（为送货日期的下个月）
        deliveryNote.setBillMonth(DateUtil.getYearMonthDate(deliveryDate, DateUtil.DATE_FORMAT_YYMM));
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
        //判读是否是第二次删除，如果是再次删除为物理删除
        Integer deleted = deliveryNote.getDeleted();
        if (deleted == DeletedEnum.UNDELETED.getValue()) {
            //先判断该送货单记录是货款开单还是其它收费开单，如果是货款开单，对应的下单记录完成和未完成数量需要回退，然后该送货单记录作废；
            // 如果是其它收费开单直接将该送货单记录作废；
            Integer billingType = deliveryNote.getBillingType();
            if (billingType == BillingTypeEnum.PAYMENT_TYPE.getValue()) {
                String commissioningCode = deliveryNote.getCommissioningCode();
                if (StringUtils.isNotBlank(commissioningCode)) {
                    Order order = orderDao.findByCommissioningCode(commissioningCode);
                    if (order != null) {
                        order = this.backOrder(order, deliveryNote);
                        orderDao.save(order);
                    }
                }
            }
            //将该送货单记录设置为作废状态
            deliveryNote.setDeleted(DeletedEnum.DELETED.getValue());
            deliveryNoteDao.save(deliveryNote);
        } else {
            deliveryNoteDao.delete(deliveryNote.getId());
        }
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
        //送货日期
        deliveryDetailDTO.setDeliveryDate(DateUtil.parseDateToStr(deliveryNote.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //开单类型转换
        deliveryDetailDTO.setChargeType(String.valueOf(deliveryNote.getBillingType()));
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
    private Order updateOrder(Order order, Long deliveryNum) {
        log.info("DeliveryNoteServiceImpl updateOrder method start :");
        //未完成数量
        Long uncompletedNum = Long.valueOf(order.getUncompletedNum());
        if (uncompletedNum < deliveryNum) {
            throw new BizException("送货数量超出下单未完成数量，请核实填写！");
        }
        //更新下单已完成数量记录
        Long completedNum = Long.valueOf(order.getCompletedNum()) + deliveryNum;
        order.setCompletedNum(String.valueOf(completedNum));
        //更新下单未完成数量记录
        uncompletedNum = uncompletedNum - deliveryNum;
        order.setUncompletedNum(String.valueOf(uncompletedNum));
        //下单完成更新下单是否完成状态为完成
        if (order.getUncompletedNum().equals(Constants.COMPLETED_NUM)) {
            order.setCompleteState(CompleteStateEnum.COMPLETE.getValue());
        }
        //下单已完成将该订单设置成已完成收费开单
        if (uncompletedNum == 0L) {
            order.setChargeOpening(CurrencyEunm.YES.getValue());
        }
        log.info("DeliveryNoteServiceImpl updateOrder method end :");
        return order;
    }

    //将MI信息赋值到下单数据上来
    private DeliveryNote copy(Order order, DeliveryNote deliveryNote, Long deliveryNum) {
        log.info("DeliveryNoteServiceImpl copy method start :");
        //mi登记记录
        MiRegister miRegister = registerDao.findOne(order.getMiRegisterId());
        //赋值客户信息
        deliveryNote.setCustomerName(miRegister.getCustomerName());
        deliveryNote.setCustomerModel(miRegister.getCustomerModel());
        deliveryNote.setMiDyCode(miRegister.getMiDyCode());
        //客户订单号
        deliveryNote.setCustomerOrderCode(order.getCustomerOrderCode());
        if (miRegister.getMiPriceId() == null) {
            throw new BizException(miRegister.getMiDyCode() + "该DY编号的MI登记价格未录入，请先录入价格！");
        }
        MiPrice miPrice = miPriceDao.findOne(miRegister.getMiPriceId());
        if (miPrice != null) {
            //单价（单位分）
            deliveryNote.setPrice(miPrice.getPrice());
            //金额(单位分)
            deliveryNote.setAmount(String.valueOf(deliveryNum * Long.valueOf(miPrice.getPrice())));
        }
        //开单类型
        deliveryNote.setBillingType(BillingTypeEnum.PAYMENT_TYPE.getValue());
        //单位
        //deliveryNote.setDeliveryUnit();
        log.info("DeliveryNoteServiceImpl copy method end :");
        return deliveryNote;
    }

    /**
     * 生成送货单号，格式为日期yyMMdd+4位自增流水号(1811270001)，流水号按天每天重新计数
     *
     * @return
     */
    private String createDeliveryCode() {
        //当前日期格式，年月日，例如：050630
        String dateToStr = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYMMDD);
        Order order = orderDao.findFirstByOrderByCreateTimeDesc();
        //要返回的投产单号
        String code = null;
        if (order == null) {
            code = String.format(dateToStr + "%04d", 1);
            return code;
        }
        String commissioningCode = order.getCommissioningCode();
        String date = commissioningCode.substring(0, 6);
        String number = commissioningCode.substring(6, 10);
        if (dateToStr.equals(date)) {
            //新增的下单和前一次下单是同一天，计数累加1
            Integer integer = Integer.valueOf(number);
            integer += 1;
            code = String.format(date + "%04d", integer);
        } else {
            //新增的下单和前一次下单不是同一天，计数重新从1开始
            code = String.format(dateToStr + "%04d", 1);
        }
        return code;
    }

}
