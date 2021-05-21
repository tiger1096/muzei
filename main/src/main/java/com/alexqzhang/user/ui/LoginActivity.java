package com.alexqzhang.user.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isUsePassword = false;

    EditText telephoneNum;
    ImageView imageView;
    TextView textView;
    int count = 0;

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

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobSDK.submitPolicyGrantResult(true, null);

                EventHandler eh=new EventHandler(){

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
            case R.id.login:
                break;
            default:
                break;
        }
    }
}