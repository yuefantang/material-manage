package com.dongyu.company.finance.dao;

import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.finance.dto.BillStatisticsDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 财务对账统计查询
 *
 * @author TYF
 * @date 2019/1/8
 * @since 1.0.0
 */
@Repository
public class BillStatisticsDao {
    @Autowired
    private EntityManager entityManager;

    public BillStatisticsDTO query(DeliveryQueryDTO dto) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT totalAmount, unverifiedAmount, verifyAmount ");
        sql.append("FROM ");
        //总金额统计
        sql.append("(SELECT IFNULL(SUM(amount),0) totalAmount FROM t_delivery_note WHERE 1=1 ");
        if (StringUtils.isNotBlank(dto.getCustomerName())) {
            sql.append("AND customer_name LIKE '" + dto.getCustomerName() + "%' ");
        }
        if (StringUtils.isNotBlank(dto.getBillMonth())) {
            sql.append("AND bill_month = '" + dto.getBillMonth() + "' ");
        }
        sql.append(") t, ");
        //未核实金额统计
        sql.append("(SELECT IFNULL(SUM(amount),0) unverifiedAmount  FROM t_delivery_note WHERE 1=1 ");
        if (StringUtils.isNotBlank(dto.getCustomerName())) {
            sql.append("AND customer_name LIKE '" + dto.getCustomerName() + "%' ");
        }
        if (StringUtils.isNotBlank(dto.getBillMonth())) {
            sql.append("AND bill_month = '" + dto.getBillMonth() + "' ");
        }
        sql.append("AND verify_state = '0' ");
        sql.append(") u, ");
        //已核实金额统计
        sql.append("(SELECT IFNULL(SUM(amount),0) verifyAmount  FROM t_delivery_note WHERE 1=1 ");
        if (StringUtils.isNotBlank(dto.getCustomerName())) {
            sql.append("AND customer_name LIKE '" + dto.getCustomerName() + "%' ");
        }
        if (StringUtils.isNotBlank(dto.getBillMonth())) {
            sql.append("AND bill_month = '" + dto.getBillMonth() + "' ");
        }
        sql.append("AND verify_state = '1' ");
        sql.append(") v ");

        SQLQuery sqlQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class);
        Query query = sqlQuery.setResultTransformer(Transformers.aliasToBean(BillStatisticsDTO.class));
//        query.setFirstResult(deliveryQueryDTO.getPageNo() * deliveryQueryDTO.getPageSize());
////        query.setMaxResults(deliveryQueryDTO.getPageSize());
        List<BillStatisticsDTO> resultList = query.list();
        entityManager.close();
        return resultList.get(0);
    }

}
