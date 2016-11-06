package com.bilibili.magicasakura.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;

import com.bilibili.magicasakura.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xyczero617@gmail.com
 * @time 16/11/6
 */

public class RippleDrawableInflateImpl extends LayerDrawableInflateImpl {
    private static Method sAddLayer;
    private static Method sEnsurePadding;

    @Override
    public Drawable inflateDrawable(Context context, XmlPullParser parser, AttributeSet attrs) throws IOException, XmlPullParserException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int innerDepth = parser.getDepth() + 1;
            int type;
            int depth;
            int markIndex = -1;
            int layerAttrUseCount = 0;
            int drawableUseCount = 0;
            int space = STEP;
            //L,T,R,B,S,E,id
            int[][] childLayersAttrs = new int[space][ATTRS.length];
            Drawable[] drawables = new Drawable[space];
            ColorStateList csl = DrawableUtils.getTintColorList(context, attrs, android.R.attr.color);
            if (csl == null) {
                return null;
            }
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                    && ((depth = parser.getDepth()) >= innerDepth || type != XmlPullParser.END_TAG)) {
                if (type != XmlPullParser.START_TAG) {
                    continue;
                }

                if (depth > innerDepth || !parser.getName().equals("item")) {
                    continue;
                }

                if (layerAttrUseCount >= childLayersAttrs.length) {
                    int[][] dstInt = new int[drawables.length + STEP][ATTRS.length];
                    System.arraycopy(childLayersAttrs, 0, dstInt, 0, childLayersAttrs.length);
                    childLayersAttrs = dstInt;
                }
                updateLayerAttrs(context, attrs, childLayersAttrs[layerAttrUseCount]);
                layerAttrUseCount++;

                Drawable drawable = DrawableUtils.getAttrDrawable(context, attrs, android.R.attr.drawable);
                if (DrawableUtils.getAttrResourceId(context, attrs, android.R.attr.id, 0) == android.R.id.mask) {
                    markIndex = layerAttrUseCount - 1;
                }

                // If the layer doesn't have a drawable or unresolved theme
                // attribute for a drawable, attempt to parse one from the child
                // element.
                if (drawable == null) {
                    while ((type = parser.next()) == XmlPullParser.TEXT) {
                    }
                    if (type != XmlPullParser.START_TAG) {
                        throw new XmlPullParserException(parser.getPositionDescription()
                                + ": <item> tag requires a 'drawable' attribute or "
                                + "child tag defining a drawable");
                    }
                    drawable = DrawableUtils.createFromXmlInner(context, parser, attrs);
                } else {
                    final ColorStateList cls = DrawableUtils.getTintColorList(context, attrs, R.attr.drawableTint);
                    if (cls != null) {
                        drawable = ThemeUtils.tintDrawable(drawable, cls, DrawableUtils.getTintMode(context, attrs, R.attr.drawableTintMode));
                    }
                }

                if (drawable != null) {
                    if (drawableUseCount >= drawables.length) {
                        Drawable[] dst = new Drawable[drawables.length + STEP];
                        System.arraycopy(drawables, 0, dst, 0, drawables.length);
                        drawables = dst;
                    }
                    drawables[drawableUseCount] = drawable;
                    drawableUseCount++;
                }
            }

            if (drawables[0] == null || drawableUseCount != layerAttrUseCount) {
                return null;
            } else {
                RippleDrawable rippleDrawable;
                rippleDrawable = new RippleDrawable(csl, null, markIndex >= 0 ? drawables[markIndex] : null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int radius = DrawableUtils.getAttrDimensionPixelSize(context, attrs, android.R.attr.radius, RippleDrawable.RADIUS_AUTO);
                    rippleDrawable.setRadius(radius);
                }
                for (int i = 0; i < drawables.length; i++) {
                    if (i == markIndex) {
                        continue;
                    }
                    addLayer(rippleDrawable, drawables[i]);
                    int[] childLayersAttr = childLayersAttrs[i];
                    if (childLayersAttr[0] != 0 || childLayersAttr[1] != 0 || childLayersAttr[2] != 0 || childLayersAttr[3] != 0) {
                        rippleDrawable.setLayerInset(i, childLayersAttr[0], childLayersAttr[1], childLayersAttr[2], childLayersAttr[3]);
                    }
                    if (childLayersAttr[4] != 0) {
                        rippleDrawable.setId(i, childLayersAttr[4]);
                    }
                }
                return rippleDrawable;
            }
        }
        return null;
    }

    private void addLayer(RippleDrawable rippleDrawable, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rippleDrawable.addLayer(drawable);
        } else {
            try {
                if (sAddLayer == null) {
                    sAddLayer = Class.forName("android.graphics.drawable.LayerDrawable")
                            .getDeclaredMethod("addLayer", Drawable.class, int[].class, int.class, int.class, int.class, int.class, int.class);
                    sAddLayer.setAccessible(true);
                }
                sAddLayer.invoke(rippleDrawable, drawable, null, 0, 0, 0, 0, 0);
                if (sEnsurePadding == null) {
                    sEnsurePadding = Class.forName("android.graphics.drawable.LayerDrawable").getDeclaredMethod("ensurePadding");
                    sEnsurePadding.setAccessible(true);
                }
                sEnsurePadding.invoke(rippleDrawable);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
