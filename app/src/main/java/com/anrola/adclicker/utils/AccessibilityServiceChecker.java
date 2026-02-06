package com.anrola.adclicker.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class AccessibilityServiceChecker {
    private static final String TAG = "AccessibilityServiceChecker";

    /**
     * 检查无障碍服务是否已开启
     * @param context          上下文
     * @param serviceClassName 辅助服务类名 "com.example.adclicker.service.AccessibilityService"
     * @return 是否已开启
     */
    public static boolean isAccessibilityServiceEnabled(Context context, String serviceClassName){
        // 获取无障碍服务管理器
        AccessibilityManager accessibilityManager = (AccessibilityManager)
                context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager == null){
            ToastUtils.showShortToast(context, "获取无障碍服务管理器失败");
            return false;
        }

        // 获取已启用的无障碍服务列表
        List<AccessibilityServiceInfo> enabledServices;
        enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
                AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        if (enabledServices == null){
            ToastUtils.showShortToast(context, "获取已启用无障碍服务列表失败");
            return false;
        }

        // 遍历已启用无障碍服务列表
        if (!enabledServices.isEmpty()) {
            String packageName = context.getPackageName();
            for (AccessibilityServiceInfo serviceInfo : enabledServices) {
                if (serviceInfo == null) {
                    continue;
                }
                // 获取服务的组件信息
                String servicePackageName = serviceInfo.getResolveInfo().serviceInfo.packageName;
                String serviceClass = serviceInfo.getResolveInfo().serviceInfo.name;

                // 匹配包名和服务类名
                if (packageName.equals(servicePackageName) && serviceClassName.equals(serviceClass)) {
                    return true;
                }
            }
        }

        return false;

    }

    /**
     * 获取无障碍服务信息
     * @param context          上下文
     * @param serviceClassName 辅助服务类名 "com.example.adclicker.service.AccessibilityService"
     * @return 无障碍服务信息, 如果未找到则返回null
     */
    public static AccessibilityServiceInfo getAccessibilityServiceInfo(Context context, String serviceClassName){
        // 获取无障碍服务管理器
        AccessibilityManager accessibilityManager = (AccessibilityManager)
                context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager == null){
            ToastUtils.showShortToast(context, "获取无障碍服务管理器失败");
            return null;
        }

        // 获取已启用的无障碍服务列表
        List<AccessibilityServiceInfo> enabledServices;
        enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(
                AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        if (enabledServices == null){
            ToastUtils.showShortToast(context, "获取已启用无障碍服务列表失败");
            return null;
        }

        // 遍历已启用无障碍服务列表
        if (!enabledServices.isEmpty()) {
            String packageName = context.getPackageName();
            for (AccessibilityServiceInfo serviceInfo : enabledServices) {
                if (serviceInfo == null) {
                    continue;
                }
                // 获取服务的组件信息
                String servicePackageName = serviceInfo.getResolveInfo().serviceInfo.packageName;
                String serviceClass = serviceInfo.getResolveInfo().serviceInfo.name;

                // 匹配包名和服务类名
                if (packageName.equals(servicePackageName) && serviceClassName.equals(serviceClass)) {
                    return serviceInfo;
                }
            }
        }

        return null;
    }
}
