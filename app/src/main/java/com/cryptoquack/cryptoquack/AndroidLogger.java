package com.cryptoquack.cryptoquack;

import com.cryptoquack.model.ILogger;

import org.jetbrains.annotations.NonNls;

import timber.log.Timber;

/**
 * Created by Duke on 2/19/2018.
 */

public class AndroidLogger implements ILogger {

    public AndroidLogger(Timber.Tree tree) {
        Timber.plant(tree);
    }

    public void v(@NonNls String message, Object... args) {
        Timber.v(message, args);
    }

    public void v(Throwable t, @NonNls String message, Object... args) {
        Timber.v(t, message, args);
    }

    public void v(Throwable t) {
        Timber.v(t);
    }

    public void d(@NonNls String message, Object... args) {
        Timber.d(message, args);
    }

    public void d(Throwable t, @NonNls String message, Object... args) {
        Timber.d(t, message, args);
    }

    public void d(Throwable t) {
        Timber.d(t);
    }

    public void i(@NonNls String message, Object... args) {
        Timber.i(message, args);
    }

    public void i(Throwable t, @NonNls String message, Object... args) {
        Timber.i(t, message, args);
    }

    public void i(Throwable t) {
        Timber.i(t);
    }

    public void w(@NonNls String message, Object... args) {
        Timber.w(message, args);
    }

    public void w(Throwable t, @NonNls String message, Object... args) {
        Timber.w(t, message, args);
    }

    public void w(Throwable t) {
        Timber.w(t);
    }

    public void e(@NonNls String message, Object... args) {
        Timber.e(message, args);
    }

    public void e(Throwable t, @NonNls String message, Object... args) {
        Timber.e(t, message, args);
    }

    public void e(Throwable t) {
        Timber.e(t);
    }

    public void log(int priority, @NonNls String message, Object... args) {
        Timber.log(priority, message, args);
    }

    public void log(int priority, Throwable t, @NonNls String message, Object... args) {
        Timber.log(priority, t, message, args);
    }

    public void log(int priority, Throwable t) {
        Timber.log(priority, t);
    }
}
