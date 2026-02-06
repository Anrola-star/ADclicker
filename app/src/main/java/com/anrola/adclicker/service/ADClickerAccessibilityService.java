package com.anrola.adclicker.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class ADClickerAccessibilityService extends AccessibilityService {
    private static final String TAG = "ADClickerAccessibilityService";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        getRootInActiveWindow();
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
           return;
        }

        List<AccessibilityNodeInfo>  skipBtnNodes  = source.findAccessibilityNodeInfosByText("跳过");

        if (skipBtnNodes != null && !skipBtnNodes.isEmpty()) {
            for (AccessibilityNodeInfo skipBtnNode : skipBtnNodes) {
                Log.d(TAG, String.format("[检测到疑似广告节点]： %s", skipBtnNode));
                if (!skipBtnNode.isClickable()) {
                    Log.d(TAG, "[节点不可点击]");
                    continue;
                }

                Log.d(TAG, String.format("点击跳过按钮： %s", skipBtnNode));
                skipBtnNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
