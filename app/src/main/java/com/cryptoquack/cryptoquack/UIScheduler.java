package com.cryptoquack.cryptoquack;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Duke on 2/17/2018.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface UIScheduler {
    String value() default "";
}
