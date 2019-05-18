package com.dongyu.company.customer.dao;

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
public class CustomerSqlDao {

    @Autowired
    private EntityManager entityManager;

    public List<String> query(String customerName, String customerType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT customer_name ");
        sql.append("from t_customer ");
        sql.append("WHERE ");
        sql.append("customer_type= '" + customerType + "'");
        if (StringUtils.isNotBlank(customerName)) {
            sql.append("AND customer_name LIKE '" + customerName + "%'");
        }
        sql.append("AND deleted='0' ");
        sql.append("ORDER BY create_time DESC");
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        List<String> resultList = nativeQuery.getResultList();
        return resultList;
    }

}
