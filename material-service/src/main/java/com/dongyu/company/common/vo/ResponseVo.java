package com.dongyu.company.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel(description = "返回Vo")
public class ResponseVo<V> {
    @ApiModelProperty(value = "异常Code，可选", example = "null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode = "200";
    @ApiModelProperty("业务是否成功")
    private Boolean success;
    @ApiModelProperty("分字段出错信息，列如表单中某一个字段")
    private List<FieldExceptionVo> fieldErrors;
    @ApiModelProperty("全局出错信息")
    private String msg;
    @ApiModelProperty("返回内容")
    private V data;

    public ResponseVo() {
    }

    public ResponseVo(Boolean success, List<FieldExceptionVo> fieldErrors, String msg, V data) {
        super();
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVo(Boolean success, List<FieldExceptionVo> fieldErrors, String msg) {
        this.success = success;
        this.fieldErrors = fieldErrors;
        this.msg = msg;
    }

    public ResponseVo(String errorCode, Boolean success, String msg) {
        this.errorCode = errorCode;
        this.success = success;
        this.msg = msg;
    }

    public static <T> ResponseVo<T> response(boolean success, List<FieldExceptionVo> fieldErrors, String msg) {
        return new ResponseVo<>(success, fieldErrors, msg);
    }

    public static <T> ResponseVo<T> response(boolean success, List<FieldExceptionVo> fieldErrors, String msg, T data) {
        return new ResponseVo<>(success, fieldErrors, msg, data);
    }

    public static <T> ResponseVo<T> successResponse(T data) {
        return response(true, Collections.emptyList(), "请求成功!", data);

    }

    public static <T> ResponseVo<T> successResponse() {
        return response(true, Collections.emptyList(), "请求成功!");

    }

    public void addFieldError(String fieldName, String errorCode) {
        if (this.getFieldErrors() == null) {
            this.fieldErrors = new ArrayList<>();
        }
        this.fieldErrors.add(new FieldExceptionVo(fieldName, errorCode));
    }

    public static <T> ResponseVo<T> failResponse(String errorCode, String msg) {
        return new ResponseVo<>(errorCode, false, msg);
    }

    public static <T> ResponseVo<T> error(String msg) {
        return new ResponseVo<>(false, null, msg, null);
    }


}
