package com.dongyu.company.finance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.constants.VerifyStateEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.deliverynote.dao.DeliveryNoteDao;
import com.dongyu.company.deliverynote.dao.DeliverySpecs;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.finance.dao.BillStatisticsDao;
import com.dongyu.company.finance.dao.MiPriceDao;
import com.dongyu.company.finance.dao.MiPriceSpecs;
import com.dongyu.company.finance.domain.MiPrice;
import com.dongyu.company.finance.dto.AddMiPriceDTO;
import com.dongyu.company.finance.dto.BillListDTO;
import com.dongyu.company.finance.dto.BillStatisticsDTO;
import com.dongyu.company.finance.dto.EditMiPriceDTO;
import com.dongyu.company.finance.dto.MiPriceDetailDTO;
import com.dongyu.company.finance.dto.MiPriceListDTO;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;
import com.dongyu.company.finance.service.FinanceService;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.domain.MiRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务业务处理Service实现类
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
@Slf4j
@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private MiPriceDao miPriceDao;
    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private DeliveryNoteDao deliveryNoteDao;
    @Autowired
    private BillStatisticsDao billStatisticsDao;


    @Override
    @Transactional
    public void add(AddMiPriceDTO addMiPriceDTO) {
        log.info("FinanceServiceImpl add method start Parm:" + JSONObject.toJSONString(addMiPriceDTO));
        MiRegister miRegister = registerDao.findByMiDyCode(addMiPriceDTO.getMiDyCode());
        if (miRegister == null) {
            throw new BizException("该DY编号没有对应的MI登记，请核实填写!");
        }
        MiPrice miPrice = miPriceDao.findByMiDyCode(addMiPriceDTO.getMiDyCode());
        //根据DY编号去重
        if (miPrice != null) {
            throw new BizException("已存在该DY编号的MI登记价格!");
        }
        miPrice = new MiPrice();
        BeanUtils.copyProperties(addMiPriceDTO, miPrice);
        //价格表关联MI登记表
        miPrice.setMiRegisterId(miRegister.getId());
        MiPrice save = miPriceDao.save(miPrice);
        //MI登记表关联价格表
        miRegister.setMiPriceId(save.getId());
        registerDao.save(miRegister);
        log.info("FinanceServiceImpl add method end;");
    }

    @Override
    public PageDTO<MiPriceListDTO> getlist(MiPriceQueryDTO dto) {
        log.info("FinanceServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<MiPrice> page = miPriceDao.findAll(MiPriceSpecs.miPriceQuerySpec(dto), pageRequest);

        PageDTO<MiPriceListDTO> pageDTO = PageDTO.of(page, item -> {
            MiPriceListDTO miPriceListDTO = new MiPriceListDTO();
            BeanUtils.copyProperties(item, miPriceListDTO);
            miPriceListDTO.setMiPriceId(item.getId());
            MiRegister miRegister = registerDao.findOne(item.getMiRegisterId());
            if (miRegister != null) {
                BeanUtils.copyProperties(miRegister, miPriceListDTO);
            }
            return miPriceListDTO;
        });
        log.info("FinanceServiceImpl getlist method end;");
        return pageDTO;
    }

    @Override
    public void deleted(Long id) {
        log.info("FinanceServiceImpl deleted method start Parm:" + id);
        MiPrice miPrice = miPriceDao.findOne(id);
        if (miPrice == null) {
            throw new BizException("该MI登记价格已不存在！");
        }
        miPrice.setDeleted(DeletedEnum.DELETED.getValue());
        miPriceDao.save(miPrice);
        log.info("FinanceServiceImpl deleted method end;");
    }

    @Override
    @Transactional
    public void edit(EditMiPriceDTO dto) {
        log.info("FinanceServiceImpl edit method start Parm:" + JSONObject.toJSONString(dto));
        MiPrice oldMiPrice = miPriceDao.findOne(dto.getId());
        if (oldMiPrice == null) {
            throw new BizException("该MI登记价格已不存在！");
        }
        if (!dto.getMiDyCode().equals(oldMiPrice.getMiDyCode())) {
            MiRegister miRegister = registerDao.findByMiDyCodeAndDeleted(dto.getMiDyCode(), DeletedEnum.DELETED.getValue());
            if (miRegister == null) {
                throw new BizException("该DY编号没有对应的MI登记或已删除，请核实填写!");
            }
            MiPrice miPrice = miPriceDao.findByMiDyCode(dto.getMiDyCode());
            //根据DY编号去重
            if (miPrice != null) {
                throw new BizException("已存在该DY编号的MI登记价格!");
            }
            miPrice = oldMiPrice;
            BeanUtils.copyProperties(dto, miPrice);
            //价格表关联MI登记表
            miPrice.setMiRegisterId(miRegister.getId());
            MiPrice save = miPriceDao.save(miPrice);
            //MI登记表关联价格表
            miRegister.setMiPriceId(save.getId());
            registerDao.save(miRegister);
        } else {
            BeanUtils.copyProperties(dto, oldMiPrice);
            miPriceDao.save(oldMiPrice);
        }
        log.info("FinanceServiceImpl edit method end;");
    }

    @Override
    public MiPriceDetailDTO getDetail(Long id) {
        log.info("FinanceServiceImpl getDetail method start Parm:" + id);
        MiPrice miPrice = miPriceDao.findOne(id);
        if (miPrice == null) {
            throw new BizException("该MI登记价格不存在!");
        }
        MiPriceDetailDTO miPriceDetailDTO = new MiPriceDetailDTO();
        BeanUtils.copyProperties(miPrice, miPriceDetailDTO);
        log.info("FinanceServiceImpl getDetail method end;");
        return miPriceDetailDTO;
    }

    @Override
    public PageDTO<BillListDTO> getBillList(DeliveryQueryDTO dto) {
        log.info("FinanceServiceImpl getBillList method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<DeliveryNote> page = deliveryNoteDao.findAll(DeliverySpecs.orederQuerySpec(dto), pageRequest);

        PageDTO<BillListDTO> pageDTO = PageDTO.of(page, item -> {
            BillListDTO billListDTO = new BillListDTO();
            BeanUtils.copyProperties(item, billListDTO);
            //送货日期
            if (item.getDeliveryDate() != null) {
                billListDTO.setDeliveryDate(DateUtil.parseDateToStr(item.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            }
            //核实状态赋值
            if (item.getVerifyState() == VerifyStateEnum.UNVERIFY.getValue()) {
                billListDTO.setVerifyState(VerifyStateEnum.UNVERIFY.getDesc());
            } else {
                billListDTO.setVerifyState(VerifyStateEnum.VERIFY.getDesc());
            }
            return billListDTO;
        });
        log.info("FinanceServiceImpl getBillList method end;");
        return pageDTO;
    }

    @Override
    public BillStatisticsDTO count(DeliveryQueryDTO dto) {
        log.info("FinanceServiceImpl count method  start Parm:" + JSONObject.toJSONString(dto));
        BillStatisticsDTO query = billStatisticsDao.query(dto);
        log.info("FinanceServiceImpl count method end;");
        return query;
    }

    @Override
    public void verify(List<Long> listId) {
        log.info("FinanceServiceImpl verify method  start Parm:" + JSONObject.toJSONString(listId));
        List<DeliveryNote> noteList = deliveryNoteDao.findAll(listId);
        for (DeliveryNote deliveryNote : noteList) {
            deliveryNote.setVerifyState(VerifyStateEnum.VERIFY.getValue());
            deliveryNoteDao.save(deliveryNote);
        }
        log.info("FinanceServiceImpl verify method end;");
    }

    @Override
    public void unverify(List<Long> listId) {
        log.info("FinanceServiceImpl unverify method  start Parm:" + JSONObject.toJSONString(listId));
        List<DeliveryNote> noteList = deliveryNoteDao.findAll(listId);
        for (DeliveryNote deliveryNote : noteList) {
            deliveryNote.setVerifyState(VerifyStateEnum.UNVERIFY.getValue());
            deliveryNoteDao.save(deliveryNote);
        }
        log.info("FinanceServiceImpl unverify method end;");
    }

    @Override
    public List<BillListDTO> getBillExportList(DeliveryQueryDTO dto) {
        log.info("FinanceServiceImpl getBillExportList method  start Parm:" + JSONObject.toJSONString(dto));
        List<DeliveryNote> deliveryNotes = deliveryNoteDao.findAll(DeliverySpecs.orederQuerySpec(dto));
        if (CollectionUtils.isEmpty(deliveryNotes)) {
            return null;
        }
        List<BillListDTO> detailDTOList = deliveryNotes.stream().map(deliveryNote -> {
            BillListDTO billListDTO = new BillListDTO();
            BeanUtils.copyProperties(deliveryNote, billListDTO);
            //送货日期
            billListDTO.setDeliveryDate(DateUtil.parseDateToStr(deliveryNote.getDeliveryDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            //核实状态赋值
            if (deliveryNote.getVerifyState() == VerifyStateEnum.UNVERIFY.getValue()) {
                billListDTO.setVerifyState(VerifyStateEnum.UNVERIFY.getDesc());
            } else {
                billListDTO.setVerifyState(VerifyStateEnum.VERIFY.getDesc());
            }
            return billListDTO;
        }).collect(Collectors.toList());
        log.info("FinanceServiceImpl getBillExportList method end;");
        return detailDTOList;
    }
}
