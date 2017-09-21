package com.air.airbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.air.airbook.R;
import com.air.airbook.base.BaseActivity;

public class SuccessComesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_comes);
        initActionBar(R.drawable.air_back, "借书成功", -1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_actionbar_left:
                        Intent intent = new Intent(SuccessComesActivity.this, MainActivity.class);
                        //在Intent对象当中添加一个键值对
                        startActivity(intent);
                        SuccessComesActivity.this.finish();
                        break;
                    case R.id.iv_actionbar_right:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(SuccessComesActivity.this, MainActivity.class);
                //在Intent对象当中添加一个键值对
                startActivity(intent);
                SuccessComesActivity.this.finish();
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
