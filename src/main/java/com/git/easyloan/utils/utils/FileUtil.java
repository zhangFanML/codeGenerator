package com.git.easyloan.utils.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String message;

    public FileUtil() {
    }

    public static Double getFilesize(String filepath) {
        File backupath = new File(filepath);
        return Double.valueOf((double)backupath.length()) / 1000.0D;
    }

    public static Boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        return !dir.getParentFile().exists() ? dir.getParentFile().mkdirs() : false;
    }

    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > 2147483647L) {
            System.out.println("file too big...");
            return null;
        } else {
            FileInputStream fi = new FileInputStream(file);
            byte[] buffer = new byte[(int)fileSize];
            int offset = 0;

            int numRead;
            for(boolean var7 = false; offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0; offset += numRead) {
            }

            if (offset != buffer.length) {
                throw new IOException("Could not completely read file " + file.getName());
            } else {
                fi.close();
                return buffer;
            }
        }
    }

    public static byte[] toByteArray(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(f));
                int buf_size = 1024;
                byte[] buffer = new byte[buf_size];
                boolean var6 = false;

                int len;
                while(-1 != (len = in.read(buffer, 0, buf_size))) {
                    bos.write(buffer, 0, len);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } catch (IOException var16) {
                var16.printStackTrace();
                throw var16;
            } finally {
                try {
                    in.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }

                bos.close();
            }
        }
    }

    public static byte[] toByteArray2(String filePath) throws IOException {
        File f = new File(filePath);
        System.out.println("filePath---->" + filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            FileChannel channel = null;
            FileInputStream fs = null;

            try {
                fs = new FileInputStream(f);
                channel = fs.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());

                while(channel.read(byteBuffer) > 0) {
                }

                byte[] var5 = byteBuffer.array();
                return var5;
            } catch (IOException var17) {
                var17.printStackTrace();
                throw var17;
            } finally {
                try {
                    channel.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }

                try {
                    fs.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }

            }
        }
    }

    public static byte[] toByteArray3(String filePath) throws IOException {
        FileChannel fc = null;
        RandomAccessFile rf = null;

        byte[] var5;
        try {
            rf = new RandomAccessFile(filePath, "r");
            fc = rf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0L, fc.size()).load();
            byte[] result = new byte[(int)fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }

            var5 = result;
        } catch (IOException var14) {
            var14.printStackTrace();
            throw var14;
        } finally {
            try {
                rf.close();
                fc.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return var5;
    }

    public static String readTxt(String filePathAndName, String encoding) throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        logger.debug("开始读取文件:" + filePathAndName);

        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }

            BufferedReader br = new BufferedReader(isr);

            try {
                String data = "";
                boolean var8 = false;

                while((data = br.readLine()) != null) {
                    str.append(data + "\n");
                }
            } catch (Exception var9) {
                str.append(var9.toString());
            }

            st = str.toString();
        } catch (IOException var10) {
            st = "";
        }

        return st;
    }

    public static String createFolder(String folderPath) throws IOException {
        String txt = folderPath;

        try {
            File myFilePath = new File(txt);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }

            return folderPath;
        } catch (Exception var3) {
            message = "创建目录操作出错";
            throw new IOException(message);
        }
    }

    public static String createFolders(String folderPath, String paths) throws IOException {
        try {
            String txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, "|");

            for(int var5 = 0; st.hasMoreTokens(); ++var5) {
                String txt = st.nextToken().trim();
                if (txts.lastIndexOf("/") != -1) {
                    txts = createFolder(txts + txt);
                } else {
                    txts = createFolder(txts + txt + "/");
                }
            }

            return txts;
        } catch (Exception var6) {
            message = "创建目录操作出错！";
            throw new IOException(message);
        }
    }

    public static void createFile(String filePathAndName, String fileContent) throws IOException {
        try {
            String filePath = filePathAndName.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }

            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            myFile.println(fileContent);
            logger.debug("生成文件" + filePathAndName + "成功！");
            myFile.close();
            resultFile.close();
        } catch (Exception var7) {
            message = "创建文件操作出错";
            throw new IOException(message);
        }
    }

    public static void createFile(String filePathAndName, String fileContent, String encoding) throws Exception {
        try {
            String filePath = filePathAndName.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }

            PrintWriter myFile = new PrintWriter(myFilePath, encoding);
            myFile.println(fileContent);
            myFile.close();
            logger.debug("生成文件：" + myFilePath);
        } catch (IOException var7) {
            throw new IOException("创建文件失败,请检查文件路径是否存在！" + var7.getMessage());
        } catch (Exception var8) {
            message = "创建文件操作出错";
            throw new Exception(message);
        }
    }

    public static boolean delFile(String filePathAndName) throws IOException {
        boolean bea = false;

        try {
            File myDelFile = new File(filePathAndName);
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
                message = filePathAndName + "删除文件操作出错";
            }

            return bea;
        } catch (Exception var4) {
            message = var4.toString();
            throw new IOException(message);
        }
    }

    public static void delFolder(String folderPath) throws IOException {
        try {
            delAllFile(folderPath);
            String filePath = folderPath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();
        } catch (Exception var3) {
            message = "删除文件夹操作出错";
            throw new IOException(message);
        }
    }

    public static boolean delAllFile(String path) throws IOException {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        } else if (!file.isDirectory()) {
            return bea;
        } else {
            String[] tempList = file.list();
            File temp = null;

            for(int i = 0; i < tempList.length; ++i) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }

                if (temp.isFile()) {
                    temp.delete();
                }

                if (temp.isDirectory()) {
                    delAllFile(path + "/" + tempList[i]);
                    delFolder(path + "/" + tempList[i]);
                    bea = true;
                }
            }

            return bea;
        }
    }

    public static void copyFile(String oldPathFile, String newPathFile) throws IOException {
        try {
            int bytesum = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPathFile);
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];

                int byteread;
                while((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    logger.debug(String.valueOf(bytesum));
                    fs.write(buffer, 0, byteread);
                }

                inStream.close();
            }

        } catch (Exception var8) {
            message = "复制单个文件操作出错";
            throw new IOException(message);
        }
    }

    public static void copyFolder(String oldPath, String newPath) throws IOException {
        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;

            for(int i = 0; i < file.length; ++i) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                    byte[] b = new byte[5120];

                    int len;
                    while((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }

        } catch (Exception var10) {
            message = "复制整个文件夹内容操作出错";
            throw new IOException(message);
        }
    }

    public static void moveFile(String oldPath, String newPath) throws IOException {
        copyFile(oldPath, newPath);
        delFile(oldPath);
        logger.debug("文件移动成功！");
    }

    public static void moveFolder(String oldPath, String newPath) throws IOException {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public static void moveFile_new(String oldPath, String newPath) {
        File source = new File(oldPath);
        File dest = new File(newPath);
        source.renameTo(dest);
        logger.debug("文件移动成功！");
    }

    public static void readTextFromEnd(String pathAndFileName, String encoding) {
        RandomAccessFile rf = null;

        try {
            rf = new RandomAccessFile(pathAndFileName, "r");
            long len = rf.length();
            long start = rf.getFilePointer();
            long nextend = start + len - 1L;
            rf.seek(nextend);
            boolean var10 = true;

            while(true) {
                while(nextend > start) {
                    int c = rf.read();
                    if (c == 10 || c == 13) {
                        String line = rf.readLine();
                        if (line == null) {
                            --nextend;
                            rf.seek(nextend);
                            continue;
                        }

                        System.out.println(new String(line.getBytes("ISO-8859-1"), encoding));
                        --nextend;
                    }

                    --nextend;
                    rf.seek(nextend);
                    if (nextend == 0L) {
                        System.out.println(new String(rf.readLine().getBytes("ISO-8859-1"), encoding));
                    }
                }

                return;
            }
        } catch (FileNotFoundException var21) {
            var21.printStackTrace();
        } catch (IOException var22) {
            var22.printStackTrace();
        } finally {
            try {
                rf.close();
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

    }

    public static List<String> dataReader(String nameFile, int start, int finish) throws Exception {
        List<String> retList = new ArrayList();
        if (start > finish) {
            throw new Exception("Error start or finish!");
        } else {
            InputStream inputStream = null;
            LineNumberReader reader = null;

            try {
                inputStream = new FileInputStream(new File(nameFile));
                reader = new LineNumberReader(new InputStreamReader(inputStream));
                logger.debug("开始计算文件总条数：" + DateHelper.formatDateTimeYYYYMMDDHHMMSS());
                int lines = getTotalLines(new File(nameFile));
                logger.debug("结束计算文件总条数：" + DateHelper.formatDateTimeYYYYMMDDHHMMSS());
                if (start >= 0 && finish >= 0 && finish <= lines && start <= lines) {
                    String line = reader.readLine();
                    lines = 0;
                    logger.debug("开始读取文件：" + DateHelper.formatDateTimeYYYYMMDDHHMMSS());

                    for(; line != null; line = reader.readLine()) {
                        ++lines;
                        if (lines >= start && lines <= finish) {
                            retList.add(line);
                        }
                    }

                    inputStream.close();
                    reader.close();
                    logger.debug("结束读取文件：" + DateHelper.formatDateTimeYYYYMMDDHHMMSS());
                    return retList;
                } else {
                    throw new Exception("Line not found!");
                }
            } catch (FileNotFoundException var8) {
                throw new Exception(var8);
            } catch (IOException var9) {
                throw new Exception(var9);
            }
        }
    }

    public static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String line = reader.readLine();

        int lines;
        for(lines = 0; line != null; line = reader.readLine()) {
            ++lines;
        }

        reader.close();
        in.close();
        return lines;
    }

    public PrintWriter fopenW(String fileName, String encoding, boolean append) throws Exception {
        PrintWriter pw = null;

        try {
            fileName = fileName.toString();
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                file.setWritable(true);
            }

            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), encoding));
            return pw;
        } catch (Exception var6) {
            var6.printStackTrace();
            if (pw != null) {
                pw.close();
                pw = null;
            }

            throw new Exception("写文件操作出错" + var6.getMessage());
        }
    }

    public void fprintlnf(PrintWriter pw, String content) {
        pw.print(content + "\n");
    }

    public void fprintf(PrintWriter pw, String content) {
        pw.print(content);
    }

    public void closeFile(PrintWriter pw, BufferedReader br) {
        if (pw != null) {
            pw.flush();
            pw.close();
            pw = null;
        }

        if (br != null) {
            try {
                br.close();
            } catch (IOException var4) {
                var4.printStackTrace();
            }

            br = null;
        }

    }

    public static BufferedReader fopenR(String fileName, String encoding) throws Exception {
        BufferedReader br = null;

        try {
            File file = new File(fileName);
            file.setReadOnly();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            return br;
        } catch (Exception var6) {
            var6.printStackTrace();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }

                br = null;
            }

            throw new Exception("读文件操作出错" + var6.getMessage());
        }
    }
}
