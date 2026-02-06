package com.anrola.adclicker.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.anrola.adclicker.R;
import com.anrola.adclicker.utils.AccessibilityServiceChecker;
import com.anrola.adclicker.utils.ShadowDrawable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static class Views{
        public LinearLayout llAvatar;
        public Button btnOpenAccessibility;
        public ImageView ivRefresh;
        public TextView tvTips;
        public LinearLayout llServiceInfo;
    }
    private Views views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        initClickListener();
        views.ivRefresh.performClick();


    }

    private void initViews() {
        views = new Views();
        views.llAvatar = findViewById(R.id.ll_avatar);
        views.btnOpenAccessibility = findViewById(R.id.btn_open_accessibility);
        views.ivRefresh = findViewById(R.id.iv_refresh);
        views.tvTips = findViewById(R.id.tv_tips);
        views.llServiceInfo = findViewById(R.id.ll_service_info);

        ShadowDrawable.setShadowDrawable(
                views.btnOpenAccessibility, getColor(R.color.colorPrimary),
                50,getColor(R.color.colorPrimary), 18, 0, 0);
        ShadowDrawable.setShadowDrawable(
                views.ivRefresh,getColor(R.color.colorPrimary),
                50,getColor(R.color.colorPrimary), 18, 0, 0);
        ShadowDrawable.setShadowDrawable(
                views.llServiceInfo, getColor(R.color.colorPrimary),
                50,getColor(R.color.colorPrimary), 18, 0, 0);
        ShadowDrawable.setShadowDrawable(
                views.llAvatar, getColor(R.color.colorPrimary),
                180,getColor(R.color.colorPrimary), 18, 5, 5);
    }

    private void initClickListener() {
        // 点击按钮跳转到辅助服务设置页面
        views.btnOpenAccessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        views.ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                views.tvTips.setVisibility(View.GONE);
                Handler handler = new Handler(Looper.getMainLooper());


                Animation animation = new RotateAnimation(
                        0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
                animation.setDuration(1000);
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.RESTART);
                views.ivRefresh.startAnimation(animation);

                boolean isServiceEnabled = AccessibilityServiceChecker.isAccessibilityServiceEnabled(
                        MainActivity.this,
                        "com.anrola.adclicker.service.ADClickerAccessibilityService");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isServiceEnabled) {

                            String text = "已开启无障碍服务";
                            startTypewriterAnimation(views.tvTips, text, 0, 50);
                        }else {
                            String text = "检测到无障碍服务未开启!";
                            startTypewriterAnimation(views.tvTips, text, 0, 50);
                        }
                        views.tvTips.setVisibility(View.VISIBLE);
                        views.ivRefresh.clearAnimation();
                    }
                }, 1000);

            }
        });
    }

    private void startTypewriterAnimation(TextView textView, String text, int startCharIndex, long delayMillis) {
        if (startCharIndex >= text.length()) {
            return;
        }

        String showText = text.substring(0, startCharIndex + 1);
        textView.setText(showText);

        int nextCharIndex = startCharIndex + 1;
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTypewriterAnimation(textView, text, nextCharIndex, delayMillis);
            }
        }, delayMillis);
    }
}