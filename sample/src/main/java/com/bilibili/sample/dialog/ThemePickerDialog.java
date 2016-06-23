package com.bilibili.sample.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bilibili.sample.R;
import com.bilibili.sample.utils.ThemeHelper;

/**
 * @author xyczero
 * @time 16/5/29
 */
public class ThemePickerDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "ThemePickerDialog";
    ImageButton[] mImageButtons = new ImageButton[8];
    Button mConfirm;
    Button mCancel;

    private int mCurrentTheme;
    private ClickListener mClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
        mCurrentTheme = ThemeHelper.getTheme(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_theme_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCancel = (Button) view.findViewById(android.R.id.button2);
        mConfirm = (Button) view.findViewById(android.R.id.button1);
        mImageButtons[0] = (ImageButton) view.findViewById(R.id.theme_pink);
        mImageButtons[1] = (ImageButton) view.findViewById(R.id.theme_purple);
        mImageButtons[2] = (ImageButton) view.findViewById(R.id.theme_blue);
        mImageButtons[3] = (ImageButton) view.findViewById(R.id.theme_green);
        mImageButtons[4] = (ImageButton) view.findViewById(R.id.theme_green_light);
        mImageButtons[5] = (ImageButton) view.findViewById(R.id.theme_yellow);
        mImageButtons[6] = (ImageButton) view.findViewById(R.id.theme_orange);
        mImageButtons[7] = (ImageButton) view.findViewById(R.id.theme_red);
        setImageButtons(mCurrentTheme);
        for (ImageButton button : mImageButtons) {
            button.setOnClickListener(this);
        }
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.button1:
                if (mClickListener != null) {
                    mClickListener.onConfirm(mCurrentTheme);
                }
            case android.R.id.button2:
                dismiss();
                break;
            case R.id.theme_pink:
                mCurrentTheme = ThemeHelper.THEME_PINK;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_purple:
                mCurrentTheme = ThemeHelper.THEME_PURPLE;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_blue:
                mCurrentTheme = ThemeHelper.THEME_BLUE;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green:
                mCurrentTheme = ThemeHelper.THEME_GREEN;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green_light:
                mCurrentTheme = ThemeHelper.THEME_GREEN_LIGHT;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_yellow:
                mCurrentTheme = ThemeHelper.THEME_YELLOW;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_orange:
                mCurrentTheme = ThemeHelper.THEME_ORANGE;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_red:
                mCurrentTheme = ThemeHelper.THEME_RED;
                setImageButtons(mCurrentTheme);
                break;
            default:
                break;
        }
    }

    private void setImageButtons(int currentTheme) {
        mImageButtons[0].setSelected(currentTheme == ThemeHelper.THEME_PINK);
        mImageButtons[1].setSelected(currentTheme == ThemeHelper.THEME_PURPLE);
        mImageButtons[2].setSelected(currentTheme == ThemeHelper.THEME_BLUE);
        mImageButtons[3].setSelected(currentTheme == ThemeHelper.THEME_GREEN);
        mImageButtons[4].setSelected(currentTheme == ThemeHelper.THEME_GREEN_LIGHT);
        mImageButtons[5].setSelected(currentTheme == ThemeHelper.THEME_YELLOW);
        mImageButtons[6].setSelected(currentTheme == ThemeHelper.THEME_ORANGE);
        mImageButtons[7].setSelected(currentTheme == ThemeHelper.THEME_RED);
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onConfirm(int currentTheme);
    }
}
