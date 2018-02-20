package com.cryptoquack.model;

import com.cryptoquack.model.exchange.Gemini.GeminiExchange;
import com.cryptoquack.model.logger.ILogger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Duke on 2/19/2018.
 */

@Module()
public class ModelModule {

    @Named("real")
    @Singleton
    @Provides
    public GeminiExchange providesRealGeminiExchange(ILogger logger) {
        return new GeminiExchange(true, logger);
    }

    @Named("sandbox")
    @Singleton
    @Provides
    public GeminiExchange providesSandboxGeminiExchange(ILogger logger) {
        return new GeminiExchange(false, logger);
    }
}
