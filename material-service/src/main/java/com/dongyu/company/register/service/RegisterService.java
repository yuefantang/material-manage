package com.dongyu.company.register.service;

import com.dongyu.company.register.dto.AddRegisterDTO;

/**
 * MI登记Service
 *
 * @author TYF
 * @date 2018/11/16
 * @since 1.0.0
 */
public interface RegisterService {

    //新增模具采购
    void add(AddRegisterDTO addRegisterDTO);

    //分页查询模具采购
   // PageDTO<MouldListDTO> getlist(MouldQueryDTO mouldQueryDTO);

    //导出查询模具采购
   // List<MouldDetailDTO> getexportList(MouldQueryDTO mouldQueryDTO);

    //修改模具采购
    //void edit(EditMouldDTO editMouldDTO);

    /**
     * 删除模具采购
     *
     * @param id 模具采购id
     */
    //void deleted(Long id);

    /**
     * 模具采购详情
     *
     * @param id 模具采购id
     * @return
     */
   // MouldDetailDTO getDetail(Long id);
}
