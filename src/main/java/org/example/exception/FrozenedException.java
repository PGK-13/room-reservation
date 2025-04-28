package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class FrozenedException extends UserException{
    public FrozenedException() {
        super(ResponseConstant.FORZENED);
    }

    public FrozenedException(String message) {
        super(message);
    }
}
