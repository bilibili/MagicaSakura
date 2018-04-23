package com.bilibili.magicasakura.widgets;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;

import com.bilibili.magicasakura.utils.DrawableUtils;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.utils.TintInfo;
import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @since 17/5/23
 */

class AppCompatSwitchHelper {

    private int[] sAttrs;
    private SwitchCompat mSwitchCompat;
    private TintManager mTintManager;
    private boolean mSkipNextApply;

    private DrawableCallback mDrawableCallback;

    private TintInfo mTintInfo;
    private int mResId;
    private int mTintResId;
    private PorterDuff.Mode mTintMode;

    AppCompatSwitchHelper(SwitchCompat switchCompat, TintManager tintManager,
                                 int[] attrs, @NonNull DrawableCallback callback) {
        this.sAttrs = attrs;
        this.mTintManager = tintManager;
        this.mSwitchCompat = switchCompat;
        this.mDrawableCallback = callback;
    }

    private boolean skipNextApply() {
        if (mSkipNextApply) {
            mSkipNextApply = false;
            return true;
        }
        mSkipNextApply = true;
        return false;
    }

    private void setSkipNextApply(boolean flag) {
        mSkipNextApply = flag;
    }

    @SuppressWarnings("ResourceType")
    public void loadFromAttribute(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mSwitchCompat.getContext().obtainStyledAttributes(
                attrs, sAttrs, defStyleAttr, 0);
        if (array.hasValue(1)) {
            mTintResId = array.getResourceId(1, 0);
            if (array.hasValue(2)) {
                setSupportDrawableTintMode(mTintMode = DrawableUtils.parseTintMode(array.getInt(2, 0), null));
            }
            setSupportDrawableTint(mTintResId);
        } else {
            Drawable drawable = mTintManager.getDrawable(mResId = array.getResourceId(0, 0));
            if (drawable != null) {
                setDrawable(drawable);
            }
        }
        array.recycle();
    }

    /**
     * External use
     */
    public void setDrawable() {
        if (skipNextApply()) return;

        resetTintResource(0);
        setSkipNextApply(false);
    }

    public void setDrawableId(int resId) {
        if (mResId != resId) {
            resetTintResource(resId);

            if (resId != 0) {
                Drawable drawable = mTintManager.getDrawable(resId);
                setDrawable(drawable != null
                        ? drawable
                        : ContextCompat.getDrawable(mSwitchCompat.getContext(), resId));
            }
        }
    }

    public void setDrawableTintList(ColorStateList origin) {
        ColorStateList tint = ThemeUtils.getThemeColorStateList(mSwitchCompat.getContext(), origin);
        if (mTintInfo == null) {
            mTintInfo = new TintInfo();
        }
        mTintInfo.mHasTintList = true;
        mTintInfo.mTintList = tint;
        applySupportDrawableTint();
    }

    public void setDrawableTintMode(PorterDuff.Mode mode) {
        if (mode != null && mode != mTintMode) {
            if (mTintInfo != null) {
                mTintInfo.mHasTintList = false;
                mTintInfo.mTintList = null;
            }
            setSupportDrawableTintMode(mode);
            setSupportDrawableTint(mTintResId);
        }
    }

    public void setButtonDrawableTintList(int resId, PorterDuff.Mode mode) {
        if (mTintResId != resId) {
            mTintResId = resId;
            if (mTintInfo != null) {
                mTintInfo.mHasTintList = false;
                mTintInfo.mTintList = null;
                mTintInfo.mHasTintMode = false;
                mTintInfo.mTintMode = null;
            }
            setSupportDrawableTintMode(mode);
            setSupportDrawableTint(resId);
        }
    }

    /**
     * Internal use
     */
    private void setDrawable(Drawable drawable) {
        if (skipNextApply()) return;

        mDrawableCallback.setDrawable(drawable);
    }

    private boolean setSupportDrawableTint(int resId) {
        if (resId != 0) {
            if (mTintInfo == null) {
                mTintInfo = new TintInfo();
            }
            mTintInfo.mHasTintList = true;
            mTintInfo.mTintList = mTintManager.getColorStateList(resId);
        }
        return applySupportDrawableTint();
    }

    private void setSupportDrawableTintMode(PorterDuff.Mode mode) {
        if (mode != null) {
            if (mTintInfo == null) {
                mTintInfo = new TintInfo();
            }
            mTintInfo.mHasTintMode = true;
            mTintInfo.mTintMode = mode;
        }
    }

    private boolean applySupportDrawableTint() {
        Drawable drawable = mDrawableCallback.getDrawable();
        if (drawable != null && mTintInfo != null && mTintInfo.mHasTintList) {
            Drawable tintDrawable = drawable.mutate();
            tintDrawable = DrawableCompat.wrap(tintDrawable);
            if (mTintInfo.mHasTintList) {
                DrawableCompat.setTintList(tintDrawable, mTintInfo.mTintList);
            }
            if (mTintInfo.mHasTintMode) {
                DrawableCompat.setTintMode(tintDrawable, mTintInfo.mTintMode);
            }
            if (tintDrawable.isStateful()) {
                tintDrawable.setState(mSwitchCompat.getDrawableState());
            }
            setDrawable(tintDrawable);
            if (drawable == tintDrawable) {
                tintDrawable.invalidateSelf();
            }
            return true;
        }
        return false;
    }

    private void resetTintResource(int resId/*background resource id*/) {
        mResId = resId;
        mTintResId = 0;
        mTintMode = null;
        if (mTintInfo != null) {
            mTintInfo.mHasTintList = false;
            mTintInfo.mTintList = null;
            mTintInfo.mHasTintMode = false;
            mTintInfo.mTintMode = null;
        }
    }

    public void tint() {
        if (mTintResId == 0 || !setSupportDrawableTint(mTintResId)) {
            Drawable drawable = mTintManager.getDrawable(mResId);
            if (drawable == null) {
                drawable = mResId == 0 ? null : ContextCompat.getDrawable(mSwitchCompat.getContext(), mResId);
            }
            setDrawable(drawable);
        }
    }

    public interface DrawableCallback {

        void setDrawable(Drawable drawable);

        Drawable getDrawable();
    }

    public interface SwitchCompatExtensible {

        void setTrackTintList(int resId);

        void setTrackTintList(int resId, PorterDuff.Mode mode);

        void setThumbTintList(int resId);

        void setThumbTintList(int resId, PorterDuff.Mode mode);
    }
}
