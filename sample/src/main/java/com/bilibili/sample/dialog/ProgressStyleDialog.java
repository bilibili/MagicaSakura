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

package com.bilibili.sample.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.bilibili.sample.R;


/**
 * @author xyczero
 * @time 16/5/23
 */
public class ProgressStyleDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = ProgressStyleDialog.class.getSimpleName();

    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_progress_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRadioButton1 = (RadioButton) view.findViewById(R.id.progress_style_1);
        mRadioButton2 = (RadioButton) view.findViewById(R.id.progress_style_2);
        mRadioButton1.setOnClickListener(this);
        mRadioButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mRadioButton1.setChecked(v.getId() == R.id.progress_style_1);
        mRadioButton2.setChecked(v.getId() == R.id.progress_style_2);
    }
}
