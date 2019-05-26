package com.dongyu.company.customer.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.customer.dao.CustomerDao;
import com.dongyu.company.customer.dao.CustomerSpecs;
import com.dongyu.company.customer.dao.CustomerSqlDao;
import com.dongyu.company.customer.domain.Customer;
import com.dongyu.company.customer.dto.AddCustomerDTO;
import com.dongyu.company.customer.dto.CustomerListDTO;
import com.dongyu.company.customer.dto.CustomerQueryDTO;
import com.dongyu.company.customer.dto.EditCustomerDTO;
import com.dongyu.company.customer.service.CustomerService;
import com.dongyu.company.order.dao.OrderSpecs;
import com.dongyu.company.order.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户Service实现
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    CustomerSqlDao customerSqlDao;

    @Transactional
    @Override
    public void add(AddCustomerDTO addCustomerDTO) {
        log.info("CustomerServiceImpl add method start：" + JSONObject.toJSONString(addCustomerDTO));
        List<Customer> customers = customerDao.findByCustomerNameAndCustomerTypeAndDeleted(addCustomerDTO.getCustomerName(), addCustomerDTO.getCustomerType(), DeletedEnum.UNDELETED.getValue());
        if (CollectionUtils.isNotEmpty(customers)) {
            throw new BizException(addCustomerDTO.getCustomerName() + "该客户已存在，请核实！");
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(addCustomerDTO, customer);
        customerDao.save(customer);
        log.info("CustomerServiceImpl add method end;");
    }

    @Transactional
    @Override
    public void edit(EditCustomerDTO editCustomerDTO) {
        log.info("CustomerServiceImpl edit method start：" + JSONObject.toJSONString(editCustomerDTO));
        if (editCustomerDTO.getId() == null) {
            throw new BizException("客户ID不能为空！");
        }
        Customer customer = customerDao.findOne(editCustomerDTO.getId());
        if (customer == null) {
            throw new BizException("客户不存在，不能编辑！");
        }
        Integer deleted = customer.getDeleted();
        if (deleted == DeletedEnum.DELETED.getValue()) {
            throw new BizException("客户已删除，不能编辑！");
        }
        List<Customer> customers = customerDao.findByCustomerNameAndCustomerTypeAndDeleted(editCustomerDTO.getCustomerName(), editCustomerDTO.getCustomerType(), DeletedEnum.UNDELETED.getValue());
        if (CollectionUtils.isNotEmpty(customers)) {
            throw new BizException(editCustomerDTO.getCustomerName() + "该客户已存在，请核实！");
        }
        BeanUtils.copyProperties(editCustomerDTO, customer);
        customerDao.save(customer);
        log.info("CustomerServiceImpl edit method end;");
    }

    @Transactional
    @Override
    public void deleted(Long id) {
        log.info("CustomerServiceImpl deleted method start：" + JSONObject.toJSONString(id));
        Customer customer = customerDao.findOne(id);
        if (customer == null) {
            throw new BizException("客户不存在！");
        }
        Integer deleted = customer.getDeleted();
        if (deleted == DeletedEnum.UNDELETED.getValue()) {
            customer.setDeleted(DeletedEnum.DELETED.getValue());
            customerDao.save(customer);
        } else {
            customerDao.delete(id);
        }
        log.info("CustomerServiceImpl deleted method end;");
    }

    @Override
    @Transactional
    public void recovery(Long id) {
        log.info("CustomerServiceImpl recovery method start：" + JSONObject.toJSONString(id));
        Customer customer = customerDao.findOne(id);
        if (customer == null) {
            throw new BizException("客户不存在！");
        }
        List<Customer> customers = customerDao.findByCustomerNameAndCustomerTypeAndDeleted(customer.getCustomerName(), customer.getCustomerType(), DeletedEnum.UNDELETED.getValue());
        if (CollectionUtils.isNotEmpty(customers)) {
            throw new BizException(customer.getCustomerName() + "该客户已存在，不能恢复！");
        }
        Integer deleted = customer.getDeleted();
        if (deleted == DeletedEnum.DELETED.getValue()) {
            customer.setDeleted(DeletedEnum.UNDELETED.getValue());
            customerDao.save(customer);
        }

        log.info("CustomerServiceImpl recovery method end;");
    }

    @Override
    public CustomerListDTO getDetail(Long id) {
        log.info("CustomerServiceImpl getDetail method start：" + JSONObject.toJSONString(id));
        Customer customer = customerDao.findOne(id);
        if (customer == null) {
            throw new BizException("客户不存在！");
        }
        CustomerListDTO dto = new CustomerListDTO();
        BeanUtils.copyProperties(customer, dto);
        log.info("CustomerServiceImpl getDetail method end;");
        return dto;
    }

    @Override
    public PageDTO<CustomerListDTO> getPageList(CustomerQueryDTO dto) {
        log.info("CustomerServiceImpl getPageList method start：" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<Customer> page = customerDao.findAll(CustomerSpecs.CustomerListQuerySpec(dto), pageRequest);
        PageDTO<CustomerListDTO> pageDTO = PageDTO.of(page, item -> {
            CustomerListDTO customerListDTO = new CustomerListDTO();
            BeanUtils.copyProperties(item, customerListDTO);
            return customerListDTO;
        });
        log.info("CustomerServiceImpl getPageList method end;");
        return pageDTO;
    }

    @Override
    public List<String> getList(String customerName, String customerType) {
        log.info("RegisterServiceImpl getCustomerName method start Parm:" + customerName + "," + customerType);
        List<String> stringList = customerSqlDao.query(customerName, customerType);
        log.info("RegisterServiceImpl getCustomerName method end;");
        return stringList;
    }
}
