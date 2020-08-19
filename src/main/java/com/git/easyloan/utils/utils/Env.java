package com.git.easyloan.utils.utils;

public class Env {
    public static String webContext;
    public static String webRoot;
    public static String serverAddress;
    public static String serverPort;

    public Env() {
    }

    public static String webRoot() {
        return webRoot;
    }

    public static void setWebRoot(String webRoot) {
        webRoot = webRoot;
    }

    public static String webContext() {
        return webContext;
    }

    public static void setWebContext(String webContext) {
        if (!webContext.startsWith("/")) {
            webContext = "/" + webContext;
        }

        webContext = webContext;
    }

    public static String absolutePath(String relativePath) {
        if (webRoot == null || webRoot.length() < 1) {
            webRoot = System.getProperty("user.dir");
        }

        String absolutePath;
        if (!webRoot.endsWith("\\") && !webRoot.endsWith("/")) {
            absolutePath = webRoot() + "/" + relativePath;
        } else {
            absolutePath = webRoot() + relativePath;
        }

        return absolutePath;
    }
}
