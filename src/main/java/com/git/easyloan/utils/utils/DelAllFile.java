package com.git.easyloan.utils.utils;

import java.io.File;

public class DelAllFile {
    public DelAllFile() {
    }

    public static void main(String[] args) {
        delFolder("e:/e/a");
        System.out.println("deleted");
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        } else if (!file.isDirectory()) {
            return flag;
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
                    flag = true;
                }
            }

            return flag;
        }
    }
}
