package com.dongyu.company.register.service;

import com.dongyu.company.register.dto.AddRegisterDTO;
import com.dongyu.company.register.dto.RegisterDetailDTO;

/**
 * MI登记Service
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
public interface RegisterService {

    /**
     * 新增MI登记
     *
     * @param addRegisterDTO
     */
    void add(AddRegisterDTO addRegisterDTO);

    //分页查询模具采购
    // PageDTO<MouldListDTO> getlist(MouldQueryDTO mouldQueryDTO);

    //导出查询模具采购
    // List<MouldDetailDTO> getexportList(MouldQueryDTO mouldQueryDTO);

    /**
     * 修改MI登记
     *
     * @param editRegisterDTO
     */
    void edit(RegisterDetailDTO editRegisterDTO);

    /**
     * 删除MI登记
     *
     * @param id MI登记id
     */
    void deleted(Long id);

    /**
     * MI登记详情
     *
     * @param id MI登记id
     * @return
     */
    RegisterDetailDTO getDetail(Long id);
}
