package com.dongyu.company.file.dao;

import com.dongyu.company.file.domian.CommonFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 文件图片数据处理
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
public interface FileDao extends JpaRepository<CommonFile, Long> {

    /**
     * 查询出与MI登记没有关联的无用图片
     */
    @Query(value = "SELECT c.* FROM t_mi_register m RIGHT JOIN t_common_file c ON m.`common_file_id`=c.`id` WHERE m.`id` IS NULL", nativeQuery = true)
    List<CommonFile> findDiscardFile();
}
