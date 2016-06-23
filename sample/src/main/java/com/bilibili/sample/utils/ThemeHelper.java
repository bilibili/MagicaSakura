package com.bilibili.sample.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author xyczero
 * @time 16/5/2
 */
public class ThemeHelper {
    private static final String CURRENT_THEME = "theme_current";

    public static final int THEME_PINK = 0x1;
    public static final int THEME_PURPLE = 0x2;
    public static final int THEME_BLUE = 0x3;
    public static final int THEME_GREEN = 0x4;
    public static final int THEME_GREEN_LIGHT = 0x5;
    public static final int THEME_YELLOW = 0x6;
    public static final int THEME_ORANGE = 0x7;
    public static final int THEME_RED = 0x8;

    public static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int themeId) {
        getSharePreference(context).edit()
                .putInt(CURRENT_THEME, themeId)
                .commit();
    }

    public static int getTheme(Context context) {
        return getSharePreference(context).getInt(CURRENT_THEME, THEME_PINK);
    }

    public static boolean isDefaultTheme(Context context) {
        return getTheme(context) == THEME_PINK;
    }
}
