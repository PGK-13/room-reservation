package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class UserNotExistedException extends UserException{
    public UserNotExistedException() {super(ResponseConstant.USER_NOT_EXIST);}

    /**
     * 自定义报错信息
     * @param message
     */
    public UserNotExistedException(String message) {
        super(message);
    }
}
