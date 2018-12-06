package com.dongyu.company.finance.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.finance.dto.AddMiPriceDTO;
import com.dongyu.company.finance.dto.EditMiPriceDTO;
import com.dongyu.company.finance.dto.MiPriceDetailDTO;
import com.dongyu.company.finance.dto.MiPriceListDTO;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;

/**
 * 财务业务处理Service
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
public interface FinanceService {

    /**
     * 新增MI登记价格
     *
     * @param addMiPriceDTO
     */
    void add(AddMiPriceDTO addMiPriceDTO);

    /**
     * MI登记价格分页查询
     *
     * @param miPriceQueryDTO
     */
    PageDTO<MiPriceListDTO> getlist(MiPriceQueryDTO miPriceQueryDTO);

    /**
     * MI登记价格删除
     *
     * @param id
     */
    void deleted(Long id);

    /**
     * MI登记价格编辑
     *
     * @param editMiPriceDTO
     */
    void edit(EditMiPriceDTO editMiPriceDTO);

    /**
     * MI登记价格详情
     *
     * @param id
     * @return
     */
    MiPriceDetailDTO getDetail(Long id);
}
