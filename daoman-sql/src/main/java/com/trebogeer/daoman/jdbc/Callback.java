package com.trebogeer.daoman.jdbc;

/**
 * @author dimav
 *         Date: 7/31/14
 *         Time: 5:41 PM
 */
public interface Callback<T> {
    void execute(T t);
}
