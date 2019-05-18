package com.dongyu.company.operation.dao;

import com.dongyu.company.operation.domain.OperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 日志记录数据操作
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
public interface OperationRecordRepository extends JpaRepository<OperationRecord, Long>, JpaSpecificationExecutor<OperationRecord> {

    //根据表名和id查询日志
    List<OperationRecord> findByEntityAndEntityIdOrderByCreateTimeDesc(String entity, Long entityId);
}
