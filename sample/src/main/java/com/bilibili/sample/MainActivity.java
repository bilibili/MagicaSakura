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

package com.bilibili.sample;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.sample.dialog.CardPickerDialog;
import com.bilibili.sample.dialog.ProgressCheckDialog;
import com.bilibili.sample.dialog.ProgressStyleDialog;
import com.bilibili.sample.utils.SnackAnimationUtil;
import com.bilibili.sample.utils.ThemeHelper;
import com.bilibili.sample.widgets.KeyEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements CardPickerDialog.ClickListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        assert recyclerView != null;
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                final int padding = getResources().getDimensionPixelOffset(R.dimen.padding_half);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                final int position = layoutParams.getViewLayoutPosition();
                if (position == 0) {
                    outRect.left = outRect.top = outRect.right = padding;
                    outRect.bottom = padding >> 1;
                } else if (position == state.getItemCount() - 1) {
                    outRect.left = outRect.bottom = outRect.right = padding;
                    outRect.top = padding >> 1;
                } else {
                    outRect.left = outRect.right = padding;
                    outRect.top = outRect.bottom = padding >> 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        adapter.addViewHolderType(
                ViewHolder.VIEW_HOLDER_HEADER,
                ViewHolder.VIEW_HOLDER_LABEL,
                ViewHolder.VIEW_HOLDER_HEADER,
                ViewHolder.VIEW_HOLDER_LOGIN,
                ViewHolder.VIEW_HOLDER_HEADER,
                ViewHolder.VIEW_HOLDER_DOWNLOAD
        );
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_theme) {
            CardPickerDialog dialog = new CardPickerDialog();
            dialog.setClickListener(this);
            dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
            ThemeHelper.setTheme(MainActivity.this, currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                final MainActivity context = MainActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary_dark));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                            //TODO: will do this for each traversal
                        }
                    }
            );
            View view = findViewById(R.id.snack_layout);
            if (view != null) {
                TextView textView = (TextView) view.findViewById(R.id.content);
                textView.setText(getSnackContent(currentTheme));
                SnackAnimationUtil.with(this, R.anim.snack_in, R.anim.snack_out)
                        .setDismissDelayTime(1000)
                        .setTarget(view)
                        .play();
            }
        }
    }

    private String getSnackContent(int current){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return getResources().getString(getResources().getIdentifier("magicasrkura_prompt_" + random.nextInt(3), "string", getPackageName())) + ThemeHelper.getName(current);
    }

    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        List<Integer> viewHolderTypes = new ArrayList<>();
        SparseArrayCompat<Integer> titleIndexs = new SparseArrayCompat<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolder.create(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof ViewHolderHeader) {
                ((ViewHolderHeader) holder).setTitle(titleIndexs.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return viewHolderTypes.size();
        }

        @Override
        public int getItemViewType(int position) {
            return viewHolderTypes.get(position);
        }

        public void addViewHolderType(int... type) {
            for (int i = 0; i < type.length; i++) {
                if (type[i] == ViewHolder.VIEW_HOLDER_HEADER) {
                    titleIndexs.put(i, titleIndexs.size() + 1);
                }
                viewHolderTypes.add(type[i]);
            }
            notifyDataSetChanged();
        }
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public static final int VIEW_HOLDER_HEADER = 0;
        public static final int VIEW_HOLDER_LABEL = VIEW_HOLDER_HEADER + 1;
        public static final int VIEW_HOLDER_LOGIN = VIEW_HOLDER_LABEL + 1;
        public static final int VIEW_HOLDER_DOWNLOAD = VIEW_HOLDER_LOGIN + 1;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public static ViewHolder create(ViewGroup viewHolder, int type) {
            switch (type) {
                case VIEW_HOLDER_HEADER:
                    return ViewHolderHeader.create(viewHolder);
                case VIEW_HOLDER_LABEL:
                    return ViewHolderLabel.create(viewHolder);
                case VIEW_HOLDER_LOGIN:
                    return ViewHolderLogin.create(viewHolder);
                case VIEW_HOLDER_DOWNLOAD:
                    return ViewHolderChoice.create(viewHolder);
                default:
                    return null;
            }
        }
    }

    public static class ViewHolderHeader extends ViewHolder {
        private static final String[] sTitles = new String[]{"Label", "Login", "Choice"};
        TintImageView icon;
        TextView title;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            icon = (TintImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void setTitle(int index) {
            title.setText(sTitles[index - 1]);
            icon.setImageResource(itemView.getResources().getIdentifier("ic_looks_" + index, "drawable", itemView.getContext().getPackageName()));
            icon.setImageTintList(R.color.theme_color_primary);
        }

        public static ViewHolderHeader create(ViewGroup parent) {
            return new ViewHolderHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_header, parent, false));
        }
    }

    public static class ViewHolderLabel extends ViewHolder {
        TextView textView;

        public ViewHolderLabel(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.prompt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelected = v.getTag() != null && (boolean) v.getTag();
                    v.setTag(!isSelected);
                    textView.setText(isSelected ? R.string.textview_click_before : R.string.textview_click_after);
                }
            });
        }

        public static ViewHolderLabel create(ViewGroup parent) {
            return new ViewHolderLabel(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_label, parent, false));
        }
    }

    public static class ViewHolderLogin extends ViewHolder implements KeyEditText.KeyPreImeListener {
        View loginLayout;
        KeyEditText name;
        KeyEditText password;
        Button loginBtn;

        public ViewHolderLogin(View itemView) {
            super(itemView);
            loginLayout = itemView.findViewById(R.id.login_layout);
            name = (KeyEditText) itemView.findViewById(R.id.username);
            password = (KeyEditText) itemView.findViewById(R.id.password);
            loginBtn = (Button) itemView.findViewById(R.id.login_btn);
            name.setKeyPreImeListener(this);
            password.setKeyPreImeListener(this);
            name.addTextChangedListener(textWatcher);
            password.addTextChangedListener(textWatcher);
        }

        private void enableLoginBtn() {
            loginBtn.setEnabled(name.getText().length() != 0 && password.getText().length() != 0);
        }

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableLoginBtn();
            }
        };

        @Override
        public void onKeyPreImeUp(int keyCode, KeyEvent event) {
            name.clearFocus();
            password.clearFocus();
        }

        public static ViewHolderLogin create(ViewGroup parent) {
            return new ViewHolderLogin(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_login, parent, false));
        }
    }

    public static class ViewHolderChoice extends ViewHolder {
        TextView textView1;
        TextView textView2;

        public ViewHolderChoice(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.progress_setting);
            textView2 = (TextView) itemView.findViewById(R.id.download);
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = ThemeUtils.getWrapperActivity(v.getContext());
                    if (activity instanceof AppCompatActivity) {
                        new ProgressStyleDialog().show(((AppCompatActivity) activity).getSupportFragmentManager(), ProgressStyleDialog.TAG);
                    }
                }
            });

            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = ThemeUtils.getWrapperActivity(v.getContext());
                    if (activity instanceof AppCompatActivity) {
                        new ProgressCheckDialog().show(((AppCompatActivity) activity).getSupportFragmentManager(), ProgressCheckDialog.TAG);
                    }
                }
            });
        }

        public static ViewHolderChoice create(ViewGroup parent) {
            return new ViewHolderChoice(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_choice, parent, false));
        }
    }
}
