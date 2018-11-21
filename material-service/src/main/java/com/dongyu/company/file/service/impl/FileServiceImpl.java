package com.dongyu.company.file.service.impl;

import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.file.dao.FileDao;
import com.dongyu.company.file.domian.CommonFile;
import com.dongyu.company.file.dto.FileDTO;
import com.dongyu.company.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 文件图片处理ServiceImpl
 *
 * @author TYF
 * @date 2018/11/15
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Value("${uploadDir}")
    private String uploadDir;
    @Autowired
    private FileDao fileDao;

    private final static List<String> fileExt = Arrays.asList("bmp", "jpg", "jpeg", "png", "gif");

    @Override
    @Transactional
    public ResponseVo<FileDTO> upload(MultipartFile file) {
        log.info("FileServiceImpl uploadImage method start：");
        if (file.isEmpty()) {
            throw new BizException("文件不能为空");
        }
        // 获取上传原文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileExt.contains(suffixName.substring(1))) {
            throw new BizException("上传文件格式错误！");
        }

        // 文件上传后的路径
        String filePath = uploadDir;
        // 解决中文问题，liunx下中文路径，图片显示问题,重新定义文件名
        String prefixName = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = prefixName + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //写入文件目录
            file.transferTo(dest);
            //保存文件图片
            CommonFile commonFile = new CommonFile();
            commonFile.setFilePath(filePath);
            commonFile.setFileName(fileName);
            CommonFile save = fileDao.save(commonFile);
            FileDTO fileDTO = new FileDTO();
            BeanUtils.copyProperties(save, fileDTO);
            log.info("FileServiceImpl uploadImage method end ");
            return ResponseVo.successResponse(fileDTO);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseVo.failResponse("500", "文件上传失败");
    }

    /**
     * 文件图片下载
     *
     * @param id       文件图片存储id
     * @param response
     */
    @Override
    public void download(Long id, HttpServletResponse response) {
        log.info("FileServiceImpl download method start Param:" + id);
        if (id == null) {
            throw new BizException("下载文件id不能为空！");
        }
        CommonFile commonFile = Optional.ofNullable(fileDao.findOne(id)).orElseThrow(() -> {
            return new BizException("下载文件所传id不存在！");
        });
        String fileUrl = commonFile.getFilePath() + commonFile.getFileName();
        if (fileUrl != null) {
            File file = new File(fileUrl);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + commonFile.getFileName());// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public Boolean delfile(Long id) {
        log.info("FileServiceImpl delfile method start Param:" + id);
        CommonFile commonFile = fileDao.findOne(id);
        if (commonFile == null) {
            return true;
        }
        try {
            fileDao.delete(id);
            File file = new File(commonFile.getFilePath() + commonFile.getFileName());
            if (!file.exists()) {
                return true;
            } else if (!file.delete()) {
                return false;
            }
        } catch (Exception e) {
            log.info("Exception occured");
            e.printStackTrace();
        }
        return true;
    }
}
