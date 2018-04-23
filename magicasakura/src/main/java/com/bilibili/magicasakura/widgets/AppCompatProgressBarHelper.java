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

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bilibili.magicasakura.R;
import com.bilibili.magicasakura.utils.TintInfo;
import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @time 16/2/4
 */
class AppCompatProgressBarHelper extends AppCompatBaseHelper<TintProgressBar> {

    private int mProgressTintResId;
    private int mIndeterminateTintResId;

    private TintInfo mProgressTintInfo;
    private TintInfo mIndeterminateTintInfo;

    AppCompatProgressBarHelper(TintProgressBar view, TintManager tintManager) {
        super(view, tintManager);
    }

    @SuppressWarnings("ResourceType")
    @Override
    void loadFromAttribute(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mView.getContext().obtainStyledAttributes(attrs, R.styleable.TintProgressBarHelper, defStyleAttr, 0);
        if (array.hasValue(R.styleable.TintProgressBarHelper_progressTint)) {
            setSupportProgressTint(mProgressTintResId = array.getResourceId(R.styleable.TintProgressBarHelper_progressTint, 0));
        }
        if (array.hasValue(R.styleable.TintProgressBarHelper_progressIndeterminateTint)) {
            setSupportIndeterminateTint(mIndeterminateTintResId = array.getResourceId(R.styleable.TintProgressBarHelper_progressIndeterminateTint, 0));
        }
        array.recycle();
    }

    private void setSupportProgressTint(int resId) {
        if (resId != 0) {
            if (mProgressTintInfo == null) {
                mProgressTintInfo = new TintInfo();
            }
            mProgressTintInfo.mHasTintList = true;
            mProgressTintInfo.mTintList = mTintManager.getColorStateList(resId);
        }
        applySupportProgressTint();
    }

    private void setSupportIndeterminateTint(int resId) {
        if (resId != 0) {
            if (mIndeterminateTintInfo == null) {
                mIndeterminateTintInfo = new TintInfo();
            }
            mIndeterminateTintInfo.mHasTintList = true;
            mIndeterminateTintInfo.mTintList = mTintManager.getColorStateList(resId);
        }
        applySupportIndeterminateTint();
    }

    private void applySupportProgressTint() {
        if (mProgressTintInfo != null
                && (mProgressTintInfo.mHasTintList || mProgressTintInfo.mHasTintMode)) {
            final Drawable target = getTintTarget(android.R.id.progress, true);
            if (target != null) {
                TintManager.tintViewDrawable(mView, target, mProgressTintInfo);
                // The drawable (or one of its children) may not have been
                // stateful before applying the tint, so let's try again.
                if (target.isStateful()) {
                    target.setState(mView.getDrawableState());
                }
            }
        }
    }

    private void applySupportIndeterminateTint() {
        Drawable mIndeterminateDrawable = mView.getIndeterminateDrawable();
        if (mIndeterminateDrawable != null && mIndeterminateTintInfo != null) {
            final TintInfo tintInfo = mIndeterminateTintInfo;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                mView.setIndeterminateDrawable(mIndeterminateDrawable = mIndeterminateDrawable.mutate());
                TintManager.tintViewDrawable(mView, mIndeterminateDrawable, mIndeterminateTintInfo);
                // The drawable (or one of its children) may not have been
                // stateful before applying the tint, so let's try again.
                if (mIndeterminateDrawable.isStateful()) {
                    mIndeterminateDrawable.setState(mView.getDrawableState());
                }
            }
        }
    }

    @Nullable
    private Drawable getTintTarget(int layerId, boolean shouldFallback) {
        Drawable layer = null;

        final Drawable d = mView.getProgressDrawable();
        if (d != null) {
            mView.setProgressDrawable(d.mutate());

            if (d instanceof LayerDrawable) {
                layer = ((LayerDrawable) d).findDrawableByLayerId(layerId);
            }

            if (shouldFallback && layer == null) {
                layer = d;
            }
        }

        return layer;
    }

    @Override
    public void tint() {
        if (mProgressTintResId != 0) {
            setSupportProgressTint(mProgressTintResId);
        }
        if (mIndeterminateTintResId != 0) {
            setSupportIndeterminateTint(mIndeterminateTintResId);
        }
    }
}
