package com.dongyu.company.register.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.register.dto.AddRegisterDTO;
import com.dongyu.company.register.dto.RegisterDetailDTO;
import com.dongyu.company.register.dto.RegisterListDTO;
import com.dongyu.company.register.dto.RegisterQueryDTO;

import java.util.List;

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

    /**
     * MI登记分页查询
     *
     * @param registerQueryDTO
     * @return
     */
    PageDTO<RegisterListDTO> getlist(RegisterQueryDTO registerQueryDTO);


    /**
     * 导出MI登记
     *
     * @param registerQueryDTO
     * @return
     */
    List<RegisterDetailDTO> getExportList(RegisterQueryDTO registerQueryDTO);

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
     * 恢复删除MI登记
     *
     * @param id MI登记id
     */
    void recovery(Long id);

    /**
     * MI登记详情
     *
     * @param id MI登记id
     * @return
     */
    RegisterDetailDTO getDetail(Long id);

    /**
     * 获取客户名称下拉列表数据
     * @param customerName
     * @return
     */
    List<String> getCustomerName(String customerName);
}
