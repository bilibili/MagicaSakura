/*
 * Copyright (C) 2016 Bilibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bilibili.magicasakurademo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakurademo.utils.ThemeHelper;
import com.bilibili.magicasakura.utils.ThemeUtils;

/**
 * @author xyczero
 * @time 16/5/2
 */
public class MyApplication extends Application implements ThemeUtils.switchColor {

    @Override
    public void onCreate() {
        super.onCreate();
        ThemeUtils.setSwitchColor(this);
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int originColor) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor;
        }
        String theme = getTheme(context);
        int colorId = -1;

        if (theme != null) {
            colorId = getThemeColor(context, originColor, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : originColor;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_LIGHT) {
            return "green_light";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return "red";
        }
        return null;
    }

    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xfffb7299:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case 0xffb85671:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case 0x99f0486c:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return -1;
    }
}
