package com.epam.hw3.exception;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
public class BalanceException extends RuntimeException {
    public BalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BalanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BalanceException(Throwable cause) {
        super(cause);
    }

    public BalanceException(String message) {
        super(message);
    }

    public BalanceException() {
        super();
    }
}
