/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.android;

import android.util.Log;

import com.yahoo.squidb.utility.Logger;
import com.yahoo.squidb.utility.SquidbLog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Logger implementation that logs using {@link android.util.Log}
 */
public class AndroidLogger implements Logger {

    @Override
    public void log(@Nonnull SquidbLog.Level level, @Nullable String tag, @Nullable String message,
            @Nullable Throwable t) {
        switch (level) {
            case INFO:
                Log.i(tag, message, t);
                break;
            case DEBUG:
                Log.d(tag, message, t);
                break;
            case WARN:
                Log.w(tag, message, t);
                break;
            case ERROR:
                Log.e(tag, message, t);
                break;
            case ASSERT:
                Log.wtf(tag, message, t);
                break;
        }
    }
}
