package com.dongyu.company.warehouse.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.warehouse.dao.StockDao;
import com.dongyu.company.warehouse.dao.StockSpecs;
import com.dongyu.company.warehouse.domain.Stock;
import com.dongyu.company.warehouse.dto.AddStockDTO;
import com.dongyu.company.warehouse.dto.StockListDTO;
import com.dongyu.company.warehouse.dto.StockQueryDTO;
import com.dongyu.company.warehouse.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 库存业务管理Service实现类
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public void add(AddStockDTO addStockDTO) {
        log.info("StockServiceImpl add method start Parm:" + JSONObject.toJSONString(addStockDTO));
        Stock oldStock = stockDao.findByDyCode(addStockDTO.getDyCode());
        //根据DY编号去重
        if (oldStock != null) {
            throw new BizException("已存在该DY编号的库存");
        }
        Stock stock = new Stock();
        BeanUtils.copyProperties(addStockDTO, stock);
        stockDao.save(stock);
        log.info("StockServiceImpl add method end;");
    }

    @Override
    public PageDTO<StockListDTO> getlist(StockQueryDTO dto) {
        log.info("StockServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<Stock> page = stockDao.findAll(StockSpecs.stockQuerySpec(dto), pageRequest);

        PageDTO<StockListDTO> pageDTO = PageDTO.of(page, item -> {
            StockListDTO stockListDTO = new StockListDTO();
            BeanUtils.copyProperties(item, stockListDTO);
            //库存登记时间
            stockListDTO.setCreateTime(DateUtil.parseDateToStr(item.getCreateTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return stockListDTO;
        });
        log.info("StockServiceImpl getlist method end;");
        return pageDTO;
    }

    @Override
    public void edit(AddStockDTO addStockDTO) {
        log.info("StockServiceImpl edit method start Parm:" + JSONObject.toJSONString(addStockDTO));
        if (addStockDTO.getId() == null) {
            throw new BizException("id不能为空!");
        }
        Stock stock = stockDao.findOne(addStockDTO.getId());
        if (stock == null) {
            throw new BizException("该库存不存在!");
        }
        Stock oldStock = stockDao.findByDyCode(addStockDTO.getDyCode());
        //根据DY编号去重
        if (oldStock != null) {
            throw new BizException("已存在该DY编号的库存!");
        }
        BeanUtils.copyProperties(addStockDTO, stock);
        stockDao.save(stock);
        log.info("StockServiceImpl edit method end;");
    }

    @Override
    public void deleted(Long id) {
        log.info("StockServiceImpl deleted method start Parm:" + id);
        Stock stock = stockDao.findOne(id);
        if (stock == null) {
            throw new BizException("该库存不存在!");
        }
        stock.setDeleted(DeletedEnum.DELETED.getValue());
        stockDao.save(stock);
        log.info("StockServiceImpl deleted method end;");
    }

    @Override
    public void recovery(Long id) {
        log.info("StockServiceImpl recovery method start Parm:" + id);
        Stock stock = stockDao.findOne(id);
        if (stock == null) {
            throw new BizException("该库存不存在!");
        }
        stock.setDeleted(DeletedEnum.UNDELETED.getValue());
        stockDao.save(stock);
        log.info("StockServiceImpl recovery method end;");
    }

    @Override
    public StockListDTO getDetail(Long id) {
        log.info("StockServiceImpl getDetail method start Parm:" + id);
        Stock stock = stockDao.findOne(id);
        if (stock == null) {
            throw new BizException("该库存不存在!");
        }
        StockListDTO dto = new StockListDTO();
        BeanUtils.copyProperties(stock, dto);
        dto.setCreateTime(DateUtil.parseDateToStr(stock.getCreateTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        log.info("StockServiceImpl getDetail method end;");
        return dto;
    }
}
