package com.dongyu.company.mould.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.mould.dao.MouldSpecs;
import com.dongyu.company.mould.dao.PurchaseMouldDao;
import com.dongyu.company.mould.domain.PurchaseMould;
import com.dongyu.company.mould.dto.AddMouldDTO;
import com.dongyu.company.mould.dto.EditMouldDTO;
import com.dongyu.company.mould.dto.MouldDetailDTO;
import com.dongyu.company.mould.dto.MouldListDTO;
import com.dongyu.company.mould.dto.MouldQueryDTO;
import com.dongyu.company.mould.service.PurchaseMouldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模具采购业务处理实现层
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
@Service
@Slf4j
public class PurchaseMouldServiceImpl implements PurchaseMouldService {


    @Autowired
    private PurchaseMouldDao purchaseMouldDao;

    @Override
    @Transactional
    public void add(AddMouldDTO addMouldDTO) {
        log.info("PurchaseMouldServiceImpl add method start Parm:" + JSONObject.toJSONString(addMouldDTO));
        //根据DY编号去重
        PurchaseMould byDyCode = purchaseMouldDao.findByDyCode(addMouldDTO.getDyCode());
        if (byDyCode != null) {
            throw new BizException("DY编号已存在,请重新输入");
        }
        PurchaseMould purchaseMould = new PurchaseMould();
        BeanUtils.copyProperties(addMouldDTO, purchaseMould);
        purchaseMouldDao.save(purchaseMould);

    }

    @Override
    public PageDTO<MouldListDTO> getlist(MouldQueryDTO dto) {
        log.info("PurchaseMouldServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<PurchaseMould> page = purchaseMouldDao.findAll(MouldSpecs.mouldListQuerySpec(dto), pageRequest);
        PageDTO<MouldListDTO> pageDTO = PageDTO.of(page, item -> {
            MouldListDTO mouldListDTO = new MouldListDTO();
            BeanUtils.copyProperties(item, mouldListDTO);
            mouldListDTO.setPurchaseDate(DateUtil.parseDateToStr(item.getPurchaseDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return mouldListDTO;
        });
        return pageDTO;
    }

    @Override
    public void edit(EditMouldDTO dto) {
        log.info("PurchaseMouldServiceImpl edit method start Parm:" + JSONObject.toJSONString(dto));
        PurchaseMould oldPurchaseMould = purchaseMouldDao.findOneById(dto.getId());
        //根据DY编号去重
        PurchaseMould byDyCode = purchaseMouldDao.findByDyCode(dto.getDyCode());
        if (byDyCode != null) {
            throw new BizException("DY编号已存在,请重新输入");
        }
        BeanUtils.copyProperties(dto, oldPurchaseMould);
        purchaseMouldDao.save(oldPurchaseMould);
    }

    @Override
    public void deleted(Long id) {
        log.info("PurchaseMouldServiceImpl deleted method start Parm:" + id);
        purchaseMouldDao.deleted(id);
    }

    @Override
    public MouldDetailDTO getDetail(Long id) {
        log.info("PurchaseMouldServiceImpl getDetail method start Parm:" + id);
        PurchaseMould purchaseMould = purchaseMouldDao.findOneById(id);
        if (purchaseMould == null) {
            throw new BizException("不存在该模具采购id");
        }
        MouldDetailDTO detailDTO = new MouldDetailDTO();
        BeanUtils.copyProperties(purchaseMould, detailDTO);
        detailDTO.setPurchaseDate(DateUtil.parseDateToStr(purchaseMould.getPurchaseDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        return detailDTO;
    }
}
