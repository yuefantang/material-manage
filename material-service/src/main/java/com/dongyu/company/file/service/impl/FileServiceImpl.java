package com.dongyu.company.file.service.impl;

import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.file.dao.FileDao;
import com.dongyu.company.file.domian.CommonFile;
import com.dongyu.company.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Override
    @Transactional
    public ResponseVo uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BizException("文件不能为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        String filePath = uploadDir;
        // 解决中文问题，liunx下中文路径，图片显示问题
        fileName = UUID.randomUUID() + suffixName;
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
            commonFile.setFilePath(filePath + fileName);
            commonFile.setSuffixName(suffixName);
            CommonFile save = fileDao.save(commonFile);
            return ResponseVo.successResponse(save.getId());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseVo.failResponse("500", "文件上传失败");
    }
}
