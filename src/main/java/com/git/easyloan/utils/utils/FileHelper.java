package com.git.easyloan.utils.utils;

import java.io.*;
import java.net.URL;
import java.util.*;

public class FileHelper {
    public static List ignoreList = new ArrayList();
    public static Set binaryExtentionsList;

    public FileHelper() {
    }

//    public static String getRelativePath(File baseDir, File file) {
//        if (baseDir.equals(file)) {
//            return "";
//        } else {
//            return baseDir.getParentFile() == null ? file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()) : file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
//        }
//    }

//    public static List<File> searchAllNotIgnoreFile(File dir) throws IOException {
//        ArrayList arrayList = new ArrayList();
//        searchAllNotIgnoreFile(dir, arrayList);
//        Collections.sort(arrayList, new Comparator<File>() {
//            public int compare(File o1, File o2) {
//                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
//            }
//        });
//        return arrayList;
//    }
//
//    public static InputStream getInputStream(String file) throws FileNotFoundException {
//        InputStream inputStream = null;
//        if (file.startsWith("classpath:")) {
//            inputStream = FileHelper.class.getClassLoader().getResourceAsStream(file.substring("classpath:".length()));
//        } else {
//            inputStream = new FileInputStream(file);
//        }
//
//        return (InputStream)inputStream;
//    }

//    public static void searchAllNotIgnoreFile(File dir, List<File> collector) throws IOException {
//        collector.add(dir);
//        if (!dir.isHidden() && dir.isDirectory() && !isIgnoreFile(dir)) {
//            File[] subFiles = dir.listFiles();
//
//            for(int i = 0; i < subFiles.length; ++i) {
//                searchAllNotIgnoreFile(subFiles[i], collector);
//            }
//        }
//
//    }

//    public static File mkdir(String dir, String file) {
//        if (dir == null) {
//            throw new IllegalArgumentException("dir must be not null");
//        } else {
//            File result = new File(dir, file);
//            parnetMkdir(result);
//            return result;
//        }
//    }

//    public static File parentMkdir(String file) {
//        if (file == null) {
//            throw new IllegalArgumentException("file must be not null");
//        } else {
//            File result = new File(file);
//            parnetMkdir(result);
//            return result;
//        }
//    }

//    public static void parnetMkdir(File outputFile) {
//        if (outputFile.getParentFile() != null) {
//            outputFile.getParentFile().mkdirs();
//        } else {
//            throw new RuntimeException("outputFile.getParentFile():" + outputFile.getParentFile());
//        }
//    }

    public static File getFileByClassLoader(String resourceName) throws IOException {
        Enumeration<URL> urls = FileHelper.class.getClassLoader().getResources(resourceName);
        if (urls.hasMoreElements()) {
            return new File((urls.nextElement()).getFile());
        } else {
            throw new FileNotFoundException(resourceName);
        }
    }

//    private static boolean isIgnoreFile(File file) {
//        for(int i = 0; i < ignoreList.size(); ++i) {
//            if (file.getName().equals(ignoreList.get(i))) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public static void loadBinaryExtentionsList(String resourceName, boolean ignoreException) {
        try {
            InputStream input = FileHelper.class.getClassLoader().getResourceAsStream(resourceName);
            binaryExtentionsList.addAll(IOHelper.readLines(new InputStreamReader(input)));
            input.close();
        } catch (Exception var3) {
            if (!ignoreException) {
                throw new RuntimeException(var3);
            }
        }

    }

//    public static boolean isBinaryFile(File file) {
//        return file.isDirectory() ? false : isBinaryFile(file.getName());
//    }

//    public static boolean isBinaryFile(String filename) {
//        return StringHelper.isBlank(getExtension(filename)) ? false : binaryExtentionsList.contains(getExtension(filename).toLowerCase());
//    }

//    public static String getExtension(String filename) {
//        if (filename == null) {
//            return null;
//        } else {
//            int index = filename.indexOf(".");
//            return index == -1 ? "" : filename.substring(index + 1);
//        }
//    }

//    public static void deleteDirectory(File directory) throws IOException {
//        if (directory.exists()) {
//            cleanDirectory(directory);
//            if (!directory.delete()) {
//                String message = "Unable to delete directory " + directory + ".";
//                throw new IOException(message);
//            }
//        }
//    }

//    public static boolean deleteQuietly(File file) {
//        if (file == null) {
//            return false;
//        } else {
//            try {
//                if (file.isDirectory()) {
//                    cleanDirectory(file);
//                }
//            } catch (Exception var3) {
//            }
//
//            try {
//                return file.delete();
//            } catch (Exception var2) {
//                return false;
//            }
//        }
//    }

//    public static void cleanDirectory(File directory) throws IOException {
//        String message;
//        if (!directory.exists()) {
//            message = directory + " does not exist";
//            throw new IllegalArgumentException(message);
//        } else if (!directory.isDirectory()) {
//            message = directory + " is not a directory";
//            throw new IllegalArgumentException(message);
//        } else {
//            File[] files = directory.listFiles();
//            if (files == null) {
//                throw new IOException("Failed to list contents of " + directory);
//            } else {
//                IOException exception = null;
//
//                for(int i = 0; i < files.length; ++i) {
//                    File file = files[i];
//
//                    try {
//                        forceDelete(file);
//                    } catch (IOException var6) {
//                        exception = var6;
//                    }
//                }
//
//                if (null != exception) {
//                    throw exception;
//                }
//            }
//        }
//    }
//
//    public static void forceDelete(File file) throws IOException {
//        if (file.isDirectory()) {
//            deleteDirectory(file);
//        } else {
//            boolean filePresent = file.exists();
//            if (!file.delete()) {
//                if (!filePresent) {
//                    throw new FileNotFoundException("File does not exist: " + file);
//                }
//
//                String message = "Unable to delete file: " + file;
//                throw new IOException(message);
//            }
//        }
//
//    }

    static {
        ignoreList.add(".svn");
        ignoreList.add("CVS");
        ignoreList.add(".cvsignore");
        ignoreList.add(".copyarea.db");
        ignoreList.add("SCCS");
        ignoreList.add("vssver.scc");
        ignoreList.add(".DS_Store");
        ignoreList.add(".git");
        ignoreList.add(".gitignore");
        binaryExtentionsList = new HashSet();
//        loadBinaryExtentionsList("binary_filelist.txt", true);
        loadBinaryExtentionsList("com/git/utils/binary_filelist.txt", true);
    }
}
