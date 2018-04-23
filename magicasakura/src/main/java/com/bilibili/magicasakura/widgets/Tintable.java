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

import android.content.Context;

/**
 * Created by xyczero on 15/9/6.
 * Email : xyczero@sina.com
 *
 * Developers can implement this interface, the view that implement it will be refreshed when calling
 * {@link com.bilibili.magicasakura.utils.ThemeUtils#refreshUI(Context)}
 */
public interface Tintable {
    void tint();
}
