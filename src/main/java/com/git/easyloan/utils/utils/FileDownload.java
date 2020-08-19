package com.git.easyloan.utils.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class FileDownload {

    public FileDownload() {
    }

    public static void fileDownload(HttpServletResponse response, String filePath, String fileName) throws Exception {
        byte[] data = FileUtil.toByteArray2(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
    }
}
