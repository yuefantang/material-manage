package com.dongyu.company.finance.dao;

import com.dongyu.company.common.transform.Transformer;
import com.dongyu.company.finance.domain.MiPrice;
import com.dongyu.company.finance.dto.MiPriceQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * MI登记价格查询
 *
 * @author TYF
 * @date 2019/5/18
 * @since 1.0.0
 */
@Repository
public class MiPriceSqlDao {

    @Autowired
    private EntityManager entityManager;

    public Page<MiPrice> queryMiPrice(MiPriceQueryDTO queryDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        sql.append(" SELECT p.*  ");
        sql.append(" FROM t_mi_price p,t_mi_register r ");
        sql.append(" WHERE p.mi_dy_code=r.mi_dy_code ");
        //根据DY编号模糊查询
        if (StringUtils.isNotBlank(queryDTO.getMiDyCode())) {
            sql.append(" AND p.mi_dy_code LIKE '%" + queryDTO.getMiDyCode() + "%' ");
        }
        //根据客户名称模糊查询
        if (StringUtils.isNotBlank(queryDTO.getCustomerName())) {
            sql.append(" AND r.customer_name LIKE '%" + queryDTO.getCustomerName() + "%' ");
        }
        //根据客户型号模糊查询
        if (StringUtils.isNotBlank(queryDTO.getCustomerModel())) {
            sql.append(" AND r.customer_model LIKE '%" + queryDTO.getCustomerModel() + "%' ");
        }
        //根据MI登记价格是否删除
        if (queryDTO.getDeleted() != null) {
            sql.append(" AND p.deleted='" + queryDTO.getDeleted() + "' ");
        }
        sql.append(" ORDER BY p.create_time DESC ");

        //获取查询总数
        countSql.append("SELECT count(1) from (" + sql + ")oo");

        SQLQuery sqlQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class);
        Query query = sqlQuery.setResultTransformer(Transformer.aliasToBean(MiPrice.class));
        javax.persistence.Query countQuery = this.entityManager.createNativeQuery(countSql.toString());
        Long count = Long.valueOf(String.valueOf(countQuery.getSingleResult()));
        //分页查询参数设置
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults((pageNumber + 1) * pageSize);
        List<MiPrice> list = query.list();
        if (entityManager != null) {
            entityManager.close();
        }
        Page<MiPrice> page = new PageImpl<>(list, pageable, count);
        return page;
    }


}
