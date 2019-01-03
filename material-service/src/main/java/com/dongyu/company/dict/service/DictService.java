package com.dongyu.company.dict.service;

import com.dongyu.company.dict.dto.DictResultDTO;

/**
 * 字典相关Service
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
public interface DictService {

    //获取下拉列表相关字典
    DictResultDTO getDictList();
}
