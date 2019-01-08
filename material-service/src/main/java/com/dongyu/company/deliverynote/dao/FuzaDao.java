package com.dongyu.company.deliverynote.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

    public void  query(){
        StringBuilder sql=new StringBuilder();

        sql.append("");

        //查询结果
        Query query = entityManager.createNativeQuery(sql.toString());
        //查询总数


    }

}
