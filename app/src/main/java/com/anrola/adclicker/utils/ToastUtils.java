package com.anrola.adclicker.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * 全局Toast工具类，支持立即关闭当前Toast并显示新内容
 * 解决多个Toast排队显示、子线程无法显示Toast的问题
 */
public class ToastUtils {
    // 静态变量保存当前显示的Toast实例
    private static Toast sCurrentToast;
    // 主线程Handler
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    /**
     * 私有化构造方法，禁止实例化
     */
    private ToastUtils() {
        throw new UnsupportedOperationException("不能实例化ToastUtils工具类");
    }

    /**
     * 显示短时长Toast（默认2秒），自动替换当前显示的Toast
     * @param context 上下文（建议使用Application Context避免内存泄漏）
     * @param message 要显示的文本内容
     */
    public static void showShortToast(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时长Toast（默认3.5秒），自动替换当前显示的Toast
     * @param context 上下文（建议使用Application Context避免内存泄漏）
     * @param message 要显示的文本内容
     */
    public static void showLongToast(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 显示自定义时长的Toast，自动替换当前显示的Toast
     * @param context 上下文（建议使用Application Context避免内存泄漏）
     * @param message 要显示的文本内容
     * @param duration 显示时长（Toast.LENGTH_SHORT / Toast.LENGTH_LONG）
     */
    public static void showToast(Context context, CharSequence message, int duration) {
        // 空值校验
        if (context == null || message == null || message.length() == 0) {
            return;
        }
        // 确保在主线程执行Toast显示（子线程调用时自动切换）
        sMainHandler.post(() -> {
            // 取消当前正在显示的Toast
            cancelCurrentToast();

            // 创建新的Toast实例（使用ApplicationContext避免内存泄漏）
            Context appContext = context.getApplicationContext();
            sCurrentToast = Toast.makeText(appContext, message, duration);

            // 显示新的Toast
            sCurrentToast.show();
        });
    }

    /**
     * 立即关闭当前显示的Toast
     */
    public static void cancelCurrentToast() {
        sMainHandler.post(() -> {
            if (sCurrentToast != null) {
                sCurrentToast.cancel();
                sCurrentToast = null; // 清空引用，避免内存泄漏
            }
        });
    }

    /**
     * 重载方法：支持传入字符串资源ID
     */
    public static void showShortToast(Context context, int resId) {
        try {
            String message = context.getResources().getString(resId);
            showShortToast(context, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重载方法：支持传入字符串资源ID
     */
    public static void showLongToast(Context context, int resId) {
        try {
            String message = context.getResources().getString(resId);
            showLongToast(context, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}