package com.dongyu.company.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.CurrencyEunm;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.constants.OperationStateEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.file.dao.FileDao;
import com.dongyu.company.file.domian.CommonFile;
import com.dongyu.company.order.dao.OrderDao;
import com.dongyu.company.order.dao.OrderSpecs;
import com.dongyu.company.order.dao.PlusOrderDao;
import com.dongyu.company.order.dao.PlusOrderSpecs;
import com.dongyu.company.order.dao.SurplusDao;
import com.dongyu.company.order.domain.Order;
import com.dongyu.company.order.domain.PlusOrder;
import com.dongyu.company.order.domain.Surplus;
import com.dongyu.company.order.dto.AddOrderDTO;
import com.dongyu.company.order.dto.AddOrderResultDTO;
import com.dongyu.company.order.dto.AddPlusOrderDTO;
import com.dongyu.company.order.dto.AddSurplusDTO;
import com.dongyu.company.order.dto.EditOrderDTO;
import com.dongyu.company.order.dto.OrderDetailDTO;
import com.dongyu.company.order.dto.OrderListDTO;
import com.dongyu.company.order.dto.OrderQueryDTO;
import com.dongyu.company.order.dto.PlusOrderListDTO;
import com.dongyu.company.order.dto.PlusOrderQueryDTO;
import com.dongyu.company.order.service.OrderService;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.domain.MiRegister;
import com.dongyu.company.register.dto.RegisterDetailDTO;
import com.dongyu.company.register.service.RegisterService;
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

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private PlusOrderDao plusOrderDao;


    @Override
    @Transactional
    public AddOrderResultDTO add(AddOrderDTO addOrderDTO) {
        log.info("OrderServiceImpl add method start："+ JSONObject.toJSONString(addOrderDTO));
        //用填入的下单DY编号去查询没有删除的MI登记
        MiRegister byMiDyCode = registerDao.findByMiDyCodeAndDeleted(addOrderDTO.getOrderDyCode(), DeletedEnum.UNDELETED.getValue());
        if (byMiDyCode == null) {
            throw new BizException("该DY编号没有对应的MI登记或已删除，请核实!");
        }
        Integer production = byMiDyCode.getProduction();
        if (production == CurrencyEunm.NO.getValue()) {
            throw new BizException("该DY编号MI工程不允许投产，请核实！");
        }
        Order order = new Order();
        BeanUtils.copyProperties(addOrderDTO, order);
        order.setOrderDate(DateUtil.parseStrToDate(addOrderDTO.getOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        order.setDeliveryDate(DateUtil.parseStrToDate(addOrderDTO.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //新增下单已完成数量初始数据为"0"
        order.setCompletedNum(Constants.COMPLETED_NUM);
        //未完成数量初始数据为订单数量
        order.setUncompletedNum(addOrderDTO.getOrderNum());
        //自动生成投产单号
        order.setCommissioningCode(this.createCode());
        //数据操作状态为新增
        order.setOperationState(OperationStateEnum.ADD.getValue());
        //关联MI登记
        order.setMiRegister(byMiDyCode);
        log.info("OrderServiceImpl add method end;");
        return this.addAndEdit(order, addOrderDTO);
    }

    @Override
    public AddOrderResultDTO edit(EditOrderDTO dto) {
        log.info("OrderServiceImpl edit method start Parm:" + JSONObject.toJSONString(dto));
        String orderNum = dto.getOrderNum();
        if (dto.getId() == null) {
            throw new BizException("下单ID不能为空！");
        }
        Order order = orderDao.findOne(dto.getId());
        if (order == null) {
            throw new BizException("该订单不存在！");
        }
        if (!dto.getOrderDyCode().equals(order.getOrderDyCode())) {
            //用修改的下单DY编号去查询MI登记
            MiRegister byMiDyCode = registerDao.findByMiDyCode(dto.getOrderDyCode());
            if (byMiDyCode == null) {
                throw new BizException("该DY编号没有对应的MI登记，请核实!");
            }
            Integer production = byMiDyCode.getProduction();
            if (production == CurrencyEunm.NO.getValue()) {
                throw new BizException("该DY编号MI工程不允许投产，请核实！");
            }
            //关联MI登记
            order.setMiRegister(byMiDyCode);
        }
        BeanUtils.copyProperties(dto, order);
        order.setOrderDate(DateUtil.parseStrToDate(dto.getOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        order.setDeliveryDate(DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //未完成数量为订单数量减去已完成数量
        if (!orderNum.equals(order.getOrderNum())) {
            int uncompletedNum = Integer.valueOf(orderNum) - Integer.valueOf(order.getCompletedNum());
            order.setUncompletedNum(String.valueOf(uncompletedNum));
        }
        //数据操作状态为修改
        order.setOperationState(OperationStateEnum.UPDATE.getValue());
        log.info("OrderServiceImpl edit method end;");
        return this.addAndEdit(order, dto);
    }

    @Override
    public void recovery(Long id) {
        log.info("OrderServiceImpl recovery method start Parm:" + id);
        Order order = orderDao.findOne(id);
        if (order == null) {
            throw new BizException("不存在该下单id");
        }
        order.setDeleted(DeletedEnum.UNDELETED.getValue());
        orderDao.save(order);
        log.info("OrderServiceImpl recovery method end;");
    }

    @Override
    public List<OrderDetailDTO> getExportList(OrderQueryDTO orderQueryDTO) {
        log.info("OrderServiceImpl getExportList method start Parm:" + JSONObject.toJSONString(orderQueryDTO));
        List<Order> orderList = orderDao.findAll(OrderSpecs.orederQuerySpec(orderQueryDTO));
        if (CollectionUtils.isEmpty(orderList)) {
            return null;
        }
        List<OrderDetailDTO> detailDTOList = orderList.stream().map(order -> {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            BeanUtils.copyProperties(order, orderDetailDTO);
            MiRegister miRegister = order.getMiRegister();
            RegisterDetailDTO detail = registerService.getDetail(miRegister.getId());
            orderDetailDTO.setRegisterDetailDTO(detail);
            //下单日期
            orderDetailDTO.setOrderDate(DateUtil.parseDateToStr(order.getOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //交货日期
            orderDetailDTO.setDeliveryDate(DateUtil.parseDateToStr(order.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return orderDetailDTO;
        }).collect(Collectors.toList());
        log.info("OrderServiceImpl getExportList method end;");
        return detailDTOList;
    }

    @Override
    @Transactional
    public void addSurplus(AddSurplusDTO dto) {
        log.info("OrderServiceImpl addSurplus method start Parm:" + JSONObject.toJSONString(dto));
        if (dto.getOrderId() == null) {
            throw new BizException("下单ID不能为空！");
        }
        Order order = orderDao.findByIdAndDeleted(dto.getOrderId(), DeletedEnum.UNDELETED.getValue());
        if (order == null) {
            throw new BizException("下单ID数据不存在或已删除！");
        }
        //存储余料处理数据
        Surplus surplus = order.getSurplus();
        //不等于空，说明是下单编辑修改
        if (surplus == null) {
            surplus = new Surplus();
        }
        BeanUtils.copyProperties(dto, surplus);
        //与下单保持数据操作状态一致
        surplus.setOperationState(order.getOperationState());
        Surplus save = surplusDao.save(surplus);
        //将余料处理数据与订单关联
        if (order.getSurplus() == null) {//下单新增
            order.setSurplus(save);
            orderDao.save(order);
        }
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
            //余料处理数据
            orderListDTO.setDto(this.getSurplusResult(item));
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
        //余料处理数据返回
        Surplus surplus = order.getSurplus();
        if(surplus==null){
            throw new BizException("余料处理未填写！请完成");
        }
        if (surplus != null) {
            BeanUtils.copyProperties(surplus, orderDetailDTO);
        }
        //MI登记详情返回数据
        MiRegister miRegister = order.getMiRegister();
        Integer materialNum = null;//实际下料总数
        if (miRegister != null) {
            RegisterDetailDTO detail = registerService.getDetail(miRegister.getId());
            orderDetailDTO.setRegisterDetailDTO(detail);
            //下料总数=余料处理展示共用料张数整数部分+余料PCS/大料PCS
            //大料PCS数
            Integer pcsNumber = Integer.valueOf(order.getMiRegister().getPcsNumber());
            //余料PCS数
            Integer surplusPcs = Integer.valueOf(surplus.getSurplusPcs());
            //共用料张数整数部分
            Integer materialInteger = Integer.valueOf(order.getHaredMaterialsNum().split("\\.")[0]);
            materialNum = materialInteger + surplusPcs / pcsNumber;
            String haredMaterialsNum = String.valueOf(materialInteger + surplusPcs / pcsNumber);
            // String haredMaterialsNum = new DecimalFormat("#.000").format(Double.parseDouble(order.getCommissioningNum()) / pcsNumber);
            orderDetailDTO.setHaredMaterialsNum(haredMaterialsNum);
            //投产数（计算规则：下料总数*大料PCS）
            orderDetailDTO.setCommissioningNum(String.valueOf(materialNum * pcsNumber));
        }
        //线路印次（计算规则：PNL数乘线路乘以下料总数+余料PNL数，以下类似）
        if (StringUtils.isNotBlank(miRegister.getPnlNumber())) {
            Integer surplusPnlNumber = Integer.valueOf(surplus.getSurplusPnl());//余料PNL数
            Integer pnlNumber = Integer.valueOf(miRegister.getPnlNumber());//PNL数
            orderDetailDTO.setLineImpression(String.valueOf(Integer.valueOf(miRegister.getLine()) * pnlNumber * materialNum + surplusPnlNumber));
            //绿油印次
            orderDetailDTO.setGreenOilImpression(String.valueOf(Integer.valueOf(miRegister.getGreenOil()) * pnlNumber * materialNum + surplusPnlNumber));
            //文字印次
            orderDetailDTO.setWordsImpression(String.valueOf(Integer.valueOf(miRegister.getWords()) * pnlNumber * materialNum + surplusPnlNumber));
            //碳桥印次
            orderDetailDTO.setCarbonBridgeImpression(String.valueOf(Integer.valueOf(miRegister.getCarbonBridge()) * pnlNumber * materialNum + surplusPnlNumber));
            //其它印次
            orderDetailDTO.setOtherImpression(String.valueOf(Integer.valueOf(miRegister.getOther()) * pnlNumber * materialNum + surplusPnlNumber));
        }
        //冲床冲次（计算规则：投产数除以一模出几）
        double punch = Double.valueOf(orderDetailDTO.getCommissioningNum()) / Integer.valueOf(miRegister.getMiNumber());
        orderDetailDTO.setPunch(String.valueOf((int) Math.ceil(punch)));
        //定位孔数（计算规则：冲床冲次乘2）
        double locatingNum = punch * 2;
        orderDetailDTO.setLocatingNum(String.valueOf((int) Math.ceil(locatingNum)));
        //报废数（计算规则：订单数的千分之五）
        double scrapNum = Integer.valueOf(orderDetailDTO.getOrderNum()) / 1000 * 5;
        orderDetailDTO.setScrapNum(String.valueOf((int) Math.ceil(scrapNum)));
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
        if (order.getOperationState() != surplus.getOperationState()) {
            throw new BizException("该订单修改完余料处理未修改！");
        }
        OrderDetailDTO detailDTO = this.getDetail(id);
        log.info("OrderServiceImpl getPrintOrder method end;");
        return detailDTO;
    }

    /**
     * 下单新增和修改公用方法
     *
     * @param order
     * @return
     */
    private AddOrderResultDTO addAndEdit(Order order, AddOrderDTO addOrderDTO) {
        log.info("OrderServiceImpl addAndEdit method start:");
        MiRegister byMiDyCode = order.getMiRegister();
        //订单数
        Integer orderNum = Integer.valueOf(addOrderDTO.getOrderNum());
        //备品率
        double parseDouble = Double.parseDouble(addOrderDTO.getSparePartsRate()) / 1000;
        //生成投产数量（规则：订单数+订单数*备品率）
        double commissioningNum = orderNum + orderNum * parseDouble;
        order.setCommissioningNum(String.valueOf((int) Math.ceil(commissioningNum)));
        //备品数
        order.setSparePartsNum(String.valueOf(orderNum * parseDouble));
        //生成平方数(规则：模片尺寸相乘/一模出几/1000000再乘以订单数)
        //一模出几
        Integer miNumber = Integer.valueOf(byMiDyCode.getMiNumber());
        // 模片尺寸相乘
        double v2 = byMiDyCode.getDieSizeLength() * byMiDyCode.getDieSizeWide();
        String squareNum = new DecimalFormat("0.000").format(v2 / miNumber / 1000000 * orderNum);
        order.setSquareNum(squareNum);
        //共用料张数(计算规则：投产数/大料PCS数,保留三位小数)
        Integer pcsNumber = Integer.valueOf(order.getMiRegister().getPcsNumber());
        String haredMaterialsNum = new DecimalFormat("0.000").format(commissioningNum / pcsNumber);
        order.setHaredMaterialsNum(haredMaterialsNum);
        orderDao.save(order);
        log.info("OrderServiceImpl addAndEdit method end;");
        return this.getSurplusResult(order);
    }

    /**
     * 返回余料处理需要的数据
     *
     * @param order
     * @return
     */
    private AddOrderResultDTO getSurplusResult(Order order) {
        log.info("OrderServiceImpl getSurplusResult method start:");
        AddOrderResultDTO resultDTO = new AddOrderResultDTO();
        resultDTO.setId(order.getId());
        //返回图片信息
        if (order.getMiRegister().getCommonFileId() != null) {
            CommonFile commonFile = fileDao.findOne(order.getMiRegister().getCommonFileId());
            if (commonFile != null) {
                resultDTO.setFileName(commonFile.getFileName());
                resultDTO.setCommonFileId(commonFile.getId());
            }
        }
        //返回共用料张数(计算规则：投产数/大料PCS数,保留三位小数)
        //大料PCS数
        Integer pcsNumber = Integer.valueOf(order.getMiRegister().getPcsNumber());
        //String haredMaterialsNum = new DecimalFormat("#.000").format(Double.parseDouble(order.getCommissioningNum()) / pcsNumber);
        String haredMaterialsNum = order.getHaredMaterialsNum();
        resultDTO.setHaredMaterialsNum(haredMaterialsNum);
        //返回余下张数(计算规则：共用料张数的小数部分)
        String remainNum = "0." + haredMaterialsNum.replaceAll("\\d+\\.", "");
        resultDTO.setRemainNum(remainNum);
        //返回PCS数(计算规则：大料PCS*余下的张数)
        double remainPcsNum = Math.ceil(pcsNumber * Double.parseDouble(remainNum));
        resultDTO.setPcsNumber(String.valueOf(remainPcsNum));
        log.info("OrderServiceImpl getSurplusResult method end;");
        return resultDTO;
    }

    /**
     * 生成投产单号，格式为日期yyMMdd+4位自增流水号(1811270001)，流水号按天每天重新计数
     *
     * @return
     */
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
    public void addPlusOrder(AddPlusOrderDTO dto) {
        log.info("OrderServiceImpl addPlusOrder method start:");
        //用填入的下单DY编号去查询没有删除的MI登记
        MiRegister byMiDyCode = registerDao.findByMiDyCodeAndDeleted(dto.getOrderDyCode(), DeletedEnum.UNDELETED.getValue());
        if (byMiDyCode == null) {
            throw new BizException("该DY编号没有对应的MI登记或已删除，请核实!");
        }
        Order order = orderDao.findByCommissioningCodeAndDeleted(dto.getCommissioningCode(), DeletedEnum.UNDELETED.getValue());
        if (order == null) {
            throw new BizException("原投产单号不存在有效下单数据，请核实!");
        }
        PlusOrder plusOrder = new PlusOrder();
        //补单单号
        plusOrder.setPlusCommissioningCode(this.createCode());
        this.addAndEditPlusOrder(plusOrder, dto, byMiDyCode);
        plusOrderDao.save(plusOrder);
        log.info("OrderServiceImpl addPlusOrder method end;");
    }

    @Override
    public PageDTO<PlusOrderListDTO> getPlusOrderList(PlusOrderQueryDTO queryDTO) {
        log.info("OrderServiceImpl getPlusOrderList method start:");
        PageRequest pageRequest = new PageRequest(queryDTO.getPageNo() - 1, queryDTO.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<PlusOrder> page = plusOrderDao.findAll(PlusOrderSpecs.plusOrderSpec(queryDTO), pageRequest);
        PageDTO<PlusOrderListDTO> pageDTO = PageDTO.of(page, item -> {
            PlusOrderListDTO plusOrderListDTO = new PlusOrderListDTO();
            BeanUtils.copyProperties(item, plusOrderListDTO);
            //交货日期yyyy-MM-dd
            plusOrderListDTO.setDeliveryDate(DateUtil.parseDateToStr(item.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //补单日期yyyy-MM-dd
            plusOrderListDTO.setPlusOrderDate(DateUtil.parseDateToStr(item.getPlusOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return plusOrderListDTO;
        });
        log.info("OrderServiceImpl getPlusOrderList method end;");
        return pageDTO;
    }

    @Override
    public void deletedPlusOrder(Long id) {
        log.info("OrderServiceImpl deletedPlusOrder method start:");
        PlusOrder plusOrder = plusOrderDao.findOne(id);
        if (plusOrder == null) {
            throw new BizException("该补单已不存在！");
        }
        plusOrder.setDeleted(DeletedEnum.DELETED.getValue());
        plusOrderDao.save(plusOrder);
        log.info("OrderServiceImpl deletedPlusOrder method end;");
    }

    @Override
    public PlusOrderListDTO getPlusOrderDetail(Long id) {
        log.info("OrderServiceImpl getPlusOrderDetail method start:");
        PlusOrder plusOrder = plusOrderDao.findOne(id);
        if (plusOrder == null) {
            throw new BizException("该补单已不存在！");
        }
        PlusOrderListDTO plusOrderListDTO = new PlusOrderListDTO();
        BeanUtils.copyProperties(plusOrder, plusOrderListDTO);
        //交货日期yyyy-MM-dd
        plusOrderListDTO.setDeliveryDate(DateUtil.parseDateToStr(plusOrder.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //补单日期yyyy-MM-dd
        plusOrderListDTO.setPlusOrderDate(DateUtil.parseDateToStr(plusOrder.getPlusOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        log.info("OrderServiceImpl getPlusOrderDetail method end;");
        return plusOrderListDTO;
    }

    @Override
    public void editPlusOrder(AddPlusOrderDTO dto) {
        log.info("OrderServiceImpl editPlusOrder method start:");
        //用填入的下单DY编号去查询没有删除的MI登记
        MiRegister byMiDyCode = registerDao.findByMiDyCodeAndDeleted(dto.getOrderDyCode(), DeletedEnum.UNDELETED.getValue());
        if (byMiDyCode == null) {
            throw new BizException("该DY编号没有对应的MI登记或已删除，请核实!");
        }
        Order order = orderDao.findByCommissioningCodeAndDeleted(dto.getCommissioningCode(), DeletedEnum.UNDELETED.getValue());
        if (order == null) {
            throw new BizException("原投产单号不存在有效下单数据，请核实!");
        }
        PlusOrder plusOrder = plusOrderDao.findOne(dto.getId());
        this.addAndEditPlusOrder(plusOrder, dto, byMiDyCode);
        plusOrderDao.save(plusOrder);
        log.info("OrderServiceImpl editPlusOrder method end;");
    }

    @Override
    public void recoveryPlusOrder(Long id) {
        log.info("OrderServiceImpl recoveryPlusOrder method start:");
        PlusOrder plusOrder = plusOrderDao.findOne(id);
        if (plusOrder == null) {
            throw new BizException("该补单已不存在！");
        }
        plusOrder.setDeleted(DeletedEnum.UNDELETED.getValue());
        plusOrderDao.save(plusOrder);
        log.info("OrderServiceImpl recoveryPlusOrder method end;");
    }

    private void addAndEditPlusOrder(PlusOrder plusOrder, AddPlusOrderDTO dto, MiRegister byMiDyCode) {
        BeanUtils.copyProperties(dto, plusOrder);
        //交货日期yyyy-MM-dd
        plusOrder.setDeliveryDate(DateUtil.parseStrToDate(dto.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //补单日期yyyy-MM-dd
        plusOrder.setPlusOrderDate(DateUtil.parseStrToDate(dto.getPlusOrderDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        //补单率
        plusOrder.setPlusOrderRate(Double.parseDouble(dto.getPlusOrderRate()));
        //经济损失
        plusOrder.setEconomicLoss(Double.parseDouble(dto.getEconomicLoss()));
        //客户名称
        plusOrder.setCustomerName(byMiDyCode.getCustomerName());
        //产品型号
        plusOrder.setCustomerModel(byMiDyCode.getCustomerModel());
        //生成平方数(规则：模片尺寸相乘/一模出几/1000000再乘以实补单数)
        //一模出几
        Integer miNumber = Integer.valueOf(byMiDyCode.getMiNumber());
        // 模片尺寸相乘
        double v2 = byMiDyCode.getDieSizeLength() * byMiDyCode.getDieSizeWide();
        String squareNum = new DecimalFormat("0.000").format(v2 / miNumber / 1000000 * dto.getFactPlusOrderNum());
        plusOrder.setSquareNum(Double.parseDouble(squareNum));
    }

}
