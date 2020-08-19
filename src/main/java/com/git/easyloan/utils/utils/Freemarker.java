package com.git.easyloan.utils.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Map;

public class Freemarker {
    public Freemarker() {
    }

    public static void print(String ftlName, Map<String, Object> root, String ftlPath) throws Exception {
        try {
            Template temp = getTemplate(ftlName, ftlPath);
            temp.process(root, new PrintWriter(System.out));
        } catch (TemplateException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    /**
     *
     * @param ftlName 模板名称
     * @param root 数据
     * @param outFile 生成的文件名称
     * @param filePath 生成的文件名路径
     * @param ftlPath 模板路径
     * @throws Exception
     */
    public static void printFile(String ftlName, Map<String, Object> root, String outFile, String filePath, String ftlPath) throws Exception {
        try {
            File file = new File(filePath + outFile);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);
            out.flush();
            out.close();
        } catch (TemplateException var8) {
            var8.printStackTrace();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    public static Template getTemplate(String ftlName, String ftlPath) throws Exception {
        try {
            FreeMarkerConfigurer cfg = (FreeMarkerConfigurer)ApplicationContextHolder.getManagerBean("myFreeMarkerConfigurer");
            Configuration configuration = cfg.getConfiguration();
            Template temp = configuration.getTemplate(ftlName);
            return temp;
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
