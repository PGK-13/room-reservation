package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/5/19
 */
public class StartTimeLateEndTime extends ReservationException{
    public StartTimeLateEndTime() {
        super(ResponseConstant.START_TIME_LATE_END_TIME);
    }

    public StartTimeLateEndTime(String msg) {
        super(msg);
    }
}
