package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/4/28
 */
public class TimeOver60Exception extends ReservationException{
    public TimeOver60Exception() {
        super(ResponseConstant.TIME_OVER_60);
    }

    public TimeOver60Exception(String message) {
        super(message);
    }
}
