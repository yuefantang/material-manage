package com.dongyu.company.file.service;

import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.file.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件图片处理service
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
public interface FileService {

    //文件上传处理
    ResponseVo<FileDTO> upload(MultipartFile file);

    //文件下载处理
    void download(Long id, HttpServletResponse response);
}
