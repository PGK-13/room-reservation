package org.example.exception.user;

import org.example.constant.ResponseConstant;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class PasswordErrorException extends UserException {
    public PasswordErrorException() {
        super(ResponseConstant.PASSWORD_ERROR);
    }

    public PasswordErrorException(String message) {
        super(message);
    }
}
