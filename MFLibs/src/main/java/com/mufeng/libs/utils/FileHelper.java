package com.mufeng.libs.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {

    /**
     * 获取视频 contentValue
     * @param paramFile
     * @param paramLong
     * @return
     */
    public static ContentValues getVideoContentValues(File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/mp4");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }

    /**
     * 拷贝文件
     * @param oldPath
     * @param out
     * @return
     */
    public static boolean copyFile(String oldPath, OutputStream out) {
        Log.d("TAG", "oldPath = " + oldPath);
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                // 读入原文件
                InputStream inStream = new FileInputStream(oldPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    out.write(buffer, 0, byteread);
                }
                inStream.close();
                out.close();
                return true;
            }
            else {
                Log.w("TAG", String.format("文件(%s)不存在。", oldPath));
            }
        }
        catch (Exception e) {
            Log.e("TAG", "复制单个文件操作出错");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 将视频保存到系统图库
     *
     * @param videoFile
     * @param context
     */
    public static boolean saveVideoToSystemAlbum(String videoFile, Context context) {

        try {
            ContentResolver localContentResolver = context.getContentResolver();
            ContentValues localContentValues = getVideoContentValues(new File(videoFile), System.currentTimeMillis());

            Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.Q) {
                // 拷贝到指定uri,如果没有这步操作，android11不会在相册显示
                try {
                    OutputStream out = context.getContentResolver().openOutputStream(localUri);
                    copyFile(videoFile, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri));
            //将该文件扫描到相册
            //MediaScannerConnection.scanFile(context, new String[] { videoFile }, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
