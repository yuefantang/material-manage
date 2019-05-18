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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        Integer purchaseType = addMouldDTO.getPurchaseType();
        //根据DY编号和采购种类去重
        PurchaseMould byDyCode = purchaseMouldDao.findByDyCodeAndPurchaseTypeAndDeleted(addMouldDTO.getDyCode(), purchaseType,DeletedEnum.UNDELETED.getValue());
        if (byDyCode != null) {
            throw new BizException("该DY编号的模具或测试架已存在,请重新输入");
        }
        if (purchaseType == ProcurementTypeEnum.MOULD.getValue()) {//采购种类为模具
            if (StringUtils.isBlank(addMouldDTO.getMouldType())) {
                throw new BizException("模具类型不能为空");
            }
            if (StringUtils.isBlank(addMouldDTO.getNumber())) {
                throw new BizException("一模出几不能为空");
            }
            if (StringUtils.isBlank(addMouldDTO.getConnect())) {
                throw new BizException("连接不能为空");
            }
            if (StringUtils.isBlank(addMouldDTO.getWide())) {
                throw new BizException("宽不能为空");
            }
            if (StringUtils.isBlank(addMouldDTO.getLength())) {
                throw new BizException("长不能为空");
            }
            if (!this.numCheck(addMouldDTO.getLength())) {
                throw new BizException("长格式错误，只能输入数字");
            }
            if (!this.numCheck(addMouldDTO.getWide())) {
                throw new BizException("宽格式错误，只能输入数字");
            }
            if (!this.numCheck(addMouldDTO.getNumber())) {
                throw new BizException("一模出几格式错误，只能输入数字");
            }
        } else {
            if (StringUtils.isBlank(addMouldDTO.getTestRackType())) {
                throw new BizException("测试架类型不能为空");
            }
            if (StringUtils.isBlank(addMouldDTO.getPoint())) {
                throw new BizException("点数不能为空");
            }
            if (StringUtils.isBlank(addMouldDTO.getCylinder())) {
                throw new BizException("气缸不能为空");
            }
            if (!this.numCheck(addMouldDTO.getPoint())) {
                throw new BizException("点数格式错误，只能输入数字");
            }
            if (!this.numCheck(addMouldDTO.getCylinder())) {
                throw new BizException("气缸格式错误，只能输入数字");
            }
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
        Integer deleted = oldPurchaseMould.getDeleted();
        if (deleted == DeletedEnum.DELETED.getValue()) {
            throw new BizException("已删除的数据不能编辑！");
        }
        Integer purchaseType = oldPurchaseMould.getPurchaseType();
        //DY编号修改则根据DY编号去重
        String oldDyCode = oldPurchaseMould.getDyCode();
        if (!dto.getDyCode().equals(oldDyCode)) {
            PurchaseMould byDyCode = purchaseMouldDao.findByDyCodeAndPurchaseTypeAndDeleted(dto.getDyCode(), purchaseType,DeletedEnum.UNDELETED.getValue());
            if (byDyCode != null) {
                throw new BizException("该DY编号的模具或测试架已存在,请重新输入");
            }
        }
        if (purchaseType == ProcurementTypeEnum.MOULD.getValue()) {//采购种类为模具
            if (StringUtils.isBlank(dto.getMouldType())) {
                throw new BizException("模具类型不能为空");
            }
            if (StringUtils.isBlank(dto.getNumber())) {
                throw new BizException("一模出几不能为空");
            }
            if (StringUtils.isBlank(dto.getConnect())) {
                throw new BizException("连接不能为空");
            }
            if (StringUtils.isBlank(dto.getWide())) {
                throw new BizException("宽不能为空");
            }
            if (StringUtils.isBlank(dto.getLength())) {
                throw new BizException("长不能为空");
            }
            if (!this.numCheck(dto.getLength())) {
                throw new BizException("长格式错误，只能输入数字");
            }
            if (!this.numCheck(dto.getWide())) {
                throw new BizException("宽格式错误，只能输入数字");
            }
            if (!this.numCheck(dto.getNumber())) {
                throw new BizException("一模出几格式错误，只能输入数字");
            }
        } else {
            if (StringUtils.isBlank(dto.getTestRackType())) {
                throw new BizException("测试架类型不能为空");
            }
            if (StringUtils.isBlank(dto.getPoint())) {
                throw new BizException("点数不能为空");
            }
            if (StringUtils.isBlank(dto.getCylinder())) {
                throw new BizException("气缸不能为空");
            }
            if (!this.numCheck(dto.getPoint())) {
                throw new BizException("点数格式错误，只能输入数字");
            }
            if (!this.numCheck(dto.getCylinder())) {
                throw new BizException("气缸格式错误，只能输入数字");
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
            throw new BizException("不存在该模具采购或测试架id");
        }
        //判段是否是第二次删除，如果是再次删除为物理删除
        Integer deleted = purchaseMould.getDeleted();
        if (deleted == DeletedEnum.UNDELETED.getValue()) {
            purchaseMould.setDeleted(DeletedEnum.DELETED.getValue());
            purchaseMouldDao.save(purchaseMould);
        } else {
            purchaseMouldDao.delete(id);
        }
        log.info("PurchaseMouldServiceImpl deleted method end:");
    }

    @Override
    public void recovery(Long id) {
        log.info("PurchaseMouldServiceImpl recovery method start Parm:" + id);
        PurchaseMould purchaseMould = purchaseMouldDao.findOneById(id);
        if (purchaseMould == null) {
            throw new BizException("不存在该模具采购id");
        }
        PurchaseMould byDyCode = purchaseMouldDao.findByDyCodeAndPurchaseTypeAndDeleted(purchaseMould.getDyCode(), purchaseMould.getPurchaseType(),DeletedEnum.UNDELETED.getValue());
        if (byDyCode != null) {
            throw new BizException("该DY编号的模具或测试架已存在,不能恢复");
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
     * 模具采购和测试架金额计算
     *
     * @param addMouldDTO
     * @return
     */
    private AddMouldDTO addOrEdit(AddMouldDTO addMouldDTO) {
        double prices = Double.parseDouble(addMouldDTO.getMouldPrice());
        String amount = null;
        if (addMouldDTO.getPurchaseType() == ProcurementTypeEnum.MOULD.getValue()) {//模具
            //计算公式：金额=（长度+宽度 ）*单价
            double length = Double.parseDouble(addMouldDTO.getLength());
            double wide = Double.parseDouble(addMouldDTO.getWide());
            amount = String.valueOf((length + wide) * prices);
            addMouldDTO.setMouldAmount(amount);
        } else if (addMouldDTO.getPurchaseType() == ProcurementTypeEnum.TEST_RACK.getValue()) {//测试架
            //计算公式：金额=点数*单价+气缸*30
            double point = Double.parseDouble(addMouldDTO.getPoint());
            double cylinder = Double.parseDouble(addMouldDTO.getCylinder());
            amount = String.valueOf((point) * prices + cylinder * 30d);
            addMouldDTO.setMouldAmount(amount);
        }
        return addMouldDTO;
    }

    //校验所传字符串是否是数字
    private static boolean numCheck(String str) {
        Pattern p = Pattern.compile(Constants.NUMBER_POINT_PATTERN);
        Matcher m = p.matcher(str);
        boolean r = m.matches();
        return r;

    }
}
