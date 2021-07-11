package com.example.jddemo.validate;

import com.example.jddemo.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;


/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static int DUPLICATE_KEY_CODE = 1001;
    private static int PARAM_FAIL_CODE = 1002;
    private static int VALIDATION_CODE = 1003;



    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return new CommonResponse(PARAM_FAIL_CODE, e.getBindingResult().getFieldError().getDefaultMessage(),null);
    }

    /**
     * Validation Exception
     */
    @ExceptionHandler(ValidationException.class)
    public CommonResponse handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return new CommonResponse(VALIDATION_CODE, e.getCause().getMessage(),null);
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return new CommonResponse(PARAM_FAIL_CODE, e.getMessage(),null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResponse handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return new CommonResponse(404, "路径不存在，请检查路径是否正确",null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public CommonResponse handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return new CommonResponse(DUPLICATE_KEY_CODE, "数据重复，请检查后提交",null);
    }

    @ExceptionHandler(BindException.class)
    public CommonResponse handleBindException(BindException e) {
        logger.error(e.getMessage(), e);
        ObjectError objectError = e.getAllErrors().get(0);
        return new CommonResponse(CommonResponse.CODE_WARN, objectError.getDefaultMessage(),null);
    }


    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return new CommonResponse(500, "系统繁忙,请稍后再试",null);
    }
}