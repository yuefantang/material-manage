package com.dongyu.company.quality.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.quality.dto.AddQualityDTO;
import com.dongyu.company.quality.dto.QualityDetailDTO;
import com.dongyu.company.quality.dto.QualityListDTO;
import com.dongyu.company.quality.dto.QualityQueryDTO;

/**
 * 品质问题Service
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
public interface QualityService {
    /**
     * 品质问题新增
     *
     * @param addQualityDTO
     */
    void add(AddQualityDTO addQualityDTO);

    /**
     * 品质问题分页查询
     *
     * @param qualityQueryDTO
     * @return
     */
    PageDTO<QualityListDTO> getlist(QualityQueryDTO qualityQueryDTO);


    /**
     * 品质问题修改
     *
     * @param qualityDetailDTO
     */
    void edit(QualityDetailDTO qualityDetailDTO);

    /**
     * 品质问题删除
     *
     * @param id 品质问题id
     */
    void deleted(Long id);

    /**
     * 恢复品质问题删除
     *
     * @param id 品质问题id
     */
    void recovery(Long id);

    /**
     * 品质问题详情
     *
     * @param id 品质问题id
     * @return
     */
    QualityDetailDTO getDetail(Long id);
}
