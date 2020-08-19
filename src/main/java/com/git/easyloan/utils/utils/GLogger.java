package com.git.easyloan.utils.utils;

import java.io.PrintStream;

public class GLogger {
    public static final int TRACE = 60;
    public static final int DEBUG = 70;
    public static final int INFO = 80;
    public static final int WARN = 90;
    public static final int ERROR = 100;
    public static int logLevel = 80;
    public static PrintStream out;
    public static PrintStream err;
    public static boolean perf;

    public GLogger() {
    }

    public static void trace(String s) {
        if (logLevel <= 60) {
            out.println("[Generator TRACE] " + s);
        }

    }

    public static void debug(String s) {
        if (logLevel <= 70) {
            out.println("[Generator DEBUG] " + s);
        }

    }

    public static void info(String s) {
        if (logLevel <= 80) {
            out.println("[Generator INFO] " + s);
        }

    }

    public static void warn(String s) {
        if (logLevel <= 90) {
            err.println("[Generator WARN] " + s);
        }

    }

    public static void warn(String s, Throwable e) {
        if (logLevel <= 90) {
            err.println("[Generator WARN] " + s + " cause:" + e);
            e.printStackTrace(err);
        }

    }

    public static void error(String s) {
        if (logLevel <= 100) {
            err.println("[Generator ERROR] " + s);
        }

    }

    public static void error(String s, Throwable e) {
        if (logLevel <= 100) {
            err.println("[Generator ERROR] " + s + " cause:" + e);
            e.printStackTrace(err);
        }

    }

    public static void perf(String s) {
        if (perf) {
            out.println("[Generator Performance] " + s);
        }

    }

    public static void println(String s) {
        if (logLevel <= 80) {
            out.println(s);
        }

    }

    static {
        out = System.out;
        err = System.err;
        perf = false;
    }
}
