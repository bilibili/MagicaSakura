package com.bilibili.magicasakura.utils;

import androidx.annotation.RestrictTo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

/**
 * just used for fix <pre>java.lang.IndexOutOfBoundsException</pre> while
 * invoke {@link InputConnection#setSelection(int, int)}
 * @author Yann Chou
 * @email zhouyanbin1029@gmail.com
 * @create 2016-08-29 11:37
 */

@RestrictTo(LIBRARY)
public final class InputConnectionImpl extends InputConnectionWrapper {

    public InputConnectionImpl(InputConnection target, boolean mutable) {
        super(target, mutable);
    }
    @Override
    public boolean setSelection(int start, int end) {
        if (start < 0 || end < 0) {
            // If the given selection is out of bounds, just ignore it.
            // Most likely the text was changed out from under the IME,
            // and the IME is going to have to update all of its state
            // anyway.
            return true;
        }
        return super.setSelection(start, end);
    }
}
