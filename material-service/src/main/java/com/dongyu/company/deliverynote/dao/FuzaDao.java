package com.dongyu.company.deliverynote.dao;

import com.dongyu.company.deliverynote.dto.DeliveryQueryDTO;
import com.dongyu.company.deliverynote.dto.Fuz;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 财务对账查询
 *
 * @author TYF
 * @date 2019/1/8
 * @since 1.0.0
 */
@Repository
public class FuzaDao {
    @Autowired
    private EntityManager entityManager;

    public List<Fuz> query(DeliveryQueryDTO deliveryQueryDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*)AS total,entity_id as id FROM t_operation_record  GROUP BY entity,entity_id");
        SQLQuery sqlQuery = entityManager.createNativeQuery(sql.toString()).unwrap(SQLQuery.class);
        Query query =sqlQuery.setResultTransformer(Transformers.aliasToBean(Fuz.class));
        query.setFirstResult(deliveryQueryDTO.getPageNo() * deliveryQueryDTO.getPageSize());
        query.setMaxResults(deliveryQueryDTO.getPageSize());
        List<Fuz> resultList = query.list();
        entityManager.close();
        return resultList;
    }

}
