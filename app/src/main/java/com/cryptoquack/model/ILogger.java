package com.cryptoquack.model;

import org.jetbrains.annotations.NonNls;

/**
 * Created by Duke on 2/19/2018.
 */

public interface ILogger {

    public void v(@NonNls String message, Object... args);

    public void v(Throwable t, @NonNls String message, Object... args);

    public void v(Throwable t);

    public void d(@NonNls String message, Object... args);

    public void d(Throwable t, @NonNls String message, Object... args);

    public void d(Throwable t);

    public void i(@NonNls String message, Object... args);

    public void i(Throwable t, @NonNls String message, Object... args);

    public void i(Throwable t);

    public void w(@NonNls String message, Object... args);

    public void w(Throwable t, @NonNls String message, Object... args);

    public void w(Throwable t);

    public void e(@NonNls String message, Object... args);

    public void e(Throwable t, @NonNls String message, Object... args);

    public void e(Throwable t);

    public void log(int priority, @NonNls String message, Object... args);

    public void log(int priority, Throwable t, @NonNls String message, Object... args);

    public void log(int priority, Throwable t);
}
