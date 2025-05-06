package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @author zklee
 * date 2025/4/29
 */
public class MoneyNotEnoughException extends ReservationException{
    public MoneyNotEnoughException() {
        super(ResponseConstant.MONEY_NOT_ENOUGH);
    }

    public MoneyNotEnoughException(String message) {
        super(message);
    }
}
