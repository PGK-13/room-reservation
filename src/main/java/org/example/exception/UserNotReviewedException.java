package org.example.exception;

import org.example.constant.ResponseConstant;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public class UserNotReviewedException extends UserException{
    public UserNotReviewedException() {
        super(ResponseConstant.NOT_REVIEWED);
    }

    public UserNotReviewedException(String message) {
        super(message);
    }
}
