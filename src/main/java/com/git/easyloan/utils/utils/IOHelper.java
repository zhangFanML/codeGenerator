package com.git.easyloan.utils.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOHelper {
    public static Writer NULL_WRITER = new IOHelper.NullWriter();

    public IOHelper() {
    }

    public static void copy(Reader reader, Writer writer) throws IOException {
        char[] buf = new char[8192];
        boolean var3 = false;

        int n;
        while((n = reader.read(buf)) != -1) {
            writer.write(buf, 0, n);
        }

    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        boolean var3 = false;

        int n;
        while((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
        }

    }

    public static List readLines(Reader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);
        List list = new ArrayList();

        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            list.add(line);
        }

        return list;
    }

    public static String readFile(File file) throws IOException {
        Reader in = new FileReader(file);
        StringWriter out = new StringWriter();
        copy((Reader)in, (Writer)out);
        in.close();
        return out.toString();
    }

    public static String readFile(File file, String encoding) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);

        String var3;
        try {
            var3 = toString(encoding, inputStream);
        } finally {
            inputStream.close();
        }

        return var3;
    }

    public static String toString(InputStream inputStream) throws UnsupportedEncodingException, IOException {
        Reader reader = new InputStreamReader(inputStream);
        StringWriter writer = new StringWriter();
        copy((Reader)reader, (Writer)writer);
        return writer.toString();
    }

    public static String toString(String encoding, InputStream inputStream) throws UnsupportedEncodingException, IOException {
        Reader reader = new InputStreamReader(inputStream, encoding);
        StringWriter writer = new StringWriter();
        copy((Reader)reader, (Writer)writer);
        return writer.toString();
    }

    public static void saveFile(File file, String content) {
        saveFile(file, content, (String)null, false);
    }

    public static void saveFile(File file, String content, boolean append) {
        saveFile(file, content, (String)null, append);
    }

    public static void saveFile(File file, String content, String encoding) {
        saveFile(file, content, encoding, false);
    }

    public static void saveFile(File file, String content, String encoding, boolean append) {
        try {
            FileOutputStream output = new FileOutputStream(file, append);
            Writer writer = StringHelper.isBlank(encoding) ? new OutputStreamWriter(output) : new OutputStreamWriter(output, encoding);
            writer.write(content);
            writer.close();
        } catch (IOException var6) {
            throw new RuntimeException(var6);
        }
    }

    public static void copyAndClose(InputStream in, OutputStream out) throws IOException {
        try {
            copy(in, out);
        } finally {
            close(in, out);
        }

    }

    public static void close(InputStream in, OutputStream out) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception var4) {
        }

        try {
            if (out != null) {
                out.close();
            }
        } catch (Exception var3) {
        }

    }

    private static class NullWriter extends Writer {
        private NullWriter() {
        }

        public void close() throws IOException {
        }

        public void flush() throws IOException {
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
        }
    }
}
