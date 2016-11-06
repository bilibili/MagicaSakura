package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author xyczero617@gmail.com
 * @time 16/11/6
 */

interface DrawableInflateDelegate {
    Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException;
}
