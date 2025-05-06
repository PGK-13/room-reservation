package org.example.exception.user;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class UserException extends RuntimeException{
    public UserException() {

    }

    public UserException(String message) {
        super(message);
    }
}
