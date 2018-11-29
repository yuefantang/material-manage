package com.dongyu.company.job;

import com.dongyu.company.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 文件定时删除处理
 *
 * @author TYF
 * @date 2018/11/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class FileJob {
    @Autowired
    private FileService fileService;

    /**
     * 文件定时删除处理
     */
    //@Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "${job.file.delete.expiration}")
    public void fileDelete() {
        log.info("FileJob fileDelete method start:");
        if (!fileService.deleteFile()) {
            log.info("文件删除失败");
        }
        log.info("FileJob fileDelete method end:");
    }

}
