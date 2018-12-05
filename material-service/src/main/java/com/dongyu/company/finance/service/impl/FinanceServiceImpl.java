package com.dongyu.company.finance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.deliverynote.dao.DeliverySpecs;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.dto.DeliveryListDTO;
import com.dongyu.company.finance.dao.MiPriceDao;
import com.dongyu.company.finance.dao.MiPriceSpecs;
import com.dongyu.company.finance.domain.MiPrice;
import com.dongyu.company.finance.dto.AddMiPriceDTO;
import com.dongyu.company.finance.dto.MiPriceListDTO;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;
import com.dongyu.company.finance.service.FinanceService;
import com.dongyu.company.register.dao.RegisterDao;
import com.dongyu.company.register.domain.MiRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        miPrice.setMiRegister(miRegister);
        MiPrice save = miPriceDao.save(miPrice);
        //MI登记表关联价格表
        miRegister.setMiPrice(save);
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
            MiRegister miRegister = item.getMiRegister();
            if (miRegister != null) {
                BeanUtils.copyProperties(miRegister, miPriceListDTO);
            }
            return miPriceListDTO;
        });
        log.info("FinanceServiceImpl getlist method end;");
        return pageDTO;
    }
}
