package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class UserExistedException extends UserException{
    public UserExistedException() {
        super(ResponseConstant.USER_ALREADY_EXIST);
    }

    public UserExistedException(String message) {
        super(message);
    }
}
