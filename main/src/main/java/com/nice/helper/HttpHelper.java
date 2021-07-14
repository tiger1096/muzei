package com.nice.helper;

import android.text.TextUtils;
import android.util.Log;

import com.alexqzhang.user.ui.LoginActivity;
import com.alexqzhang.util.DeviceUtils;
import com.alexqzhang.util.FileUtils;
import com.nice.config.NiceToSeeYouConstant;
import com.nice.storage.SharedPreferencesUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpHelper {
    private final static String TAG = HttpHelper.class.getSimpleName();

    public static Map<String,List<String>> postForHeader(String httpUrl, JSONObject json) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            int num = SharedPreferencesUtils.getInt(NiceToSeeYouConstant.SET_COOKIE_NUM);
            if (num > 0) {
                for (int i = 0; i < num; i ++) {
                    connection.addRequestProperty("Cookie", SharedPreferencesUtils.getString(NiceToSeeYouConstant.SET_COOKIE + i));
                }
            }

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(outputStream);
            String jsonString = json.toString();
            writer.writeBytes(jsonString);
            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return connection.getHeaderFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    public static String postForResult(String httpUrl, JSONObject json) {
        String httpJson = "";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            int num = SharedPreferencesUtils.getInt(NiceToSeeYouConstant.SET_COOKIE_NUM);
            if (num > 0) {
                for (int i = 0; i < num; i ++) {
                    connection.addRequestProperty("Cookie", SharedPreferencesUtils.getString(NiceToSeeYouConstant.SET_COOKIE + i));
                }
            }
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(outputStream);
            String jsonString = json.toString().replace("\\\"", "\"");

            writer.writeBytes(jsonString);
            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream is = connection.getInputStream();
                httpJson = getStringFromInputStream(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception = " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return httpJson;
    }

    public static String downloadFile(String httpUrl, String basePath) {
        File downloadDir = DeviceUtils.getExternalFilesDir(NiceToSeeYouConstant.getContext(), basePath);
        String fileName = httpUrl.substring(httpUrl.lastIndexOf("/") + 1);
        if (downloadDir == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        File downloadFile = new File(FileUtils.genSeperateFileDir(downloadDir.getAbsolutePath()) +
                fileName);
        OutputStream output = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream input = conn.getInputStream();

            output = new FileOutputStream(downloadFile);
            byte[] buffer = new byte[4 * 1024];
            while (input.read(buffer) != -1) {
                output.write(buffer);
            }
            output.flush();
            input.close();

            return downloadFile.getAbsolutePath();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    Log.e(TAG, "downloadFile output.close = " + e.getMessage());
                }
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        String html = baos.toString();
        baos.close();
        return html;
    }
}
