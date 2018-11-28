package com.dongyu.company.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.file.dao.FileDao;
import com.dongyu.company.file.domian.CommonFile;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.dao.SurplusDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.order.dao.OrderSpecs;
import com.dongyu.company.order.domain.Surplus;
import com.dongyu.company.order.dto.AddOrderDTO;
import com.dongyu.company.order.dto.AddOrderResultDTO;
import com.dongyu.company.order.dto.AddSurplusDTO;
import com.dongyu.company.order.dto.EditOrderDTO;
import com.dongyu.company.order.dto.OrderDetailDTO;
import com.dongyu.company.order.dto.OrderListDTO;
import com.dongyu.company.order.dto.OrderQueryDTO;
import com.dongyu.company.order.service.OrderService;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.domain.MiRegister;
import com.dongyu.company.register.dto.RegisterDetailDTO;
import com.dongyu.company.register.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private FileDao fileDao;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private SurplusDao surplusDao;

    @Override
    @Transactional
    public AddOrderResultDTO add(AddOrderDTO addOrderDTO) {
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
        order.setMiRegister(byMiDyCode);
        //订单数
        Integer orderNum = Integer.valueOf(addOrderDTO.getOrderNum());
        //备品率
        double parseDouble = Double.parseDouble(addOrderDTO.getSparePartsRate());
        //生成投产数量（规则：订单数+订单数*备品率）
        order.setCommissioningNum(String.valueOf(Math.ceil(orderNum + orderNum * parseDouble)));
        //备品数
        order.setSparePartsNum(String.valueOf(orderNum * parseDouble));
        //生成平方数(规则：模片尺寸相乘/一模出几/1000000再乘以订单数)
        //order.setSquareNum();
        Order save = orderDao.save(order);
        //新增完返回余料处理需要的数据
        AddOrderResultDTO resultDTO = new AddOrderResultDTO();
        resultDTO.setId(save.getId());
        //返回图片信息
        if (save.getMiRegister().getCommonFileId() != null) {
            CommonFile commonFile = fileDao.findOne(save.getMiRegister().getCommonFileId());
            if (commonFile != null) {
                resultDTO.setFileName(commonFile.getFileName());
                resultDTO.setCommonFileId(commonFile.getId());
            }
        }
        //返回PCS数
        resultDTO.setPcsNumber(save.getMiRegister().getPcsNumber());
        //返回共用料张数(计算规则：)
        //resultDTO.setHaredMaterialsNum();
        //返回余下张数(计算规则：)
        //resultDTO.setRemainNum();
        log.info("OrderServiceImpl add method end;");
        return resultDTO;
    }

    @Override
    @Transactional
    public void addSurplus(AddSurplusDTO dto) {
        log.info("OrderServiceImpl addSurplus method start Parm:" + JSONObject.toJSONString(dto));
        if (dto.getId() == null) {
            throw new BizException("下单ID不能为空！");
        }
        Order order = orderDao.findOne(dto.getId());
        if (order == null) {
            throw new BizException("下单ID数据不存在！");
        }
        //存储余料处理数据
        Surplus surplus = new Surplus();
        BeanUtils.copyProperties(dto, surplus);
        Surplus save = surplusDao.save(surplus);
        //将余料处理数据与订单关联
        order.setSurplus(save);
        orderDao.save(order);
        log.info("OrderServiceImpl addSurplus method end;");
    }

    @Override
    public PageDTO<OrderListDTO> getlist(OrderQueryDTO dto) {
        log.info("OrderServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<Order> page = orderDao.findAll(OrderSpecs.orederQuerySpec(dto), pageRequest);

        PageDTO<OrderListDTO> pageDTO = PageDTO.of(page, item -> {
            OrderListDTO orderListDTO = new OrderListDTO();
            BeanUtils.copyProperties(item, orderListDTO);
            MiRegister miRegister = item.getMiRegister();
            //客户名称
            orderListDTO.setCustomerName(miRegister.getCustomerName());
            //客户 型号
            orderListDTO.setCustomerModel(miRegister.getCustomerModel());
            //下单日期
            orderListDTO.setOrderDate(DateUtil.parseDateToStr(item.getOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //交货日期
            orderListDTO.setDeliveryDate(DateUtil.parseDateToStr(item.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return orderListDTO;
        });
        log.info("OrderServiceImpl getlist method end;");
        return pageDTO;
    }

    @Override
    public void deleted(Long id) {
        log.info("OrderServiceImpl deleted method start Parm:" + id);
        Order order = orderDao.findOne(id);
        if (order == null) {
            throw new BizException("该订单已不存在！");
        }
        order.setDeleted(DeletedEnum.DELETED.getValue());
        orderDao.save(order);
        log.info("OrderServiceImpl deleted method end;");
    }

    @Override
    public void edit(EditOrderDTO dto) {
        log.info("OrderServiceImpl edit method start Parm:" + JSONObject.toJSONString(dto));


        log.info("OrderServiceImpl edit method end;");
    }

    @Override
    public OrderDetailDTO getDetail(Long id) {
        log.info("OrderServiceImpl getDetail method start Parm:" + id);
        Order order = orderDao.findOne(id);
        if (order == null) {
            throw new BizException("该订单已不存在！");
        }
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        BeanUtils.copyProperties(order, orderDetailDTO);
        //下单日期
        orderDetailDTO.setOrderDate(DateUtil.parseDateToStr(order.getOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //交货日期
        orderDetailDTO.setDeliveryDate(DateUtil.parseDateToStr(order.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //线路印次
        //orderDetailDTO.setLineImpression();
        //定位孔数
        // orderDetailDTO.setLocatingNum();
        //绿油印次
        //orderDetailDTO.setGreenOilImpression();
        //文字印次
        //  orderDetailDTO.setWordsImpression();
        //碳桥印次
        // orderDetailDTO.setCarbonBridgeImpression();
        //其它印次
        // orderDetailDTO.setOtherImpression();
        //冲床冲次
        //  orderDetailDTO.setPunch();

        //余料处理数据返回
        Surplus surplus = order.getSurplus();
        if (surplus != null) {
            BeanUtils.copyProperties(surplus, orderDetailDTO);
        }
        //MI登记详情返回数据
        MiRegister miRegister = order.getMiRegister();
        if (miRegister != null) {
            RegisterDetailDTO detail = registerService.getDetail(miRegister.getId());
            orderDetailDTO.setRegisterDetailDTO(detail);
        }
        log.info("OrderServiceImpl getDetail method end;");
        return orderDetailDTO;
    }

    @Override
    public OrderDetailDTO getPrintOrder(Long id) {
        log.info("OrderServiceImpl getPrintOrder method start Parm:" + id);
        Order order = orderDao.findOne(id);
        if (order == null) {
            throw new BizException("该订单已不存在！");
        }
        Surplus surplus = order.getSurplus();
        if (surplus == null) {
            throw new BizException("该订单余料处理未填写！");
        }
        OrderDetailDTO detailDTO = this.getDetail(id);

        log.info("OrderServiceImpl getPrintOrder method end;");
        return detailDTO;
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
}
