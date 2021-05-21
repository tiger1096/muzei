package com.alexqzhang.user.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexqzhang.mainpage.ui.MainPageActivity;

import com.nice.seeyou.R;

public class LoginActivity extends AppCompatActivity {
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
                startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
            }
        });

        // TODO 后续切换登录方式应该不需要跳转新的activity，只需要改动页面的文字即可，这样切换的时候没有闪烁

//        imageView = findViewById(R.id.imageView);
//        textView = findViewById(R.id.textView);

//        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
//            public void onSwipeTop() {
//            }
//
//            public void onSwipeRight() {
//                if (count == 0) {
//                    imageView.setImageResource(R.drawable.good_night_img);
//                    textView.setText("Night");
//                    count = 1;
//                } else {
//                    imageView.setImageResource(R.drawable.good_morning_img);
//                    textView.setText("Morning");
//                    count = 0;
//                }
//            }
//
//            public void onSwipeLeft() {
//                if (count == 0) {
//                    imageView.setImageResource(R.drawable.good_night_img);
//                    textView.setText("Night");
//                    count = 1;
//                } else {
//                    imageView.setImageResource(R.drawable.good_morning_img);
//                    textView.setText("Morning");
//                    count = 0;
//                }
//            }
//
//            public void onSwipeBottom() {
//            }
//
//        });
    }
}