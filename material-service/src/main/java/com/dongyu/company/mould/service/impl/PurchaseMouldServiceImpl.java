package com.dongyu.company.mould.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.constants.ProcurementTypeEnum;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
        addMouldDTO = this.addOrEdit(addMouldDTO);
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
    public List<MouldDetailDTO> getExportList(MouldQueryDTO dto) {
        log.info("PurchaseMouldServiceImpl getExportList method start Parm:" + JSONObject.toJSONString(dto));
        List<PurchaseMould> purchaseMouldList = purchaseMouldDao.findAll(MouldSpecs.mouldListQuerySpec(dto));
        if (CollectionUtils.isEmpty(purchaseMouldList)) {
            return null;
        }
        List<MouldDetailDTO> detailDTOList = purchaseMouldList.stream().map(purchaseMould -> {
            MouldDetailDTO mouldDetailDTO = new MouldDetailDTO();
            BeanUtils.copyProperties(purchaseMould, mouldDetailDTO);
            mouldDetailDTO.setPurchaseDate(DateUtil.parseDateToStr(purchaseMould.getPurchaseDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return mouldDetailDTO;
        }).collect(Collectors.toList());
        return detailDTOList;
    }

    @Override
    public void edit(EditMouldDTO dto) {
        log.info("PurchaseMouldServiceImpl edit method start Parm:" + JSONObject.toJSONString(dto));
        PurchaseMould oldPurchaseMould = purchaseMouldDao.findOneById(dto.getId());
        //DY编号修改则根据DY编号去重
        String oldDyCode = oldPurchaseMould.getDyCode();
        if (!dto.getDyCode().equals(oldDyCode)) {
            PurchaseMould byDyCode = purchaseMouldDao.findByDyCode(dto.getDyCode());
            if (byDyCode != null) {
                throw new BizException("DY编号已存在,请重新输入");
            }
        }
        dto = (EditMouldDTO) this.addOrEdit(dto);
        BeanUtils.copyProperties(dto, oldPurchaseMould);
        purchaseMouldDao.save(oldPurchaseMould);
    }

    @Override
    public void deleted(Long id) {
        log.info("PurchaseMouldServiceImpl deleted method start Parm:" + id);
        PurchaseMould purchaseMould = purchaseMouldDao.findOneById(id);
        if (purchaseMould == null) {
            throw new BizException("不存在该模具采购id");
        }
        purchaseMould.setDeleted(DeletedEnum.DELETED.getValue());
        purchaseMouldDao.save(purchaseMould);
        log.info("PurchaseMouldServiceImpl deleted method end:");
    }

    @Override
    public void recovery(Long id) {
        log.info("PurchaseMouldServiceImpl recovery method start Parm:" + id);
        PurchaseMould purchaseMould = purchaseMouldDao.findOneById(id);
        if (purchaseMould == null) {
            throw new BizException("不存在该模具采购id");
        }
        purchaseMould.setDeleted(DeletedEnum.UNDELETED.getValue());
        purchaseMouldDao.save(purchaseMould);
        log.info("PurchaseMouldServiceImpl recovery method end;");
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

    /**
     * 模具采购金额计算
     *
     * @param addMouldDTO
     * @return
     */
    private String amount(AddMouldDTO addMouldDTO, String price) {
        //计算公式：金额=（长度+宽度 ）*单价
        double length = Double.parseDouble(addMouldDTO.getLength());
        double wide = Double.parseDouble(addMouldDTO.getWide());
        double prices = Double.parseDouble(price);
        String amount = String.valueOf((length + wide) * prices);
        return amount;
    }

    private AddMouldDTO addOrEdit(AddMouldDTO addMouldDTO) {
        if (addMouldDTO.getPurchaseType() == ProcurementTypeEnum.MOULD.getValue()) {
            if (StringUtils.isBlank(addMouldDTO.getMouldPrice())) {
                throw new BizException("模具单价不能为空");
            }
            String mouldAmount = this.amount(addMouldDTO, addMouldDTO.getMouldPrice());
            addMouldDTO.setMouldAmount(mouldAmount);
        } else if (addMouldDTO.getPurchaseType() == ProcurementTypeEnum.TEST_RACK.getValue()) {
            if (StringUtils.isBlank(addMouldDTO.getRackPrice())) {
                throw new BizException("测试架单价不能为空");
            }
            String rackAmount = this.amount(addMouldDTO, addMouldDTO.getRackPrice());
            addMouldDTO.setRackAmount(rackAmount);
        } else {
            if (StringUtils.isBlank(addMouldDTO.getMouldPrice()) || StringUtils.isBlank(addMouldDTO.getRackPrice())) {
                throw new BizException("模具单价或测试架单价不能为空");
            }
            String mouldAmount = this.amount(addMouldDTO, addMouldDTO.getMouldPrice());
            addMouldDTO.setMouldAmount(mouldAmount);
            String rackAmount = this.amount(addMouldDTO, addMouldDTO.getRackPrice());
            addMouldDTO.setRackAmount(rackAmount);
        }
        return addMouldDTO;
    }

}
