package com.dongyu.company.order.domain;

import com.dongyu.company.order.dto.OrderQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * 下单原生sql多表查询
 *
 * @author TYF
 * @date 2018/11/27
 * @since 1.0.0
 */
@Repository
public class OrderSqlDao {
    @Autowired
    private EntityManager entityManager;

    public Page<Object[]> queryOrder(OrderQueryDTO orderQueryDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        sql.append(" SELECT o.commissioning_code,o.order_dy_code ,m.`customer_name`,o.order_num,o.ustomerc_order_code,o.order_date,o.delivery_date ");
        sql.append(" FROM t_order o LEFT JOIN t_mi_register m ON o.order_dy_code=m.`mi_dy_code` ");
        if (StringUtils.isNotBlank(orderQueryDTO.getCommissioningCode())) {
            sql.append(" WHERE o.commissioning_code LIKE '%" + orderQueryDTO.getCommissioningCode() + "%'");
        }
        sql.append("ORDER BY o.credate_time DESC");
        Query query = this.entityManager.createNativeQuery(sql.toString());
        //获取查询总数
        countSql.append("SELECT count(1) from (" + sql + ")oo");
        Query countQuery = this.entityManager.createNativeQuery(countSql.toString());
        Long count = Long.valueOf(String.valueOf(countQuery.getSingleResult()));
        //分页查询参数设置
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults((pageNumber + 1) * pageSize);
        List resultList = query.getResultList();
        if (entityManager != null) {
            entityManager.close();
        }
        Page<Object[]> page = new PageImpl<>(resultList, pageable, count);
        return page;
    }


}
