package com.dongyu.company.quality.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.constants.DeletedEnum;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.DateUtil;
import com.dongyu.company.quality.dao.QualityDao;
import com.dongyu.company.quality.dao.QualitySpecs;
import com.dongyu.company.quality.domain.Quality;
import com.dongyu.company.quality.dto.AddQualityDTO;
import com.dongyu.company.quality.dto.EditQualityDTO;
import com.dongyu.company.quality.dto.QualityDetailDTO;
import com.dongyu.company.quality.dto.QualityListDTO;
import com.dongyu.company.quality.dto.QualityQueryDTO;
import com.dongyu.company.quality.service.QualityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private QualityDao qualityDao;

    @Override
    public void add(AddQualityDTO addQualityDTO) {
        log.info("QualityServiceImpl add method start Parm:" + JSONObject.toJSONString(addQualityDTO));
        Quality quality = new Quality();
        BeanUtils.copyProperties(addQualityDTO, quality);
        qualityDao.save(quality);
        log.info("QualityServiceImpl add method end;");
    }

    @Override
    public PageDTO<QualityListDTO> getlist(QualityQueryDTO dto) {
        log.info("QualityServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        PageRequest pageRequest = new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.DESC, Constants.CREATE_TIME);
        Page<Quality> page = qualityDao.findAll(QualitySpecs.qualityListQuerySpec(dto), pageRequest);
        PageDTO<QualityListDTO> pageDTO = PageDTO.of(page, item -> {
            QualityListDTO qualityListDTO = new QualityListDTO();
            BeanUtils.copyProperties(item, qualityListDTO);
            qualityListDTO.setProblemTime(DateUtil.parseDateToStr(item.getProblemTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            return qualityListDTO;
        });
        log.info("QualityServiceImpl getlist method end;");
        return pageDTO;

    }

    @Override
    public void edit(EditQualityDTO editQualityDTO) {
        log.info("QualityServiceImpl edit method start Parm:" + JSONObject.toJSONString(editQualityDTO));
        Quality oldQuality = qualityDao.findOne(editQualityDTO.getId());
        BeanUtils.copyProperties(editQualityDTO, oldQuality);
        qualityDao.save(oldQuality);
        log.info("QualityServiceImpl edit method end;");
    }

    @Override
    public void deleted(Long id) {
        log.info("QualityServiceImpl deleted method start Parm:" + id);
        Quality quality = qualityDao.findOne(id);
        if (quality == null) {
            throw new BizException("不存在该品质问题id");
        }
        quality.setDeleted(DeletedEnum.DELETED.getValue());
        qualityDao.save(quality);
        log.info("QualityServiceImpl deleted method end:");
    }

    @Override
    public void recovery(Long id) {
        log.info("QualityServiceImpl recovery method start Parm:" + id);
        Quality quality = qualityDao.findOne(id);
        if (quality == null) {
            throw new BizException("不存在该品质问题id");
        }
        quality.setDeleted(DeletedEnum.UNDELETED.getValue());
        qualityDao.save(quality);
        log.info("QualityServiceImpl recovery method end;");
    }

    @Override
    public QualityDetailDTO getDetail(Long id) {
        log.info("QualityServiceImpl getDetail method start Parm:" + id);
        Quality quality = qualityDao.findOne(id);
        if (quality == null) {
            throw new BizException("不存在该品质问题id");
        }
        QualityDetailDTO qualityDetailDTO = new QualityDetailDTO();
        BeanUtils.copyProperties(quality, qualityDetailDTO);
        qualityDetailDTO.setProblemTime(DateUtil.parseDateToStr(quality.getProblemTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        log.info("QualityServiceImpl getDetail method end;");
        return qualityDetailDTO;
    }
}
