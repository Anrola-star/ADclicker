package com.anrola.adclicker.activity;

import static com.anrola.adclicker.utils.unit.dp2px;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    private static final String TAG = "MainActivity";
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static class Views{
        public LinearLayout llAvatar;
        public Button btnOpenAccessibility;
        public ImageView ivRefresh;
        public TextView tvTips;
        public LinearLayout llServiceInfo;
        public ImageView ivSetting;

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
        views.ivSetting = findViewById(R.id.iv_setting);

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
        ShadowDrawable.setShadowDrawable(
                views.ivSetting, getColor(R.color.colorPrimary),
                180,getColor(R.color.colorPrimary), 18, 0, 0);
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
        // 点击刷新按钮, 检测无障碍服务是否开启
        views.ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                views.tvTips.setVisibility(View.GONE);

                if(views.ivRefresh.getAnimation() == null){
                    Animation animation = new RotateAnimation(
                            0, 360,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f
                    );
                    animation.setDuration(1000);
                    animation.setRepeatCount(Animation.INFINITE);
                    animation.setRepeatMode(Animation.RESTART);
                    views.ivRefresh.startAnimation(animation);
                }
                views.ivRefresh.startAnimation(views.ivRefresh.getAnimation());

                boolean isServiceEnabled = AccessibilityServiceChecker.isAccessibilityServiceEnabled(
                        MainActivity.this,
                        "com.anrola.adclicker.service.ADClickerAccessibilityService");

                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isServiceEnabled) {
                            String text = "已开启无障碍服务";
                            startTypewriterAnimation(views.tvTips, text, 0, 50);
                        }else {
                            String text = "检测到无障碍服务未开启!";
                            startTypewriterAnimation(views.tvTips, text, 0, 50);
                        }
                        views.ivRefresh.getAnimation().setRepeatCount(1);
                        views.tvTips.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });
        // 点击设置按钮
        views.ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (views.ivSetting.getAnimation() == null){
                    Animation rotateAnimation = new RotateAnimation(
                            0, 360,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f
                    );
                    rotateAnimation.setDuration(1000);
                    rotateAnimation.setRepeatCount(Animation.INFINITE);
                    rotateAnimation.setRepeatMode(Animation.RESTART);
                    views.ivSetting.startAnimation(rotateAnimation);
                }
                views.ivSetting.startAnimation(views.ivSetting.getAnimation());

                showSettingBottomSheet();
            }
        });
        // 点击头像
        views.llAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 关于
            }
        });
    }

    private void showSettingBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        View sheetRootView = LayoutInflater.from(this)
                .inflate(R.layout.bottom_sheet_setting, null);

        Button btnFunctionSwitch = sheetRootView.findViewById(R.id.btn_function_switch);
        Button btnApplicationSetting = sheetRootView.findViewById(R.id.btn_application_setting);
        Button btnPermissionSetting = sheetRootView.findViewById(R.id.btn_permission_setting);
        ShadowDrawable.setShadowDrawable(
                btnFunctionSwitch, getColor(R.color.colorPrimary),
                50,getColor(R.color.colorPrimary), 18, 0, 0);

        ShadowDrawable.setShadowDrawable(
                        btnApplicationSetting, getColor(R.color.colorPrimary),
                        50,getColor(R.color.colorPrimary), 18, 0, 0);
        ShadowDrawable.setShadowDrawable(
                        btnPermissionSetting, getColor(R.color.colorPrimary),
                        50,getColor(R.color.colorPrimary), 18, 0, 0);

        bottomSheetDialog.setContentView(sheetRootView);

        btnFunctionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 功能开关
            }
        });

        btnApplicationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 应用设置
            }
        });

        btnPermissionSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 权限设置
            }
        });


        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        views.ivSetting.getAnimation().setRepeatCount(1);
                    }
                }, 1000);
            }
        });
        bottomSheetDialog.show();
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