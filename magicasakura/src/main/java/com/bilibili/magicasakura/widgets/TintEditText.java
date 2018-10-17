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

package com.bilibili.magicasakura.widgets;

import android.annotation.TargetApi;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.bilibili.magicasakura.utils.InputConnectionImpl;
import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @time 16/2/1
 */
@SuppressLint("AppCompatCustomView")
public class TintEditText extends EditText implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible,
        AppCompatCompoundDrawableHelper.CompoundDrawableExtensible, AppCompatTextHelper.TextExtensible {
    private AppCompatBackgroundHelper mBackgroundHelper;
    private AppCompatCompoundDrawableHelper mCompoundDrawableHelper;
    private AppCompatTextHelper mTextHelper;

    public TintEditText(Context context) {
        this(context, null);
    }

    public TintEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public TintEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager tintManager = TintManager.get(getContext());

        mTextHelper = new AppCompatTextHelper(this, tintManager);
        mTextHelper.loadFromAttribute(attrs, defStyleAttr);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);

        mCompoundDrawableHelper = new AppCompatCompoundDrawableHelper(this, tintManager);
        mCompoundDrawableHelper.loadFromAttribute(attrs, defStyleAttr);
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
    public void setBackground(Drawable background) {
        super.setBackground(background);
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
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundColor(color);
        } else {
            super.setBackgroundColor(color);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else {
            super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.setCompoundDrawablesWithIntrinsicBounds();
        }
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
    public void setCompoundDrawableTintList(int leftResId, int topResId, int rightResId, int bottomResId) {
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.setCompoundDrawablesTintList(leftResId, topResId, rightResId, bottomResId);
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
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
        if (mCompoundDrawableHelper != null) {
            mCompoundDrawableHelper.tint();
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        if (conn != null) {
            return new InputConnectionImpl(conn, false);
        }
        return null;
    }
}
