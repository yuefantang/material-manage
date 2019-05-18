package com.dongyu.company.finance.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.finance.dto.AddMiPriceDTO;
import com.dongyu.company.finance.dto.BillListDTO;
import com.dongyu.company.finance.dto.BillStatisticsDTO;
import com.dongyu.company.finance.dto.EditMiPriceDTO;
import com.dongyu.company.finance.dto.MiPriceDetailDTO;
import com.dongyu.company.finance.dto.MiPriceListDTO;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;

import java.util.List;

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
     * MI登记价格恢复
     * @param id
     */
     void recovery(Long id);

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

    /**
     * 财务账单明细分页查询
     *
     * @param dto
     * @return
     */
    PageDTO<BillListDTO> getBillList(DeliveryQueryDTO dto);

    /**
     * 财务账单查询统计金额
     *
     * @param dto
     * @return
     */
    BillStatisticsDTO count(DeliveryQueryDTO dto);

    /**
     * 财务账单核实
     *
     * @param listId
     */
    void verify(List<Long> listId);

    /**
     * 财务账单核实取消
     *
     * @param listId
     */
    void unverify(List<Long> listId);

    /**
     * 财务账单明细导出和打印数据
     *
     * @param dto
     * @return
     */
    List<BillListDTO> getBillExportList(DeliveryQueryDTO dto);


}
