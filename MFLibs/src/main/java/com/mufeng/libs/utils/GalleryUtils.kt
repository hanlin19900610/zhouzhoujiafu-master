package com.mufeng.libs.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SaveResultModel(
    var isSuccess: Boolean,
    var filePath: String? = null,
    var errorMessage: String? = null
){
    fun toHashMap(): HashMap<String, Any?> {
        val hashMap = HashMap<String, Any?>()
        hashMap["isSuccess"] = isSuccess
        hashMap["filePath"] = filePath
        hashMap["errorMessage"] = errorMessage
        return hashMap
    }
}

object GalleryUtils {

    private fun generateFile(context: Context, extension: String = "", name: String? = null): File {
        val brand = Build.BRAND
        var storePath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getExternalFilesDir(null)?.absolutePath + File.separator
        } else {

            if (brand == "xiaomi") { // 小米手机brand.equals("xiaomi")
                Environment.getExternalStorageDirectory().path + "/DCIM/Camera/"
            } else if (brand == "HUAWEI" || brand == "HONOR") {
                Environment.getExternalStorageDirectory().path + "/DCIM/Camera/"
            } else { // Meizu 、Oppo
                Environment.getExternalStorageDirectory().path + "/DCIM/"
            }
        }

        if (extension == "mp4") {
            storePath += Environment.DIRECTORY_MOVIES
        } else {
            storePath += Environment.DIRECTORY_PICTURES
        }

        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        var fileName = name ?: System.currentTimeMillis().toString()
        if (extension.isNotEmpty()) {
            fileName += (".$extension")
        }
        return File(appDir, fileName)
    }

    fun saveImageToGallery(
        context: Context,
        bmp: Bitmap,
        quality: Int,
        name: String?,
        result: (result: Map<String, Any?>) -> Unit
    ) {
        val file = generateFile(context, "jpg", name = name)
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val fos = FileOutputStream(file)
                println("ImageGallerySaverPlugin $quality")
                bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos)
                fos.flush()
                fos.close()
                GlobalScope.launch(Dispatchers.Main) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        saveImageToGalleryR(context, "jpg", file, result)
                    } else {
//                        val uri = Uri.fromFile(file)
//                        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                        val uri = MediaStore.Images.Media.insertImage(
                            context.contentResolver,
                            file.absolutePath,
                            file.name,
                            null
                        );
                        bmp.recycle()
                        result(SaveResultModel(true, uri.toString(), null).toHashMap())
                    }
                }
            }
        } catch (e: Exception) {
            result(SaveResultModel(false, null, e.toString()).toHashMap())
        }
    }

    fun saveImageToGalleryWithUrl(
        context: Context,
        path: String,
        result: (result: Map<String, Any?>) -> Unit
    ) {
        val file = generateFile(context, File(path).extension)
        try {
            GlobalScope.launch(Dispatchers.IO) {
//                File(path).copyTo(file)
                com.blankj.utilcode.util.FileUtils.copy(File(path), file)
                GlobalScope.launch(Dispatchers.Main) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        saveImageToGalleryR(context, File(path).extension, file, result)
                    } else {
//                        val uri = Uri.fromFile(file)
//                        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                        val uri = MediaStore.Images.Media.insertImage(
                            context.contentResolver,
                            file.absolutePath,
                            file.name,
                            null
                        );
                        result(
                            SaveResultModel(
                                uri.toString().isNotEmpty(),
                                uri.toString(),
                                null
                            ).toHashMap()
                        )
                    }
                }
            }
        } catch (e: Exception) {
            result(SaveResultModel(false, null, e.toString()).toHashMap())
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun saveImageToGalleryR(
        context: Context,
        extension: String,
        file: File,
        result: (result: Map<String, Any?>) -> Unit
    ) {
        val mImageTime = System.currentTimeMillis()
        val imageDate: String = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date(mImageTime))
        val SCREENSHOT_FILE_NAME_TEMPLATE = "gallery_%s.$extension"
        val mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate)
        val mimeType = when (extension) {
            "png" -> "image/png"
            "bmp", "dib" -> "image/bmp"
            "gif" -> "image/gif"
            else -> "image/jpeg"
        }
        val values = ContentValues()
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            Environment.DIRECTORY_PICTURES + File.separator + "gallery"
        )
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000)
        values.put(
            MediaStore.MediaColumns.DATE_EXPIRES,
            (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000
        )
        values.put(MediaStore.MediaColumns.IS_PENDING, 1)
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try {
            GlobalScope.launch(Dispatchers.IO) {
                val out = resolver.openOutputStream(uri!!)
                val fileInputStream = FileInputStream(file)
                FileUtils.copy(fileInputStream, out!!)
                fileInputStream.close()
                out.close()
                GlobalScope.launch(Dispatchers.Main) {
                    values.clear()
                    values.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    values.putNull(MediaStore.MediaColumns.DATE_EXPIRES)
                    resolver.update(uri, values, null, null)
                    result(
                        SaveResultModel(
                            uri.toString().isNotEmpty(),
                            UriUtils.getFileAbsolutePath(context, uri),
                            null
                        ).toHashMap()
                    )
                }
            }

        } catch (e: IOException) {
            resolver.delete(uri!!, null);
            result(SaveResultModel(false, null, e.toString()).toHashMap())
        }
    }

    fun saveVideoToGallery(
        context: Context,
        filePath: String,
        result: (result: Map<String, Any?>) -> Unit
    ) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val originalFile = File(filePath)
                val file = generateFile(context, originalFile.extension)
                com.blankj.utilcode.util.FileUtils.copy(originalFile, file)
//                originalFile.copyTo(file)
                GlobalScope.launch(Dispatchers.Main) {
                    val bool = FileHelper.saveVideoToSystemAlbum(file.absolutePath, context)
                    result(
                        SaveResultModel(
                            bool,
                            "",
                            null
                        ).toHashMap())
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//                        saveVideoToGalleryR(context, file, result)
//                    }else {
//                        val uri = Uri.fromFile(file)
//                        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
//                        result(
//                            SaveResultModel(
//                                uri.toString().isNotEmpty(),
//                                uri.toString(),
//                                null
//                            ).toHashMap()
//                        )
//                    }
                }
            }
        } catch (e: Exception) {
            result(SaveResultModel(false, null, e.toString()).toHashMap())
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun saveVideoToGalleryR(
        context: Context,
        file: File,
        result: (result: Map<String, Any?>) -> Unit
    ) {
        val mImageTime = System.currentTimeMillis()
        val imageDate: String = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date(mImageTime))
        val SCREENSHOT_FILE_NAME_TEMPLATE = "gallery_%s.mp4"
        val mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate)


        val values = ContentValues()
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            Environment.DIRECTORY_PICTURES + File.separator + "gallery"
        )
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000)
        values.put(
            MediaStore.MediaColumns.DATE_EXPIRES,
            (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000
        )
        values.put(MediaStore.MediaColumns.IS_PENDING, 1)
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)

        try {
            GlobalScope.launch(Dispatchers.IO) {
                val out = resolver.openOutputStream(uri!!)
                val fileInputStream = FileInputStream(file)
                FileUtils.copy(fileInputStream, out!!)
                fileInputStream.close()
                out.close()
                val filePath = UriUtils.getFileAbsolutePath(context, uri)
                GlobalScope.launch(Dispatchers.Main) {
                    values.clear();
                    values.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    values.putNull(MediaStore.MediaColumns.DATE_EXPIRES)
                    resolver.update(uri, values, null, null)

                    result(SaveResultModel(uri.toString().isNotEmpty(), filePath, null).toHashMap())
                }
            }

        } catch (e: IOException) {
            resolver.delete(uri!!, null)
            result(SaveResultModel(false, null, e.toString()).toHashMap())
        }
    }

}