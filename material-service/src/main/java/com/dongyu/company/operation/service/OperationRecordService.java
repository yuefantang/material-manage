package com.dongyu.company.operation.service;

import com.dongyu.company.operation.dto.OperationQueryDTO;
import com.dongyu.company.operation.dto.OperationRecordTDO;

import java.util.List;

/**
 * 日志管理service
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
public interface OperationRecordService {

    //日志查询
    List<OperationRecordTDO> findOperation(OperationQueryDTO dto);
}
