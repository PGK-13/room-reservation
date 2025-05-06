package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/4/28
 */
public class TimeRequiredException extends ReservationException{
    public TimeRequiredException() {
        super(ResponseConstant.TIME_REQUIRED);
    }

    public TimeRequiredException(String message) {
        super(message);
    }
}
