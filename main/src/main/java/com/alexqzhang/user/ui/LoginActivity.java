package com.alexqzhang.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexqzhang.mainpage.ui.MainPageActivity;
import com.mob.MobSDK;
import com.nice.seeyou.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private boolean isUsePassword = false;

    EditText telephoneNum;
    ImageView imageView;
    TextView textView;
    int count = 0;

    private List<String> loginCookies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TextView phoneValidate = findViewById(R.id.phone_validate);
        phoneValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PhoneLoginActivity.class));
            }
        });

        Button register = findViewById(R.id.register);
        register.setOnClickListener(this);
        TextView forgetPassword = findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(this);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobSDK.submitPolicyGrantResult(true, null);

                EventHandler eh = new EventHandler(){

                    @Override
                    public void afterEvent(int event, int result, Object data) {

                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //回调完成
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                //提交验证码成功
                            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                                //获取验证码成功
                            }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                                //返回支持发送验证码的国家列表
                            }
                        }else{
                            ((Throwable)data).printStackTrace();
                            Log.e("alex", ((Throwable)data).getMessage());
                        }
                    }
                };
                SMSSDK.registerEventHandler(eh); //注册短信回调
                SMSSDK.getVerificationCode("86", "13022116320");
                startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
            }
        });

        // TODO 后续切换登录方式应该不需要跳转新的activity，只需要改动页面的文字即可，这样切换的时候没有闪烁

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        EditText telephoneNum = findViewById(R.id.telephone_num);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                register();
                break;
            case R.id.forget_password:
//                testFetch();
                forgetPassword();
                break;
            case R.id.login:
                break;
            default:
                break;
        }
    }

    private void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.29:8080/march-server/UserManage/Login");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(5000);

                    connection.setRequestProperty("telephone", "13022116320");
                    connection.setRequestProperty("captcha", "966996");
                    connection.setRequestMethod("POST");

                    OutputStream os = connection.getOutputStream();

                    int code = connection.getResponseCode();
                    if (code != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "getResponseCode = " + code);
                    }

                    Map<String, List<String>> headers = connection.getHeaderFields();
                    if (headers != null && headers.containsKey("Set-Cookie")) {
                        List<String> cookies = headers.get("Set-Cookie");
                        for (String value : cookies) {
                            loginCookies.add(value);
                            Log.e(TAG, "value = " + value);
                        }
                    } else {
                        Log.e(TAG, "no Set-Cookie");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(TAG, "[register] " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "[register] " + e.getMessage());
                }
            }
        }).start();
    }

    private void forgetPassword() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.29:8080/march-server/UserManage/UpdateUser");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(5000);
                    for (String value : loginCookies) {
                        connection.addRequestProperty("Cookie", value);
                    }
                    connection.setRequestMethod("POST");

                    int code = connection.getResponseCode();
                    if (code != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "getResponseCode = " + code);
                        return;
                    }

                    Map<String, List<String>> headers = connection.getHeaderFields();
                    if (headers != null && headers.containsKey("Set-Cookie")) {
                        List<String> cookies = headers.get("Set-Cookie");
                        for (String value : cookies) {
                            Log.e(TAG, "value = " + value);
                        }
                    } else {
                        Log.e(TAG, "no Set-Cookie");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(TAG, "[register] " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "[register] " + e.getMessage());
                }
            }
        }).start();
    }

    private void testFetch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.29:8080/march-server/Wallpaper/FetchText");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(5000);
                    for (String value : loginCookies) {
                        connection.addRequestProperty("Cookie", value);
                    }
                    connection.setRequestMethod("POST");

                    int code = connection.getResponseCode();
                    if (code != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "getResponseCode = " + code);
                        return;
                    }

                    Map<String, List<String>> headers = connection.getHeaderFields();
                    if (headers != null && headers.containsKey("Set-Cookie")) {
                        List<String> cookies = headers.get("Set-Cookie");
                        for (String value : cookies) {
                            Log.e(TAG, "value = " + value);
                        }
                    } else {
                        Log.e(TAG, "no Set-Cookie");
                    }

                    String status = connection.getHeaderField("NTSY_STATUS");
                    Log.e(TAG, "NTSY_STATUS = " + status);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte [] buffer = new byte[1024 * 16];
                    InputStream inputStream = connection.getInputStream();

                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    String result = outputStream.toString();
                    Log.e(TAG, "jsonResult = " + result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(TAG, "[register] " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "[register] " + e.getMessage());
                }
            }
        }).start();
    }
}