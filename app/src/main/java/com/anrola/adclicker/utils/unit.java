package com.anrola.adclicker.utils;

import android.content.Context;

public class unit {
    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
