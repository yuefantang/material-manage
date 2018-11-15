package com.dongyu.company.file.domian;

import com.dongyu.company.common.domain.BaseDomain;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件图片实体类
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@Entity
@Data
@Table(name = "t_common_file")
@EntityListeners({AuditingEntityListener.class})
public class CommonFile extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //文件路径
    @Length(max = 255)
    @Column(name = "file_name", unique = true)
    private String filePath;

    //文件后缀名
    @Length(max = 30)
    @Column(name = "suffix_name")
    private String suffixName;

}
