package com.example.mater_electronic.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static File getFileFromUri(Context context, Uri uri) {
        try {
            // First try to get the real path
            String realPath = getRealPathFromUri(context, uri);
            if (realPath != null) {
                return new File(realPath);
            }

            // If that fails, copy to cache directory
            return copyUriToFile(context, uri);

        } catch (Exception e) {
            Log.e("FileUtils", "Error getting file from URI", e);
            return null;
        }
    }

    private static String getRealPathFromUri(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            Log.e("FileUtils", "Error getting real path", e);
        }
        return null;
    }

    private static File copyUriToFile(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        if (inputStream == null) {
            throw new IOException("Cannot open input stream from URI");
        }

        // Create temporary file
        File tempFile = new File(context.getCacheDir(), "temp_image_" + System.currentTimeMillis() + ".jpg");

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            inputStream.close();
        }

        return tempFile;
    }
}