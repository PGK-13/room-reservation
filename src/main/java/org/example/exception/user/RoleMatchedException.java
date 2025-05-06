package org.example.exception.user;

import org.example.constant.ResponseConstant;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class RoleMatchedException extends UserException {
    public RoleMatchedException() {
        super(ResponseConstant.ROLE_CANT_MATCHED);
    }

    public RoleMatchedException(String message) {
        super(message);
    }
}
