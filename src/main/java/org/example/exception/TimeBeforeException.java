package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/4/28
 */
public class TimeBeforeException extends ReservationException{
    public TimeBeforeException() {
        super(ResponseConstant.TIME_BEFORE);
    }

    public TimeBeforeException(String message) {
        super(message);
    }
}
