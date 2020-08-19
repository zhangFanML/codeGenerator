package com.git.easyloan.utils.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class JDKUtils {
    private static Log log = LogFactory.getLog(JDKUtils.class);

    public JDKUtils() {
    }

    public static String getCaller(int seq) {
        String caller = "";
        StackTraceElement[] stack = (new Throwable()).getStackTrace();

        for(int i = 0; i < stack.length; ++i) {
            StackTraceElement ste = stack[i];
            log.debug(i + "--" + ste.getFileName() + " " + ste.getClassName() + "." + ste.getMethodName() + "() " + ste.getLineNumber());
            if (i == seq) {
                caller = ste.getFileName() + " " + ste.getClassName() + "." + ste.getMethodName() + "() " + ste.getLineNumber();
                break;
            }
        }

        return caller;
    }

    public static String getCallers() {
        StringBuffer caller = new StringBuffer();
        StackTraceElement[] stack = (new Throwable()).getStackTrace();

        for(int i = 0; i < stack.length; ++i) {
            StackTraceElement ste = stack[i];
            String line = ste.getClassName() + "." + ste.getMethodName() + "()[" + ste.getLineNumber() + "行]";
            if (line.indexOf("easyloan") > -1) {
                caller.append(line);
                caller.append("<br>\r\n");
            }
        }

        log.debug(caller.toString());
        return caller.toString();
    }

    public static String jdkRunTime(String command) throws IOException {
        String systemType = "";
        long sTime = (new Date()).getTime();
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        System.out.println(os);
        if (!os.startsWith("win") && !os.startsWith("Win")) {
            systemType = "linux";
        } else {
            systemType = "windows";
        }

        Process process = null;

        try {
            String[] cmdarray = null;
            Runtime runtime = Runtime.getRuntime();
            if ("windows".equals(systemType)) {
                cmdarray = new String[]{"cmd.exe", "/C", command};
                process = runtime.exec(cmdarray);
            } else {
                process = runtime.exec(command);
            }

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            bw.write("Test Java VM");
            bw.flush();
            bw.close();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

            String line;
            while((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    System.out.println(line);
                }
            }

            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GBK"));

            while((line = error.readLine()) != null) {
                if (!line.trim().equals("")) {
                    System.err.println(line);
                }
            }
        } catch (IOException var17) {
            var17.printStackTrace();
        } catch (RuntimeException var18) {
            var18.printStackTrace();
        } finally {
            process.destroy();
        }

        long eTime = (new Date()).getTime();
        System.out.println("执行时间为：" + (eTime - sTime) + "毫秒！");
        return "";
    }

    public static String jdkProcessBuilder() {
        return "";
    }

    public static void main(String[] args) {
        try {
            jdkRunTime("java -version");
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
