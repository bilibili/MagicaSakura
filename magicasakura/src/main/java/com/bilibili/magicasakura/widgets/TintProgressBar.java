package com.bilibili.magicasakura.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @time 16/2/4
 */
public class TintProgressBar extends ProgressBar implements Tintable {
    private AppCompatProgressBarHelper mProgressBarHelper;

    public TintProgressBar(Context context) {
        this(context, null);
    }

    public TintProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.progressBarStyle);
    }

    public TintProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager tintManage = TintManager.get(context);

        mProgressBarHelper = new AppCompatProgressBarHelper(this, tintManage);
        mProgressBarHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    public void tint() {
        if (mProgressBarHelper != null) {
            mProgressBarHelper.tint();
        }
    }
}
