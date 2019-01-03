package com.dongyu.company.operation.dao;

import com.dongyu.company.operation.domain.OperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 日志记录数据操作
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
public interface OperationRecordDao extends JpaRepository<OperationRecord, Long>, JpaSpecificationExecutor<OperationRecord> {
}
