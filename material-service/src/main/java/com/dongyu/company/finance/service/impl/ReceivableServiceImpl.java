package com.dongyu.company.finance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.finance.dao.ReceivableDao;
import com.dongyu.company.finance.dao.ReceivableSpecs;
import com.dongyu.company.finance.domain.Receivable;
import com.dongyu.company.finance.dto.AddReceivableDTO;
import com.dongyu.company.finance.dto.EditReceivableDTO;
import com.dongyu.company.finance.dto.ReceivableListDTO;
import com.dongyu.company.finance.dto.ReceivableQueryDTO;
import com.dongyu.company.finance.service.ReceivableService;
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
 * 收款业务处理Service实现类
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
@Service
@Slf4j
public class ReceivableServiceImpl implements ReceivableService {
    @Autowired
    private ReceivableDao receivableDao;

    @Override
    public void add(AddReceivableDTO addReceivableDTO) {
        log.info("ReceivableServiceImpl add method start Parm:" + JSONObject.toJSONString(addReceivableDTO));
        Receivable oldReceivable = receivableDao.findByCustomerNameAndFundMonthAndDeleted(addReceivableDTO.getCustomerName(), addReceivableDTO.getFundMonth(), DeletedEnum.UNDELETED.getValue());
        if (oldReceivable != null) {
            throw new BizException(addReceivableDTO.getCustomerName() + "该客户" + addReceivableDTO.getFundMonth() + "款项已存在,请核实填写!");
        }
        Receivable receivable = new Receivable();
        BeanUtils.copyProperties(addReceivableDTO, receivable);
        receivableDao.save(receivable);
        log.info("ReceivableServiceImpl add method end;");
    }

    @Override
    public PageDTO<ReceivableListDTO> getlist(ReceivableQueryDTO dto) {
        log.info("ReceivableServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<Receivable> page = receivableDao.findAll(ReceivableSpecs.receivableQuerySpec(dto), pageRequest);

        PageDTO<ReceivableListDTO> pageDTO = PageDTO.of(page, item -> {
            ReceivableListDTO receivableListDTO = new ReceivableListDTO();
            BeanUtils.copyProperties(item, receivableListDTO);
            receivableListDTO.setReceivableDate(DateUtil.parseDateToStr(item.getReceivableDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return receivableListDTO;
        });
        log.info("ReceivableServiceImpl getlist method end;");
        return pageDTO;
    }

    @Override
    public List<ReceivableListDTO> getExportList(ReceivableQueryDTO queryDTO) {
        log.info("ReceivableServiceImpl getExportList method start Parm:" + JSONObject.toJSONString(queryDTO));
        List<Receivable> receivables = receivableDao.findAll(ReceivableSpecs.receivableQuerySpec(queryDTO));
        if (CollectionUtils.isEmpty(receivables)) {
            return null;
        }
        List<ReceivableListDTO> dtoList = receivables.stream().map(receivable -> {
            ReceivableListDTO receivableListDTO = new ReceivableListDTO();
            BeanUtils.copyProperties(receivable, receivableListDTO);
            receivableListDTO.setReceivableDate(DateUtil.parseDateToStr(receivable.getReceivableDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return receivableListDTO;
        }).collect(Collectors.toList());
        log.info("ReceivableServiceImpl getExportList method end;");
        return dtoList;
    }

    @Override
    public void edit(EditReceivableDTO editReceivableDTO) {
        log.info("ReceivableServiceImpl edit method start:");
        if (editReceivableDTO.getId() == null) {
            throw new BizException("id不能为空！");
        }
        Receivable receivable = receivableDao.findOne(editReceivableDTO.getId());
        if (receivable == null) {
            throw new BizException("该收款记录不存在！");
        }
        Receivable oldReceivable = receivableDao.findByCustomerNameAndFundMonthAndDeleted(editReceivableDTO.getCustomerName(), editReceivableDTO.getFundMonth(), DeletedEnum.UNDELETED.getValue());
        if (oldReceivable != null) {
            throw new BizException(editReceivableDTO.getCustomerName() + "该客户" + editReceivableDTO.getFundMonth() + "款项已存在,请核实填写!");
        }
        BeanUtils.copyProperties(editReceivableDTO, receivable);
        receivableDao.save(receivable);
        log.info("ReceivableServiceImpl edit method end;");
    }

    @Override
    public ReceivableListDTO getDetail(Long id) {
        log.info("ReceivableServiceImpl getDetail method start:" + id);
        if (id == null) {
            throw new BizException("id不能为空！");
        }
        Receivable receivable = receivableDao.findOne(id);
        if (receivable == null) {
            throw new BizException("该收款记录不存在！");
        }
        ReceivableListDTO receivableListDTO = new ReceivableListDTO();
        BeanUtils.copyProperties(receivable, receivableListDTO);
        receivableListDTO.setReceivableDate(DateUtil.parseDateToStr(receivable.getReceivableDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        log.info("ReceivableServiceImpl getDetail method end;");
        return receivableListDTO;
    }

    @Override
    @Transactional
    public void deleted(Long id) {
        log.info("ReceivableServiceImpl deleted method start:" + id);
        if (id == null) {
            throw new BizException("id不能为空！");
        }
        Receivable receivable = receivableDao.findOne(id);
        if (receivable == null) {
            throw new BizException("该收款记录不存在！");
        }
        receivable.setDeleted(DeletedEnum.DELETED.getValue());
        receivableDao.save(receivable);
        log.info("ReceivableServiceImpl deleted method end;");
    }

    @Override
    @Transactional
    public void recovery(Long id) {
        log.info("ReceivableServiceImpl recovery method start:" + id);
        if (id == null) {
            throw new BizException("id不能为空！");
        }
        Receivable receivable = receivableDao.findOne(id);
        if (receivable == null) {
            throw new BizException("该收款记录不存在！");
        }
        receivable.setDeleted(DeletedEnum.UNDELETED.getValue());
        receivableDao.save(receivable);
        log.info("ReceivableServiceImpl recovery method end;");
    }
}
