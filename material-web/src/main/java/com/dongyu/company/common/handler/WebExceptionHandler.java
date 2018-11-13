package com.dongyu.company.common.handler;

import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.exception.FileException;
import com.dongyu.company.common.vo.ResponseVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * WEB异常处理器
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@ControllerAdvice("com.dongyu.company")
public class WebExceptionHandler {
    static Logger LOG = LoggerFactory.getLogger(WebExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo<Object> handleSystemException(BizException e) {
        LOG.error(e.getMessage(), e);
        ResponseVo<Object> vo = new ResponseVo<>();
        fillExceptionVO(e, vo);
        setVo(vo);
        return vo;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo handleTypeMismathException(MethodArgumentNotValidException e) {
        ResponseVo<Object> vo = new ResponseVo<>();
        if (e.getBindingResult() != null) {
            for (FieldError item : e.getBindingResult().getFieldErrors()) {
                String itemMessage = item.getDefaultMessage();
                vo.addFieldError(item.getField(), itemMessage);
            }
        }
        setVo(vo);
        return vo;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo globalError(Exception e) {
        LOG.error(e.getMessage(), e);
        ResponseVo<Object> vo = new ResponseVo<>();
        fillExceptionVO(e, vo);
        setVo(vo);
        return vo;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo bindError(BindException e) {
        LOG.error(e.getMessage(), e);
        ResponseVo<Object> vo = new ResponseVo<>();
        if (e.getBindingResult() != null) {
            for (FieldError item : e.getBindingResult().getFieldErrors()) {
                String itemMessage = item.getDefaultMessage();
                vo.addFieldError(item.getField(), itemMessage);
            }
        }
        vo.setMsg("后台校验不通过");
        setVo(vo);
        return vo;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo globalError(UnauthorizedException e) {
        LOG.error(e.getMessage(), e);
        //没有权限访问需要修改
        ResponseVo<Object> vo = new ResponseVo<>();
        vo.setMsg("该用户没有权限");
        setVo(vo);
        return vo;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo authenticationError(AuthenticationException e) {
        LOG.error(e.getMessage(), e);
        ResponseVo<Object> vo = new ResponseVo<>();
        Throwable cause = e.getCause();
        vo.setMsg(cause.getMessage());
        setVo(vo);
        return vo;
    }


    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseVo<Object> handleFileException(FileException e) {
        return ResponseVo.error(getLocaleMessage(e.getMessage(), null));
    }


    /**
     * 填充异常响应消息
     *
     * @param e
     * @param vo
     */
    private void fillExceptionVO(Exception e, ResponseVo<Object> vo) {
        BizException se = (BizException) e;
        if (se.getMessage() != null) {
            String message = e.getMessage();
            try {
                message = messageSource.getMessage(e.getMessage(), se.getArgs(), LocaleContextHolder.getLocale());
            } catch (NoSuchMessageException ex) {
                LOG.warn("no such message key:" + message);
            }
            vo.setMsg(message);
        }
        vo.setErrorCode(se.getErrorCode() == null ? null : String.valueOf(se.getErrorCode()));
    }


    private String getLocaleMessage(String message, Object[] args) {
        try {
            message = messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            LOG.warn("no such message key:" + message);
        }
        return message;
    }

    private void setVo(ResponseVo<Object> vo) {
        vo.setSuccess(false);
        vo.setErrorCode("500");
    }

}
