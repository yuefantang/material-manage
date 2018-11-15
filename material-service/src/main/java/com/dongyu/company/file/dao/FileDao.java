package com.dongyu.company.file.dao;

import com.dongyu.company.file.domian.CommonFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文件图片数据处理
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
public interface FileDao extends JpaRepository<CommonFile, Long> {
}
