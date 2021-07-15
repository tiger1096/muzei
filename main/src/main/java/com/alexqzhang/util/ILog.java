package com.alexqzhang.util;

/**
 * Created by camusli on 28/6/26.
 */
public interface ILog {

    /**
     * <p>
     * Log a message with verbose log level.
     * </p>
     */
    public void v(String tag, String message);

    public void v(String tag, String message, Throwable t);

    /**
     * <p>
     * Log a message with debug log level.
     * </p>
     */
    public void d(String tag, String message);

    public void d(String tag, String message, Throwable t);

    /**
     * <p>
     * Log a message with info log level.
     * </p>
     */
    public void i(String tag, String message);

    public void i(String tag, String message, Throwable t);

    /**
     * <p>
     * Log a message with warn log level.
     * </p>
     */
    public void w(String tag, String message);

    public void w(String tag, String message, Throwable t);

    /**
     * <p>
     * Log a message with error log level.
     * </p>
     */
    public void e(String tag, String message);

    public void e(String tag, String message, Throwable t);

}
