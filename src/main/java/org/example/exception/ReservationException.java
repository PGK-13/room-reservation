package org.example.exception;

/**
 * @author zklee
 * date 2025/4/28
 */
public class ReservationException extends RuntimeException{
    public ReservationException() {
    }

    public ReservationException(String message) {
        super(message);
    }
}
