package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/5/12
 */
public class NoCurrentDayException extends ReservationException{
    public NoCurrentDayException() {
        super(ResponseConstant.TIME_CURRENTDAY);
    }

    public NoCurrentDayException(String msg) {
        super(msg);
    }
}
