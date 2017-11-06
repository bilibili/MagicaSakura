package com.bilibili.magicasakura.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;

import com.bilibili.magicasakura.R;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @since 17/5/23
 */

public class TintSwitchCompat extends SwitchCompat implements Tintable, AppCompatTextHelper.TextExtensible,
        AppCompatBackgroundHelper.BackgroundExtensible, AppCompatSwitchHelper.SwitchCompatExtensible,
        AppCompatCompoundButtonHelper.CompoundButtonExtensible {

    private AppCompatBackgroundHelper mBackgroundHelper;
    private AppCompatCompoundButtonHelper mCompoundButtonHelper;
    private AppCompatTextHelper mTextHelper;
    private AppCompatSwitchHelper mThumbHelper;
    private AppCompatSwitchHelper mTrackHelper;

    public TintSwitchCompat(Context context) {
        this(context, null);
    }

    public TintSwitchCompat(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.switchStyle);
    }

    public TintSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager tintManager = TintManager.get(context);
        mThumbHelper = new AppCompatSwitchHelper(this, tintManager,
                R.styleable.TintSwitchThumb,
                new AppCompatSwitchHelper.DrawableCallback() {
                    @Override
                    public void setDrawable(Drawable drawable) {
                        setThumbDrawable(drawable);
                    }

                    @Override
                    public Drawable getDrawable() {
                        return getThumbDrawable();
                    }
                });
        mThumbHelper.loadFromAttribute(attrs, defStyleAttr);

        mTrackHelper = new AppCompatSwitchHelper(this, tintManager,
                R.styleable.TintSwitchTrack,
                new AppCompatSwitchHelper.DrawableCallback() {
                    @Override
                    public void setDrawable(Drawable drawable) {
                        setTrackDrawable(drawable);
                    }

                    @Override
                    public Drawable getDrawable() {
                        return getTrackDrawable();
                    }
                });
        mTrackHelper.loadFromAttribute(attrs, defStyleAttr);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);

        mCompoundButtonHelper = new AppCompatCompoundButtonHelper(this, tintManager);
        mCompoundButtonHelper.loadFromAttribute(attrs, defStyleAttr);

        mTextHelper = new AppCompatTextHelper(this, tintManager);
        mTextHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (ThemeUtils.isSkipAnimatedSelector()) {
            Drawable drawable = CompoundButtonCompat.getButtonDrawable(this);
            try {
                if (ThemeUtils.getWrapperDrawable(drawable) instanceof AnimatedStateListDrawable) {
                    drawable.jumpToCurrentState();
                }
            } catch (NoClassDefFoundError error) {
                error.printStackTrace();
            }
        }
    }

    @Override
    public void setThumbDrawable(Drawable thumb) {
        super.setThumbDrawable(thumb);
        if (mThumbHelper != null) {
            mThumbHelper.setDrawable();
        }
    }

    @Override
    public void setThumbResource(int resId) {
        if (mThumbHelper != null) {
            mThumbHelper.setDrawableId(resId);
        } else {
            super.setThumbResource(resId);
        }
    }

    @Override
    public void setThumbTintList(@Nullable ColorStateList tint) {
        if (mThumbHelper != null) {
            mThumbHelper.setDrawableTintList(tint);
        } else {
            super.setThumbTintList(tint);
        }
    }

    @Override
    public void setThumbTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mThumbHelper != null) {
            mThumbHelper.setDrawableTintMode(tintMode);
        } else {
            super.setThumbTintMode(tintMode);
        }
    }

    @Override
    public void setThumbTintList(int resId) {
        if (mThumbHelper != null) {
            mThumbHelper.setButtonDrawableTintList(resId, null);
        }
    }

    @Override
    public void setThumbTintList(int resId, PorterDuff.Mode mode) {
        if (mThumbHelper != null) {
            mThumbHelper.setButtonDrawableTintList(resId, mode);
        }
    }

    @Override
    public void setTrackDrawable(Drawable track) {
        super.setTrackDrawable(track);
        if (mTrackHelper != null) {
            mTrackHelper.setDrawable();
        }
    }

    @Override
    public void setTrackResource(int resId) {
        if (mTrackHelper != null) {
            mTrackHelper.setDrawableId(resId);
        } else {
            super.setTrackResource(resId);
        }
    }

    @Override
    public void setTrackTintList(@Nullable ColorStateList tint) {
        if (mTrackHelper != null) {
            mTrackHelper.setDrawableTintList(tint);
        } else {
            super.setTrackTintList(tint);
        }
    }

    @Override
    public void setTrackTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mTrackHelper != null) {
            mTrackHelper.setDrawableTintMode(tintMode);
        } else {
            super.setTrackTintMode(tintMode);
        }
    }

    @Override
    public void setTrackTintList(int resId) {
        if (mTrackHelper != null) {
            mTrackHelper.setButtonDrawableTintList(resId, null);
        }
    }

    @Override
    public void setTrackTintList(int resId, PorterDuff.Mode mode) {
        if (mTrackHelper != null) {
            mTrackHelper.setButtonDrawableTintList(resId, mode);
        }
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        if (mTextHelper != null) {
            mTextHelper.setTextColor();
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        if (mTextHelper != null) {
            mTextHelper.setTextColor();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
        if (mTextHelper != null) {
            mTextHelper.setTextAppearanceForTextColor(resId);
        }
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (mTextHelper != null) {
            mTextHelper.setTextAppearanceForTextColor(resId);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundDrawableExternal(background);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundResId(resId);
        } else {
            super.setBackgroundResource(resId);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundColor(color);
        }
    }

    @Nullable
    @Override
    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        if (mCompoundButtonHelper != null) {
            mCompoundButtonHelper.setButtonDrawable();
        }
    }

    @Override
    public void setButtonDrawable(@DrawableRes int resId) {
        if (mCompoundButtonHelper != null) {
            mCompoundButtonHelper.setButtonDrawable(resId);
        } else {
            super.setButtonDrawable(resId);
        }
    }

    @Override
    public int getCompoundPaddingLeft() {
        final int value = super.getCompoundPaddingLeft();
        return mCompoundButtonHelper != null
                ? mCompoundButtonHelper.getCompoundPaddingLeft(value)
                : value;
    }

    @Override
    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, mode);
        }
    }

    @Override
    public void setCompoundButtonTintList(int resId) {
        if (mCompoundButtonHelper != null) {
            mCompoundButtonHelper.setButtonDrawableTintList(resId, null);
        }
    }

    @Override
    public void setCompoundButtonTintList(int resId, PorterDuff.Mode mode) {
        if (mCompoundButtonHelper != null) {
            mCompoundButtonHelper.setButtonDrawableTintList(resId, mode);
        }
    }

    @Override
    public void setTextColorById(@ColorRes int colorId) {
        if (mTextHelper != null) {
            mTextHelper.setTextColorById(colorId);
        }
    }

    @Override
    public void tint() {
        if (mTextHelper != null) {
            mTextHelper.tint();
        }
        if (mCompoundButtonHelper != null) {
            mCompoundButtonHelper.tint();
        }
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
        if (mTrackHelper != null) {
            mTrackHelper.tint();
        }
        if (mThumbHelper != null) {
            mThumbHelper.tint();
        }
    }
}
