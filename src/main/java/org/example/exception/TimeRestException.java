package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/4/28
 */
public class TimeRestException extends ReservationException{
    public TimeRestException() {
        super(ResponseConstant.TIME_REST);
    }

    public TimeRestException(String message) {
        super(message);
    }
}
