package com.trebogeer.daoman.jdbc;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:42 PM
 */
public class VRuntime extends RuntimeException {
    public VRuntime() {
    }

    public VRuntime(String message) {
        super(message);
    }

    public VRuntime(String message, Throwable cause) {
        super(message, cause);
    }

    public VRuntime(Throwable cause) {
        super(cause);
    }

    public VRuntime(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
