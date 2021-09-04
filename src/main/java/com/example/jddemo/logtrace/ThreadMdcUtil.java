package com.example.jddemo.logtrace;

import com.example.jddemo.logtrace.LogInterceptor;
import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 程序员  by dell
 * time  2021-07-10
 **/

public class ThreadMdcUtil {
    public static void setTraceIdIfAbsent() {
        if (MDC.get(LogInterceptor.TRACE_ID) == null) {
            MDC.put(LogInterceptor.TRACE_ID, getTraceId());
        }
    }

    private static String getTraceId() {
      return   UUID.randomUUID().toString().replace("-", "");
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
