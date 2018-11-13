package com.dongyu.company.common.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Data
@MappedSuperclass
public abstract class BaseDomain {

    @Column(name = "create_time")
    @CreatedDate
    private Date createTime;

    @Column(name = "create_by")
    @CreatedBy
    private Long createBy;

    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "update_By")
    @LastModifiedBy
    private Long updateBy;


}
