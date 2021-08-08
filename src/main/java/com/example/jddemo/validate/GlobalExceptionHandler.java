package com.example.jddemo.validate;

import com.example.jddemo.response.ApiResponse;
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
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return new ApiResponse(PARAM_FAIL_CODE, e.getBindingResult().getFieldError().getDefaultMessage(),null);
    }

    /**
     * Validation Exception
     */
    @ExceptionHandler(ValidationException.class)
    public ApiResponse handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return new ApiResponse(VALIDATION_CODE, e.getCause().getMessage(),null);
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return new ApiResponse(PARAM_FAIL_CODE, e.getMessage(),null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ApiResponse(404, "路径不存在，请检查路径是否正确",null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ApiResponse handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return new ApiResponse(DUPLICATE_KEY_CODE, "数据重复，请检查后提交",null);
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse handleBindException(BindException e) {
        logger.error(e.getMessage(), e);
        ObjectError objectError = e.getAllErrors().get(0);
        return new ApiResponse(ApiResponse.CODE_WARN, objectError.getDefaultMessage(),null);
    }


    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ApiResponse(500, "系统繁忙,请稍后再试",null);
    }
}