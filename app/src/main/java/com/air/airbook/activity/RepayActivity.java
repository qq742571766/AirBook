package com.air.airbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.air.airbook.R;
import com.air.airbook.base.BaseActivity;
import com.mwf.fuzzyquery.utils.SocketModuleUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RepayActivity extends BaseActivity implements View.OnClickListener {
    private TextView bookname;
    private TextView writer;
    private TextView press;
    private TextView remark;
    private TextView status;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay);
        initActionBar(R.drawable.air_back, "还书信息", -1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_actionbar_left:
                        Intent intent = new Intent(RepayActivity.this, MainActivity.class);
                        //在Intent对象当中添加一个键值对
                        startActivity(intent);
                        RepayActivity.this.finish();
                        break;
                    case R.id.iv_actionbar_right:
                        break;
                }
            }
        });
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String file = intent.getStringExtra("file");
        if (file != null) {
            try {
                jsonObject = new JSONObject(file);
                bookname.setText(jsonObject.getString("bookname"));
                writer.setText(jsonObject.getString("writer"));
                press.setText(jsonObject.getString("press"));
                remark.setText(jsonObject.getString("remark"));
                status.setText(jsonObject.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        bookname = (TextView) findViewById(R.id.tv_bookname);
        writer = (TextView) findViewById(R.id.writer);
        press = (TextView) findViewById(R.id.press);
        remark = (TextView) findViewById(R.id.remark);
        status = (TextView) findViewById(R.id.tv_status);
        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String data = null;
        try {
            data = "{\"act\":\"returnBookService\"," + "\"barcode\":\"" + jsonObject.getString("barcode") + "\"}";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SocketModuleUtils utils = new SocketModuleUtils(data);
        utils.setOnButtonClickListener(new SocketModuleUtils.OnButtonClickListener() {
            @Override
            public void OnButtonClick(String file) {
                if (file.equals("1")) {
                    Intent intent = new Intent(RepayActivity.this, GiveBackActivity.class);
                    startActivity(intent);
                } else if (file.equals("2")) {
                    Toast.makeText(RepayActivity.this, "借书记录不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(RepayActivity.this, MainActivity.class);
                //在Intent对象当中添加一个键值对
                startActivity(intent);
                RepayActivity.this.finish();
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}