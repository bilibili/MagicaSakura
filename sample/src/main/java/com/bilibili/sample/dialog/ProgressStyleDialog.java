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
