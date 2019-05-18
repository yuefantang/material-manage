package com.dongyu.company.customer.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.customer.dto.AddCustomerDTO;
import com.dongyu.company.customer.dto.CustomerListDTO;
import com.dongyu.company.customer.dto.CustomerQueryDTO;
import com.dongyu.company.customer.dto.EditCustomerDTO;

import java.util.List;

/**
 * 客户管理业务处理层
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
public interface CustomerService {

    //新增客户
    void add(AddCustomerDTO addCustomerDTO);

    //修改客户
    void edit(EditCustomerDTO editCustomerDTO);

    /**
     * 删除客户
     *
     * @param id 客户id
     */
    void deleted(Long id);

    /**
     * 恢复客户
     *
     * @param id 客户id
     */
    void recovery(Long id);

    /**
     * 客户详情
     *
     * @param id 客户id
     * @return
     */
    CustomerListDTO getDetail(Long id);


    //分页查询客户
    PageDTO<CustomerListDTO> getPageList(CustomerQueryDTO dto);


    /**
     * 获取客户名称下拉数据
     * @param customerName
     * @param customerType
     * @return
     */
    List<String> getList(String customerName, String customerType);
}
