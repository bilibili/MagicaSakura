package com.bilibili.magicasakura.utils;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

import java.util.LinkedList;

/**
 * @author xyczero617@gmail.com
 * @time 15/11/21
 */

public class TintInfo {
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;
    public boolean mHasTintMode;
    public boolean mHasTintList;

    int[] mTintColors;
    int[][] mTintStates;

    public TintInfo() {

    }

    public TintInfo(LinkedList<int[]> stateList, LinkedList<Integer> colorList) {
        if (colorList == null || stateList == null) return;

        mTintColors = new int[colorList.size()];
        for (int i = 0; i < colorList.size(); i++)
            mTintColors[i] = colorList.get(i);
        mTintStates = stateList.toArray(new int[stateList.size()][]);
    }

    public boolean isInvalid() {
        return mTintColors == null || mTintStates == null || mTintColors.length != mTintStates.length;
    }
}
