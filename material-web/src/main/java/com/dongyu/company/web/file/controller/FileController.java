package com.dongyu.company.web.file.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

/**
 * 文件图片上传下载相关
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@RestController
@Api(tags = "FileController", description = "文件图片上传下载相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation("文件图片上传")
    @PostMapping(value = "/upload")
    public ResponseVo upload(@RequestParam("file") MultipartFile file) {
        ResponseVo responseVo = fileService.uploadImage(file);
        return responseVo;
    }

    @ApiOperation("文件图片下载")
    @GetMapping(value = "/download")
    public void download(@ApiParam(name = "id", value = "文件图片存储ID") @RequestParam(value = "id",defaultValue ="0") Long id, HttpServletResponse response) {
        fileService.download(id, response);
    }
}
