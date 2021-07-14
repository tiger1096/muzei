package com.alexqzhang.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文件处理工具类
 *
 * @author kevinxing
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * assets目录前缀
     */
    public static final String RES_PREFIX_ASSETS = "assets://";
    public static final String RES_PREFIX_STORAGE = "/";
    public static final String RES_PREFIX_HTTP = "http://";
    public static final String RES_PREFIX_HTTPS = "https://";

    // TODO(@Dianxin) It's error-prone that we have dot version POSTFIX and non-dot version POSTFIX.
    public static final String RES_POSTFIX_GLB = ".glb";
    public static final String RES_POSTFIX_GLTF = ".gltf";
    public static final String RES_POSTFIX_ENCRYPTED_GLB = ".dat";
    public static final String RES_POSTFIX_ENCRYPTED_GLTF = ".datf";

    public static final String PIC_POSTFIX_JPEG = ".jpg";
    public static final String PIC_POSTFIX_PNG = ".png";
    public static final String PIC_POSTFIX_WEBP = ".webp";
    public static final String PIC_POSTFIX_MP4 =  ".mp4";

    public final static String MD5_SALT = "aekit";

    public static String checkPhoto(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        if (new File(path).exists()) {
            return path;
        }
        int slashIndex = path.lastIndexOf("/");
        String lastStr = path.substring(slashIndex);
        int dotIndex = lastStr.lastIndexOf(".");
        if (dotIndex == -1) {
            String jpeg = path + PIC_POSTFIX_JPEG;
            if (new File(jpeg).exists()) {
                return jpeg;
            }
            String png = path + PIC_POSTFIX_PNG;
            if (new File(png).exists()) {
                return png;
            }
        }
        return path;
    }

    public static String checkAssetsPhoto(Context context, String path) {
        if (TextUtils.isEmpty(path)){
            return null;
        }
        AssetManager assets = context.getAssets();

        InputStream stream = null;
        try {
            stream = assets.open(path);
            return path;
        } catch (IOException e) {
            //空异常处理
        } finally {
            IOUtils.closeQuietly(stream);
        }

        if (path.lastIndexOf(".") != -1) {
            String webp = path.substring(0, path.lastIndexOf('.') + 1) + "webp";
            try {
                stream = assets.open(webp);
                return webp;
            } catch (IOException e) {
                //空异常处理
            } finally {
                IOUtils.closeQuietly(stream);
            }
            return null;
        }

        String jpg = path + PIC_POSTFIX_JPEG;
        try {
            stream = assets.open(jpg);
            return jpg;
        } catch (IOException e) {
            //空异常处理
        } finally {
            IOUtils.closeQuietly(stream);
        }

        String webp = path + PIC_POSTFIX_WEBP;
        try {
            stream = assets.open(webp);
            return webp;
        } catch (IOException e) {
            //空异常处理
        } finally {
            IOUtils.closeQuietly(stream);
        }

        String png = path + PIC_POSTFIX_PNG;
        try {
            stream = assets.open(png);
            return png;
        } catch (IOException e) {
            //空异常处理
        } finally {
            IOUtils.closeQuietly(stream);
        }
        return null;
    }

    public static boolean isDirectoryWritable(String directory) {
        File file = new File(directory);
        if (file.exists() && !file.isDirectory()) { // file is file, not folder
            return false;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            try {
                return file.canWrite();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获得去除 {@link #RES_PREFIX_ASSETS} 前缀的资源路径
     *
     * @param path 资源路径
     * @return 去除{@link #RES_PREFIX_ASSETS} 前缀的资源路径
     */
    public static String getRealPath(String path) {
        return TextUtils.isEmpty(path) ? path : path.startsWith(RES_PREFIX_ASSETS) ? path.substring(RES_PREFIX_ASSETS.length()) : path;
    }

    public static String getPostfix(String path) {
        int indexOfDot = path.lastIndexOf(".");
        String postfix = path.substring(indexOfDot);
        return postfix;
    }
    public static String getRealDirPath(String path) {
        return getRealPath(path.substring(0, path.lastIndexOf(File.separator)));
    }

    public static String getFileName(String path) {
        Log.v(TAG, "[getFileName] path = " + path);
        String title = null;
        if (!TextUtils.isEmpty(path)) {
            int indexOfDot = path.lastIndexOf(".");
            int indexOfPath = path.lastIndexOf("/");
            if (indexOfPath >= 0 && indexOfDot > indexOfPath) {
                title = path.substring(indexOfPath + 1, indexOfDot);
            }
        }
        Log.v(TAG, "[getFileName] return title = " + title);
        return title;
    }

    // 4QQ + BEGIN
    public static String getFileNameWithSuffixByPath(String path) {
        Log.v(TAG, "[getFileNameWithSuffixByPath] path = " + path);
        String title = null;
        if (!TextUtils.isEmpty(path)) {
            int indexOfPath = path.lastIndexOf("/");
            title = path.substring(indexOfPath + 1);
        }
        Log.v(TAG, "[getFileNameWithSuffixByPath] return title = " + title);
        return title;
    }
    // 4QQ + END

    public static String getFileNameFromUrl(String url) {
        if (url == null) {
            return null;
        }
//        LogUtils.v(TAG, "getFileNameFromUrl, url = %s", url);
        int index = url.lastIndexOf("/");
//        LogUtils.v(TAG, "index of / is %d", index);
        if (index == -1) {
            return null;
        }
        // e.g. http://adb.abc/asdf/
        if (index == url.length() - 1) {
            return null;
        }
        String fileName = url.substring(index + 1);
//        LogUtils.v(TAG, "fileName is %s", fileName);
        return fileName;
    }

    public static String getFileSuffixFromUrl(String url) {
        if (url == null) {
            return null;
        }
//        LogUtils.v(TAG, "getFileNameFromUrl, url = %s", url);
        int index = url.lastIndexOf(".");
//        LogUtils.v(TAG, "index of / is %d", index);
        if (index == -1) {
            return null;
        }
        // e.g. http://adb.abc/asdf/
        if (index == url.length() - 1) {
            return null;
        }
        String fileSuffix = url.substring(index + 1);
//        LogUtils.v(TAG, "fileName is %s", fileName);
        return fileSuffix;
    }

    // 4QQ + BEGIN
    public static void save(File file, String text) {
        OutputStream stream = null;
        BufferedWriter writer = null;
        try {
            stream = new FileOutputStream(file);
            writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
            writer.write(text);
        } catch (Exception e) {

            Log.e(TAG, e.getMessage());
        } finally {
            IOUtils.closeQuietly(stream);
            IOUtils.closeQuietly(writer);
        }
    }
    // 4QQ + END

    public static void saveFile(String fileName, String text) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * 读取文件，返回文件字符串
     *
     * @param context  读取assets文件所需的上下文环境
     * @param dirPath  需要读取的文件夹路径
     * @param filename 需要读取的文件名
     * @return 文件中所有内容拼成的字符串
     */
    public static String load(Context context, String dirPath, String filename) {
        if (TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(filename)) {
            return "";
        }
        if (dirPath.startsWith(FileUtils.RES_PREFIX_ASSETS)) {
            return loadAssetsString(context, FileUtils.getRealPath(dirPath) + File.separator + filename);
        } else {
            return loadSdCardFileString(dirPath + File.separator + filename);
        }
    }

    /**
     * 读取文件，返回文件字符串
     *
     * @param context  读取assets文件所需的上下文环境
     * @param filePath 需要读取的文件地址
     * @return 文件中所有内容拼成的字符串
     */
    public static String load(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        if (filePath.startsWith(FileUtils.RES_PREFIX_ASSETS)) {
            return loadAssetsString(context, FileUtils.getRealPath(filePath));
        } else {
            return loadSdCardFileString(filePath);
        }
    }

    public static String loadSdCardFileString(String filePath) {
        StringBuilder buf = new StringBuilder();
        InputStream is = null;
        BufferedReader in = null;
        try {
            is = new FileInputStream(filePath);
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                buf.append(line);
                buf.append("\n");
            }
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(is);
        }
        return buf.toString();
    }

    public static String loadAssetsString(Context context, String path) {
        StringBuilder buf = new StringBuilder();
        InputStream is = null;
        BufferedReader in = null;
        try {
            is = context.getAssets().open(path);
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                buf.append(line);
                buf.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(is);
        }
        return buf.toString();
    }

    // 4QQ + BEGIN
    public static byte[] loadByteArray(Context context, String path) {
        InputStream is = null;
        byte[] result = new byte[0];
        try {
            if (path.startsWith(RES_PREFIX_ASSETS)) {
                is = context.getAssets().open(getRealPath(path));
            } else {
                is = new FileInputStream(path);
            }
            result = loadByteArray(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return result;
    }

    public static byte[] loadByteArray(InputStream inputStream){
        byte[] result = new byte[0];
        ByteArrayOutputStream baos = null;
        try {
            int length = -1;
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();
            while ((length = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
            result = baos.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(baos);
        }
        return result;
    }
    // 4QQ + END

    public static byte[] readBytes(InputStream inputStream, int len){
        byte[] result = new byte[len];
        try {
            int sum = 0;
            int length = -1;
            while (sum < len && (length = inputStream.read(result, sum, len - sum)) != -1) {
                sum += length;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] loadAssetsByteArray(Context context, String path) {
        byte[] result = null;
        try {
            InputStream assetsInputStream = context.getAssets().open(path);
            int available = assetsInputStream.available();
            result = new byte[available];
            int byteCode = assetsInputStream.read(result);
            assetsInputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static String loadRawResourceString(final Context context, final int resourceId) {
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine;
        final StringBuilder body = new StringBuilder();
        try {
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(bufferedReader);
        }
        return body.toString();
    }

    public static void CopyAssetsMaterialToExternalStorage(Context context, String assetsDir) {
        try {
            String[] fileList = context.getAssets().list(assetsDir);
            if (fileList.length > 0) {//如果是目录
                File file = new File(context.getExternalFilesDir(null).getAbsolutePath() + File.separator + assetsDir);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileList) {
                    assetsDir = assetsDir + File.separator + fileName;

                    CopyAssetsMaterialToExternalStorage(context, assetsDir);

                    assetsDir = assetsDir.substring(0, assetsDir.lastIndexOf(File.separator));
                }
            } else {
                FileUtils.copyAssets(context, assetsDir, context.getExternalFilesDir(null).getAbsolutePath() + File.separator + assetsDir);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyAssetsToSDCard(Context context, String assetsDir, String dstDir) {
        try {
            String[] fileList = context.getAssets().list(assetsDir);
            if (fileList.length > 0) {//如果是目录
                File file = new File(dstDir + File.separator + assetsDir);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileList) {
                    assetsDir = assetsDir + File.separator + fileName;

                    copyAssetsToSDCard(context, assetsDir, dstDir);

                    assetsDir = assetsDir.substring(0, assetsDir.lastIndexOf(File.separator));
                }
            } else {
                FileUtils.copyAssets(context, assetsDir, dstDir + File.separator + assetsDir);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝assets下文件到目标地址
     *
     * @param context   assets所需的上下文环境
     * @param assetName assets文件路径
     * @param dst       目标文件路径
     * @return 是否拷贝成功
     */
    public static boolean copyAssets(Context context, String assetName, String dst) {
        return copyAssets(context, assetName, dst, SIMPLE_ASSET_MD5_COMPARATOR);
    }

    /**
     * Copy asset files. If assetName is a file, the it will be copied to file dst.
     *
     * @param context    application context.
     * @param assetName  asset name to copy.
     * @param dst        destination file.
     * @param comparator a asset file comparator to determine whether asset & dst are equal files. Null to overwrite all dst
     *                   files.
     */
    public static boolean copyAssets(Context context, String assetName, String dst, AssetFileComparator comparator) {
        return performCopyAssetsFile(context, assetName, dst, comparator);
    }

    private static boolean performCopyAssetsFile(Context context, String assetPath, String dstPath, AssetFileComparator comparator) {
        if (TextUtils.isEmpty(assetPath) || TextUtils.isEmpty(dstPath)) {
            Log.e(TAG, "TextUtils.isEmpty(assetPath) || TextUtils.isEmpty(dstPath)");
            return false;
        }

        AssetManager assetManager = context.getAssets();
        File dstFile = new File(dstPath);

        boolean succeed = false;
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dstFile.exists()) {
                if (comparator != null && comparator.equals(context, assetPath, dstFile)) {
                    Log.i(TAG, "comparator != null && comparator.equals(context, assetPath, dstFile)");
                    return true;
                } else {
                    // file will be overwrite later.
                    if (dstFile.isDirectory()) {
                        delete(dstFile);
                    }
                }
            }

            File parent = dstFile.getParentFile();
            if (parent.isFile()) {
                delete(parent);
            }
            if (!parent.exists() && !parent.mkdirs()) {
                Log.e(TAG, "!parent.exists() && !parent.mkdirs(), parent dir = " + parent.getAbsolutePath());
                return false;
            }

            in = assetManager.open(assetPath);
            if (in.available() <= 0) {
                succeed = false;
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dstFile));
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                succeed = true;
            }

        } catch (Throwable e) {
            Log.e(TAG, String.format("performCopyAssetsFile exception catched, info = %s", e.toString()));
            //RqdUtils.reportMsgToRDM(String.format("performCopyAssetsFile exception catched, info = %s", e.toString()));
            // delete broken file.
            delete(dstFile);
        } finally {
            closeSilently(in);
            closeSilently(out);
        }
        return succeed;
    }

    private static long getFileLength(String path) {

        File file = new File(path);
        if(!file.exists()){
            return -1;
        }
        return file.length();

    }

    private static long getAssetLength(Context context, String assetPath) {
        AssetManager assetManager = context.getAssets();
        // try to determine whether or not copy this asset file, using their size.
        AssetFileDescriptor fd = null;
        try {
            fd = assetManager.openFd(assetPath);
            return fd.getLength();
        } catch (IOException e) {
            // this file is compressed. cannot determine it's size.
        } finally {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // try stream.
        InputStream tmpIn = null;
        try {
            tmpIn = assetManager.open(assetPath);
            return tmpIn.available();
        } catch (IOException e) {
            // do nothing.
        } finally {
            closeSilently(tmpIn);
        }
        return -1;
    }

    /**
     * 删除目录下所有文件
     *
     * @param dir 目录
     */
    public static void clearDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        final File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (final File f : files) {
            if (f.isDirectory()) {
                clearDir(f);
            } else {
                f.delete();
            }
        }
        dir.delete();
    }

    /**
     * 删除目录列表下所有文件
     *
     * @param dirs 目录列表
     */
    public static void clearDirs(File[] dirs) {
        if (dirs == null) {
            return;
        }
        for (File dir : dirs) {
            clearDir(dir);
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return true为文件存在，false为文件不存在
     */
    public static boolean exists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        // assets中的文件，默认一定存在；非assets中的文件，需要正常判断是否存在
        return path.indexOf("assets") >= 0 || new File(path).exists();

    }

    public static boolean assetAndPathExist(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        if (path.startsWith("assets")) {
            AssetManager assetManager = context.getAssets();
            try {
                String file = path.substring(path.lastIndexOf("/") + 1);
                String dir = path.substring(0, path.lastIndexOf("/"));
                dir = dir.replace("assets://", "");
                String[] files = assetManager.list(dir);
                if (files != null) {
                    for (String f : files) {
                        if (TextUtils.equals(f.substring(f.lastIndexOf("/") + 1), file)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }

        return new File(path).exists();
    }

    public static boolean hasFiles(String path) {
        if(!exists(path)){
            return false;
        }
        File file = new File(path);
        if(file.isDirectory()) {
            String[] list = file.list();
            return list != null && list.length > 0;
        } else {
            return true;
        }

    }
    public static boolean move(File srcFile, String destPath) {
        // Destination directory
        File dir = new File(destPath);

        // Move file to new directory
        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

        return success;
    }

    public static void delete(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File f = new File(path);
        delete(f);
    }

    /**
     * 删除文件或文件夹
     *
     * @param file 文件{@link File}
     */
    public static void delete(@Nullable File file) {
        if (file == null) {
            return;
        }

        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * Delete corresponding path, file or directory.
     *
     * @param file      path to delete.
     * @param ignoreDir whether ignore directory. If true, all files will be deleted while directories is reserved.
     */
    public static void delete(File file, boolean ignoreDir) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] fileList = file.listFiles();
        if (fileList == null) {
            return;
        }

        for (File f : fileList) {
            delete(f, ignoreDir);
        }
        // delete the folder if need.
        if (!ignoreDir) {
            file.delete();
        }
    }

    public static void deleteFiles(String dirPath, final String suffix) {
        if (TextUtils.isEmpty(dirPath)) {
            return;
        }
        File[] files = new File(dirPath).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (TextUtils.isEmpty(suffix)) {
                    return true;
                }
                return s.endsWith(suffix);
            }
        });
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    /**
     * Delete all files of the directory
     *
     * @param path
     */
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                deleteAllFilesOfDir(files[i]);
            }
        }
        path.delete();
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                // empty.
            }
        }
    }

    public static String readTxtFile(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (path.startsWith(FileUtils.RES_PREFIX_ASSETS)) {
            InputStream in = null;
            BufferedReader r = null;
            try {
                in = context.getAssets().open(path.substring(FileUtils.RES_PREFIX_ASSETS.length()));
                r = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            BufferedReader r = null;
            try {
                r = new BufferedReader(new FileReader(path));
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 计算文件SHA1值
     *
     * @param file
     * @return
     */
    public static String getSHA1(String file) {
        if (new File(file).exists()) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                FileInputStream fis = new FileInputStream(file);
                byte[] bytesBuffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(bytesBuffer)) != -1) {
                    digest.update(bytesBuffer, 0, bytesRead);
                }
                fis.close();
                StringBuilder sb = new StringBuilder();
                for (byte b : digest.digest()) {
                    int x = b & 0xFF;
                    sb.append(x < 16 ? "0" : "");
                    sb.append(Integer.toHexString(x).toLowerCase());
                }
                return sb.toString();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 计算文件MD5值
     *
     * @param file
     * @return
     */
    public static String getMD5(String file, String salt) {
        if (new File(file).exists()) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                FileInputStream fis = new FileInputStream(file);
                byte[] bytesBuffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(bytesBuffer)) != -1) {
                    digest.update(bytesBuffer, 0, bytesRead);
                }
                digest.update(salt.getBytes());
                fis.close();
                StringBuilder sb = new StringBuilder();
                for (byte b : digest.digest()) {
                    int x = b & 0xFF;
                    sb.append(x < 16 ? "0" : "");
                    sb.append(Integer.toHexString(x).toLowerCase());
                }
                return sb.toString();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 计算assets中文件MD5值
     * @param context
     * @param assetPath
     * @param salt
     * @return
     */
    public static String getAssetsMD5(Context context, String assetPath, String salt) {
        if (context == null || TextUtils.isEmpty(assetPath) || salt == null) {
            return null;
        }

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(assetPath);
            if (inputStream.available() <= 0) {
                return null;
            }

            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] bytesBuffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytesBuffer)) > 0) {
                digest.update(bytesBuffer, 0, bytesRead);
            }
            digest.update(salt.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digest.digest()) {
                int x = b & 0xFF;
                sb.append(x < 16 ? "0" : "");
                sb.append(Integer.toHexString(x).toLowerCase());
            }
            return sb.toString();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public static String md5ForBase64Data(String base64Data) {
        final char[] magicNums = {
                'r', 'D', 'z', 'o',
                'i', 'e', '5', 'e',
                '3', 'o', 'n', 'g',
                'f', 'z', '1', 'l'};
        StringBuffer original = new StringBuffer(base64Data);
        for (int i = 0; i < magicNums.length; i++) {
            original.append(magicNums[i]);
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.toString().getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转为base64
     *
     * @param file
     * @return
     */
    public static String toBase64(String file) {
        String result = null;
        try {
            FileInputStream fis = new FileInputStream(new File(file));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Base64OutputStream base64out = new Base64OutputStream(baos, Base64.NO_WRAP);
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = fis.read(buffer)) >= 0) {
                base64out.write(buffer, 0, len);
            }
            base64out.flush();
            base64out.close();
            /*
             * Why should we close Base64OutputStream before processing the data:
             * http://stackoverflow.com/questions/24798745/android-file-to-base64-using-streaming-sometimes-missed-2-bytes
             */
            result = new String(baos.toByteArray(), "UTF-8");
            baos.close();
            fis.close();
        } catch (Exception e) {
            //空异常处理
        }
        return result;
    }

    /**
     * Comparator of asset and target file.
     */
    public interface AssetFileComparator {
        boolean equals(Context context, String assetPath, File dstFile);
    }

    public interface FileComparator {
        boolean equals(String srcPath, File dstFile);
    }

    /**
     * Simple asset file comparator which only depends on asset file length.
     */
    public final static AssetFileComparator SIMPLE_ASSET_COMPARATOR = new AssetFileComparator() {
        @Override
        public boolean equals(Context context, String assetPath, File dstFile) {
            long assetFileLength = getAssetLength(context, assetPath);
            return assetFileLength != -1 && assetFileLength == dstFile.length();
        }
    };

    /**
     * Simple asset file comparator which only depends on asset file length.
     */
    public final static AssetFileComparator SIMPLE_ASSET_MD5_COMPARATOR = new AssetFileComparator() {
        @Override
        public boolean equals(Context context, String assetPath, File dstFile) {
            String assetsMD5 = getAssetsMD5(context, assetPath, FileUtils.MD5_SALT);
            String dstMD5 = getMD5(dstFile.getAbsolutePath(), FileUtils.MD5_SALT);

            if (TextUtils.isEmpty(assetsMD5) || TextUtils.isEmpty(dstMD5)) {
                return false;
            }

            return assetsMD5.equals(dstMD5);
        }
    };

    /**
     * Simple asset file comparator which only depends on asset file length.
     */
    public final static FileComparator SIMPLE_FILE_COMPARATOR = new FileComparator() {
        @Override
        public boolean equals(String srcPath, File dstFile) {
            long fileLength = getFileLength(srcPath);
            return fileLength != -1 && fileLength == dstFile.length();
        }
    };

    public static boolean copyFile(String srcPath, String dstPath) {
        return copyFile(srcPath, dstPath, SIMPLE_FILE_COMPARATOR);
    }


    private static boolean copyFile(String srcPath, String dstPath, FileComparator comparator) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            Log.e(TAG, "TextUtils.isEmpty(assetPath) || TextUtils.isEmpty(dstPath)");
            return false;
        }

        boolean succeed = false;
        InputStream in = null;
        OutputStream out = null;
        File dstFile = new File(dstPath);

        try {
            if (dstFile.exists()) {
                if (comparator != null && comparator.equals(srcPath, dstFile)) {
                    Log.i(TAG, "comparator != null && comparator.equals(context, assetPath, dstFile)");
                    return true;
                } else {
                    // file will be overwrite later.
                    if (dstFile.isDirectory()) {
                        delete(dstFile);
                    }
                }
            }

            File parent = dstFile.getParentFile();
            if (parent.isFile()) {
                delete(parent);
            }
            if (!parent.exists() && !parent.mkdirs()) {
                Log.e(TAG, "!parent.exists() && !parent.mkdirs(), parent dir = " + parent.getAbsolutePath());
                return false;
            }

            in = new FileInputStream(srcPath);
            if (in.available() <= 0) {
                succeed = false;
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dstFile));
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                succeed = true;
            }

        } catch (Throwable e) {
            Log.e(TAG, String.format("performCopyFile exception catched, info = %s", e.toString()));
            //RqdUtils.reportMsgToRDM(String.format("performCopyAssetsFile exception catched, info = %s", e.toString()));
            // delete broken file.
            delete(dstFile);
        } finally {
            closeSilently(in);
            closeSilently(out);
        }
        return succeed;
    }

    public static boolean copyFile(InputStream is, OutputStream os) {
        if (is == null || os == null){
            return false;
        }
        try {
            byte[] bs = new byte[4096];
            int len;
            while ((len = is.read(bs)) > 0) {
                os.write(bs, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return true;
    }

    public static boolean copyFile(InputStream fosFrom, String dstPath) {
        OutputStream fosTo = null;
        try {
            fosTo = new FileOutputStream(dstPath);
            byte[] bt = new byte[4096];
            int c;
            while ((c = fosFrom.read(bt)) > 0) {
                fosTo.write(bt, 0, c);
            }
            return true;
        } catch (Exception ex) {
            //空异常处理
        } finally {
            IOUtils.closeQuietly(fosFrom);
            IOUtils.closeQuietly(fosTo);
        }
        return false;
    }

    public static void merge(List<String> fInputs, File fout) throws Exception {
        File fin = null;
        // 构建文件输出流
        FileOutputStream out = new FileOutputStream(fout);
        byte[] bt = new byte[1024];
        for (String path : fInputs) {
            // 打开文件输入流
            fin = new File(path);
            FileInputStream in = new FileInputStream(fin);
            // 从输入流中读取数据，并写入到文件数出流中
            int len;
            while ((len = in.read(bt)) > 0) {
                out.write(bt, 0, len);
            }
            out.flush();
            in.close();
        }
        out.close();
    }

    public static void asyncCopyFile(InputStream srcInputStream, String dstPath, OnFileCopyListener listener) {
        FileCopyTask task = new FileCopyTask(srcInputStream, dstPath);
        task.setOnFileCopyListener(listener);
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public static String readTextFileFromRaw(final Context context, final int resourceId) {
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine;
        final StringBuilder body = new StringBuilder();

        try {
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(bufferedReader);
        }

        return body.toString();
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    // kenney: 最终保存到相册中的文件名：selfiee_2018112129173018.mp4
    public static String getFinalVideoName() {
        return String.format("selfiee_%s.mp4", (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
    }

    static class FileCopyTask extends AsyncTask<String, String, Boolean> {

        OnFileCopyListener mListener;
        InputStream mSrcInputStream;
        String mDestPath;

        FileCopyTask(InputStream is, String path) {
            mSrcInputStream = is;
            mDestPath = path;
        }

        public void setOnFileCopyListener(OnFileCopyListener listener) {
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mListener != null) {
                mListener.onCopyStart();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return copyFile(mSrcInputStream, mDestPath);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (mListener != null) {
                if (result) {
                    mListener.onCopySuccess();
                }else {
                    mListener.onCopyFailed();
                }
            }
        }
    }

    public interface OnFileCopyListener {
        void onCopyStart();

        void onCopySuccess();

        void onCopyFailed();
    }

    // 4QQ + BEGIN
    public static File createFile(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            if (f.getParentFile() != null && !f.getParentFile().exists()) {
                if (f.getParentFile().mkdirs()) {
                    f.createNewFile();
                }
            } else {
                f.createNewFile();
            }
        }
        return f;
    }

    public static String genSeperateFileDir(String fileDir) {
        if (fileDir != null && (!fileDir.endsWith(File.separator))) {
            fileDir = fileDir + File.separator;
        }

        return fileDir;
    }


    public static long getFolderSize(File file){
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
