package com.dongyu.company.file.service;

import com.dongyu.company.common.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件图片处理service
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
public interface FileService {

    //文件上传处理
    ResponseVo uploadImage(MultipartFile file);

    //文件下载处理
}
