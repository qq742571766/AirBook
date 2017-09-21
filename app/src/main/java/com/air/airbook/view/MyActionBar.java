package com.air.airbook.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.air.airbook.R;

public class MyActionBar extends LinearLayout {
    private final ImageView iv_actionbar_left;
    private final ImageView iv_actionbar_right;
    private final TextView tv_actionbar;

    public MyActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_actionbar, this);
        iv_actionbar_left = findViewById(R.id.iv_actionbar_left);
        iv_actionbar_right = findViewById(R.id.iv_actionbar_right);
        tv_actionbar = findViewById(R.id.tv_actionbar);
    }

    public void initTitle(String title) {
        tv_actionbar.setText(title);
    }

    public void initActionbar(int leftResId, String title, int rightResId, OnClickListener l) {
        tv_actionbar.setText(title);
        if (leftResId == -1) {
            iv_actionbar_left.setVisibility(View.INVISIBLE);
        } else {
            iv_actionbar_left.setImageResource(leftResId);
            iv_actionbar_left.setOnClickListener(l);
        }
        if (rightResId == -1) {
            iv_actionbar_right.setVisibility(View.INVISIBLE);
        } else {
            iv_actionbar_right.setImageResource(rightResId);
            iv_actionbar_right.setOnClickListener(l);
        }
    }
}
