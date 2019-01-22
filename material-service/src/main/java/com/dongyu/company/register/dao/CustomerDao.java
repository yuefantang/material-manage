package com.dongyu.company.register.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * 客户查询dao
 *
 * @author TYF
 * @date 2019/1/22
 * @since 1.0.0
 */
@Repository
public class CustomerDao {

    @Autowired
    private EntityManager entityManager;

    public List<String> query(String customerName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT customer_name ");
        sql.append("FROM t_mi_register ");
        if (StringUtils.isNotBlank(customerName)) {
            sql.append("WHERE customer_name LIKE '" + customerName + "%'");
        }
        sql.append("GROUP BY customer_name");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        List<String> resultList = nativeQuery.getResultList();
        return resultList;
    }

}
