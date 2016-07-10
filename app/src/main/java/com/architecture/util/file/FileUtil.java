package com.architecture.util.file;

import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lz on 2016/7/9.
 */
public class FileUtil {

    public static final int ONE_KB = 1024;
    public static final int ONE_MB = 1048576;
    public static final int ONE_GB = 1073741824;

    /**
     * 容量换算
     *
     * @param size
     * @return
     */
    public static String byteCountToDisplaySize(int size) {
        String displaySize;
        if (size / ONE_GB > 0) {
            displaySize = size / ONE_GB + " GB";
        } else if (size / ONE_MB > 0) {
            displaySize = size / ONE_MB + " MB";
        } else if (size / ONE_KB > 0) {
            displaySize = size / ONE_KB + " KB";
        } else {
            displaySize = size + " bytes";
        }

        return displaySize;
    }

    /**
     * 获取子文件夹名称
     *
     * @param filename
     * @return
     */
    public static String dirname(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return i >= 0 ? filename.substring(0, i) : "";
    }

    /**
     * 获取子文件名称
     *
     * @param filename
     * @return
     */
    public static String filename(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return i >= 0 ? filename.substring(i + 1) : filename;
    }

    /**
     * 文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 读文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String fileRead(String fileName) throws IOException {
        StringBuffer buf = new StringBuffer();
        FileInputStream in = new FileInputStream(fileName);
        byte[] b = new byte[512];

        int count;
        while ((count = in.read(b)) > 0) {
            buf.append(new String(b, 0, count));
        }

        in.close();
        return buf.toString();
    }

    /**
     * 写文件
     *
     * @param fileName
     * @param data
     * @throws Exception
     */
    public static void fileWrite(String fileName, String data) throws Exception {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(data.getBytes());
        out.close();
    }

    /**
     * 复制文件
     *
     * @param inFileName
     * @param outFileName
     * @throws Exception
     */
    public static void fileCopy(String inFileName, String outFileName) throws Exception {
        String content = fileRead(inFileName);
        fileWrite(outFileName, content);
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void fileDelete(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    /**
     * 创建文件夹
     *
     * @param dir
     */
    public static void mkdir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取文件所在路径剩余容量  单位M
     */
    public static int getFileAvailableSize(File file) {
        StatFs statfs = new StatFs(file.getPath());
        int blocksize = statfs.getBlockSize();
        int blockaval = statfs.getAvailableBlocks();
        return (int) (((double) blockaval * (double) blocksize) / ONE_MB);
    }
}
