package com.dongyu.company.quality.service.impl;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.quality.dto.AddQualityDTO;
import com.dongyu.company.quality.dto.QualityDetailDTO;
import com.dongyu.company.quality.dto.QualityListDTO;
import com.dongyu.company.quality.dto.QualityQueryDTO;
import com.dongyu.company.quality.service.QualityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 品质问题Service实现类
 *
 * @author TYF
 * @date 2019/1/5
 * @since 1.0.0
 */
@Slf4j
@Service
public class QualityServiceImpl implements QualityService {

    @Override
    public void add(AddQualityDTO addQualityDTO) {

    }

    @Override
    public PageDTO<QualityListDTO> getlist(QualityQueryDTO qualityQueryDTO) {
        return null;
    }

    @Override
    public void edit(QualityDetailDTO qualityDetailDTO) {

    }

    @Override
    public void deleted(Long id) {

    }

    @Override
    public void recovery(Long id) {

    }

    @Override
    public QualityDetailDTO getDetail(Long id) {
        return null;
    }
}
