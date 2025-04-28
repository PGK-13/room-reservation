package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.Code;
import org.example.constant.ResponseConstant;
import org.example.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常捕获类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理不可控异常
     * @param e Exception
     * @return ResponseResult<Void>
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        log.error("catch exception:{}",e.getMessage());
        return Result.error(ResponseConstant.SERVER_ERROR);
    }

    /**
     * 处理 user 异常 (登录)
     * @param e
     * @return
     */
    @ExceptionHandler(UserException.class)
    @ResponseBody
    public Result handlerException(UserException e) {
        log.error("catch UserException:{}",e.getMessage());
        return Result.error(Code.USER_ERROR, e.getMessage());
    }

}
