package com.dongyu.company.dict.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.dict.dto.DictResultDTO;
import com.dongyu.company.dict.dto.StaticDataDTO;
import com.dongyu.company.dict.dto.StaticQueryDTO;

import java.util.List;
import java.util.Map;

/**
 * 字典相关Service
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
public interface DictService {

    //获取下拉列表枚举相关字典
    DictResultDTO getDictList();

    //获取静态数据
    Map<String, List<StaticDataDTO>> getStaticData();

    //新增下拉列表数据
    void add(StaticDataDTO dto);

    //查询下拉列表数据
    PageDTO<StaticDataDTO> getlist(StaticQueryDTO queryDTO);

    /**
     * 删除列表数据
     *
     * @param id 列表数据id
     */
    void deleted(Long id);

    /**
     * 恢复删除列表数据
     *
     * @param id 列表数据id
     */
    void recovery(Long id);


}
