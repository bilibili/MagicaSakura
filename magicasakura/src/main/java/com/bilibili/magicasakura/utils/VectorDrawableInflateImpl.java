/*
 * Copyright (c) 2015-2017 BiliBili Inc.
 */

package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static com.bilibili.magicasakura.utils.DrawableUtils.obtainAttributes;

/**
 * @author Jungly
 * @email jungly.ik@gmail.com
 * @since 2017/05/26
 */
class VectorDrawableInflateImpl implements DrawableInflateDelegate {

    private int resId;

    public VectorDrawableInflateImpl(int resId) {
        this.resId = resId;
    }

    @Override
    public Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException {
        ColorStateList colorFilter = getTintColorList(context, attrs, android.R.attr.tint);
        Drawable d;
        if (resId == 0) {
            d = VectorDrawableCompat.createFromXmlInner(context.getResources(), parser, attrs);
        } else {
            d = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
        }
        if (d != null && colorFilter != null) {
            DrawableCompat.setTintList(d, colorFilter);
        }
        return d;
    }

    private ColorStateList getTintColorList(Context context, AttributeSet attrs, int tintAttr) {
        TypedArray a = obtainAttributes(context.getResources(), context.getTheme(), attrs, new int[]{tintAttr});
        if (!a.hasValue(0)) {
            a.recycle();
            return null;
        }
        ColorStateList cls = null;
        int colorRes = a.getResourceId(0, 0);
        if (colorRes != 0) {
            cls = TintManager.get(context).getColorStateList(colorRes);
        } else {
            int color = ThemeUtils.replaceColor(context, a.getColor(0, 0));
            if (color != 0) {
                int[][] states = new int[1][];
                states[0] = new int[]{};
                cls = new ColorStateList(states, new int[]{color});
            }
        }
        a.recycle();
        return cls;
    }
}
