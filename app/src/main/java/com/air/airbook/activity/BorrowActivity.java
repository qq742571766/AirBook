package com.air.airbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.air.airbook.R;
import com.air.airbook.base.BaseActivity;
import com.mwf.fuzzyquery.ChoosePlateActivity;
import com.mwf.fuzzyquery.utils.SocketModuleUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class BorrowActivity extends BaseActivity implements View.OnClickListener {
    private TextView bookname;
    private TextView writer;
    private TextView press;
    private TextView remark;
    private TextView status;
    private String empService;
    private SocketModuleUtils utils;
    private JSONObject jsonObject;
    private Button ev_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        initActionBar(R.drawable.air_back, "借书信息", -1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_actionbar_left:
                        Intent intent = new Intent(BorrowActivity.this, MainActivity.class);
                        //在Intent对象当中添加一个键值对
                        startActivity(intent);
                        BorrowActivity.this.finish();
                        break;
                    case R.id.iv_actionbar_right:
                        break;
                }
            }
        });
        utils = new SocketModuleUtils("{\"act\":\"getEmpService\"}");
        utils.setOnButtonClickListener(new SocketModuleUtils.OnButtonClickListener() {
            @Override
            public void OnButtonClick(String file) {
                empService = file;
            }
        });
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ChoosePlateActivity.bean != null && ChoosePlateActivity.bean.length() > 0) {
            String empname = ChoosePlateActivity.bean.split(",")[0];
            ev_query.setText(empname);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ev_query.setText("点击查询员工信息");
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
        ev_query = (Button) findViewById(R.id.ev_query);
        ev_query.setOnClickListener(this);
        LinearLayout rl_inquire = (LinearLayout) findViewById(R.id.rl_inquire);
        rl_inquire.setOnClickListener(this);
        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        ev_query.setText("点击查询员工信息");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ev_query:
            case R.id.rl_inquire:
                if (empService != null) {
                    Intent intent = new Intent(BorrowActivity.this, ChoosePlateActivity.class);
                    intent.putExtra("empService", empService);
                    startActivity(intent);
                }
                break;

            case R.id.btn_ok:
                if (!ev_query.getText().toString().equals("点击查询员工信息")) {
                    try {
                        if (jsonObject != null && jsonObject.length() > 0 && !jsonObject.getString("status").equals("已借出")) {
                            try {
                                String data = "{\"act\":\"leadBookService\"," + "\"barcode\":\"" +
                                        jsonObject.getString("barcode") + "\"," + "\"qrcode\":\"" +
                                        ChoosePlateActivity.bean.split(",")[2] + "\"}";
                                utils = new SocketModuleUtils(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            utils.setOnButtonClickListener(new SocketModuleUtils.OnButtonClickListener() {
                                @Override
                                public void OnButtonClick(String file) {
                                    switch (file) {
                                        case "1":
                                            Toast.makeText(BorrowActivity.this, "借阅数据达到上限达到5本", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "":
                                            Toast.makeText(BorrowActivity.this, "借阅人不存在", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "3":
                                            Intent intent = new Intent(BorrowActivity.this, SuccessComesActivity.class);
                                            startActivity(intent);
                                            BorrowActivity.this.finish();
                                            break;
                                    }
                                }
                            });
                        } else {
                            if (jsonObject != null) {
                                Toast.makeText(this, "此书已借出...借阅人:" + jsonObject.getString("lender"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        ChoosePlateActivity.bean = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(BorrowActivity.this, MainActivity.class);
                //在Intent对象当中添加一个键值对
                startActivity(intent);
                BorrowActivity.this.finish();
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}