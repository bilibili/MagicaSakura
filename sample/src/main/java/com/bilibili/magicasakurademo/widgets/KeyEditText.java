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

package com.bilibili.magicasakurademo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.bilibili.magicasakura.widgets.TintEditText;

/**
 * @author xyczero
 * @time 16/5/23
 */
public class KeyEditText extends TintEditText {
    private KeyPreImeListener mKeyPreImeListener;

    public KeyEditText(Context context) {
        super(context);
    }

    public KeyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (mKeyPreImeListener != null) {
                    mKeyPreImeListener.onKeyPreImeUp(keyCode, event);
                }
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setKeyPreImeListener(KeyPreImeListener listener) {
        mKeyPreImeListener = listener;
    }

    public interface KeyPreImeListener {
        void onKeyPreImeUp(int keyCode, KeyEvent event);
    }
}
