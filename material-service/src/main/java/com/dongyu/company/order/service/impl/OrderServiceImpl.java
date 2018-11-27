package com.dongyu.company.order.service.impl;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.order.dto.AddOrderDTO;
import com.dongyu.company.order.dto.OrderDetailDTO;
import com.dongyu.company.order.dto.OrderListDTO;
import com.dongyu.company.order.dto.OrderQueryDTO;
import com.dongyu.company.order.service.OrderService;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.domain.MiRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 下单Service实现
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RegisterDao registerDao;

    @Override
    @Transactional
    public void add(AddOrderDTO addOrderDTO) {
        log.info("OrderServiceImpl add method start：");
        //用填入的下单DY编号去查询MI登记
        MiRegister byMiDyCode = registerDao.findByMiDyCode(addOrderDTO.getOrderDyCode());
        if (byMiDyCode == null) {
            throw new BizException("该DY编号没有对应的MI登记，请核实!");
        }
        Order order = new Order();
        BeanUtils.copyProperties(addOrderDTO, order);
        order.setOrderDate(DateUtil.parseStrToDate(addOrderDTO.getOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        order.setDeliveryDate(DateUtil.parseStrToDate(addOrderDTO.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //自动生成投产单号
        order.setCommissioningCode(this.createCode());
        orderDao.save(order);
        log.info("OrderServiceImpl add method end：");
    }

    //生成投产单号，格式为日期yyMMdd+4位自增流水号(1811270001)，流水号按天每天重新计数
    private String createCode() {
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


    @Override
    public PageDTO<OrderListDTO> getlist(OrderQueryDTO orderQueryDTO) {


        return null;
    }

    @Override
    public void deleted(Long id) {

    }

    @Override
    public OrderDetailDTO getDetail(Long id) {
        return null;
    }


}
