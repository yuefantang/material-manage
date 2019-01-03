package com.dongyu.company.mould.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.mould.dto.AddMouldDTO;
import com.dongyu.company.mould.dto.EditMouldDTO;
import com.dongyu.company.mould.dto.MouldDetailDTO;
import com.dongyu.company.mould.dto.MouldListDTO;
import com.dongyu.company.mould.dto.MouldQueryDTO;

import java.util.List;

/**
 * 模具采购业务处理层
 *
 * @author TYF
 * @date 2018/11/11
 * @since 1.0.0
 */
public interface PurchaseMouldService {

    //新增模具采购
    void add(AddMouldDTO addMouldDTO);

    //分页查询模具采购
    PageDTO<MouldListDTO> getlist(MouldQueryDTO mouldQueryDTO);

    //导出查询模具采购
    List<MouldDetailDTO> getexportList(MouldQueryDTO mouldQueryDTO);

    //修改模具采购
    void edit(EditMouldDTO editMouldDTO);

    /**
     * 删除模具采购
     *
     * @param id 模具采购id
     */
    void deleted(Long id);

    /**
     * 恢复删除模具采购
     *
     * @param id 模具采购id
     */
    void recovery(Long id);

    /**
     * 模具采购详情
     *
     * @param id 模具采购id
     * @return
     */
    MouldDetailDTO getDetail(Long id);

}
