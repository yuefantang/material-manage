package com.dongyu.company.warehouse.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.warehouse.dto.AddStockDTO;
import com.dongyu.company.warehouse.dto.StockListDTO;
import com.dongyu.company.warehouse.dto.StockQueryDTO;

/**
 * 库存业务管理Service
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
public interface StockService {
    /**
     * 新增库存登记
     *
     * @param addStockDTO
     */
    void add(AddStockDTO addStockDTO);

    /**
     * 库存登记分页查询
     *
     * @param stockQueryDTO
     * @return
     */
    PageDTO<StockListDTO> getlist(StockQueryDTO stockQueryDTO);

    /**
     * 修改库存登记
     *
     * @param addStockDTO
     */
    void edit(AddStockDTO addStockDTO);

    /**
     * 删除库存
     *
     * @param id 库存id
     */
    void deleted(Long id);

    /**
     * 恢复删除库存
     *
     * @param id 库存id
     */
    void recovery(Long id);

    /**
     * 库存详情
     *
     * @param id 库存id
     * @return
     */
    StockListDTO getDetail(Long id);
}
