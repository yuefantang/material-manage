package com.dongyu.company.finance.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.finance.dto.AddReceivableDTO;
import com.dongyu.company.finance.dto.EditReceivableDTO;
import com.dongyu.company.finance.dto.ReceivableListDTO;
import com.dongyu.company.finance.dto.ReceivableQueryDTO;

import java.util.List;

/**
 * 收款业务处理Service
 *
 * @author TYF
 * @date 2019/1/20
 * @since 1.0.0
 */
public interface ReceivableService {
    /**
     * 新增收款
     *
     * @param addReceivableDTO
     */
    void add(AddReceivableDTO addReceivableDTO);

    /**
     * 收款分页查询
     *
     * @param queryDTO
     * @return
     */
    PageDTO<ReceivableListDTO> getlist(ReceivableQueryDTO queryDTO);


    /**
     * 导出收款
     *
     * @param queryDTO
     * @return
     */
    List<ReceivableListDTO> getExportList(ReceivableQueryDTO queryDTO);

    /**
     * 修改收款
     *
     * @param editReceivableDTO
     */
    void edit(EditReceivableDTO editReceivableDTO);

    /**
     * 收款详情
     *
     * @param id 收款id
     * @return
     */
    ReceivableListDTO getDetail(Long id);

    /**
     * 删除收款
     *
     * @param id 收款id
     */
    void deleted(Long id);

    /**
     * 恢复删除收款
     *
     * @param id 收款id
     */
    void recovery(Long id);
}
