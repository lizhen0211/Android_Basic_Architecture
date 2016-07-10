package com.architecture.util.file;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lz on 2016/7/9.
 */
public class StorageUtil {

    /**
     * Save a File on Internal Storage
     **/

    /**
     * 在内部存储区创建文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static boolean createFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.exists();
    }

    /**
     * 在内置存储区写文件
     *
     * @param context
     */
    public static void writeToFile(Context context, String fileName, String text) {
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除内置存储区域文件
     *
     * @param context
     * @param fileName
     */
    public static boolean deleteFile(Context context, String fileName) {
        try {
            return context.deleteFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 在应用内部存储缓存文件夹创建文件
     *
     * @param context
     * @param url
     * @return
     */
    public static File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    /**
     * Save a File on External Storage
     **/

    /**
     * 检查扩展存储区是否可写
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 检查扩展存储区是否可读
     *
     * @return
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
