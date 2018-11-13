package com.dongyu.company.common.exception;

import java.io.Serializable;

/**
 * TODO:请添加描述
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
public class FileException extends Exception implements Serializable {

    public FileException(String message) {
        super(message);
    }
}
