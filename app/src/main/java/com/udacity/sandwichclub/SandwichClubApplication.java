package com.udacity.sandwichclub;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class SandwichClubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(final @NotNull StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
