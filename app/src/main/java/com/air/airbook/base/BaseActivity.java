package com.air.airbook.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.air.airbook.R;
import com.air.airbook.view.MyActionBar;

public class BaseActivity extends AppCompatActivity {
    protected void initActionBar(int leftResId, String title, int rightResId, View.OnClickListener l) {
        MyActionBar ab = (MyActionBar) findViewById(R.id.actionbar);
        ab.initActionbar(leftResId, title, rightResId, l);
    }

    protected void initTitle(String title) {
        MyActionBar ab = (MyActionBar) findViewById(R.id.actionbar);
        ab.initTitle(title);
    }
}
