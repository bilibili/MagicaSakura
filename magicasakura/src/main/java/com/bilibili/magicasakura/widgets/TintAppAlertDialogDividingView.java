package com.bilibili.magicasakura.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.bilibili.magicasakura.R;
import com.bilibili.magicasakura.utils.ThemeUtils;

/**
 * @author xyczero617@gmail.com
 * @time 2015/9/1
 */
public class TintAppAlertDialogDividingView extends View {
    public static final int[] TINT_ATTRS = {
            android.R.attr.background
    };

    public TintAppAlertDialogDividingView(Context context) {
        this(context, null);
    }

    public TintAppAlertDialogDividingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintAppAlertDialogDividingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, TINT_ATTRS);
        if (a.hasValue(0)) {
            if (a.getResourceId(0, 0) == android.R.color.holo_blue_light) {
                setBackgroundColor(ThemeUtils.getThemeAttrColor(context, R.attr.themeColorSecondary));
            }
        }
    }
}
