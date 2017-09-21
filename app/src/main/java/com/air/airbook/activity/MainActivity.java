package com.air.airbook.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.air.airbook.R;
import com.air.airbook.base.BaseActivity;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    private String on_off;
    private long firstTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置ActionBar
        initActionBar(-1, "首页", -1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_actionbar_left:
                        break;
                    case R.id.iv_actionbar_right:
                        break;
                }
            }
        });
        LinearLayout l = (LinearLayout) this.findViewById(R.id.l);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                on_off = "borrow";
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
        LinearLayout l2 = (LinearLayout) this.findViewById(R.id.l2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                on_off = "repay";
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
        LinearLayout l3 = (LinearLayout) this.findViewById(R.id.l3);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(MainActivity.this, BookListActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String files = bundle.getString("file");
            if (files != null && files.length() > 0) {
                if (on_off.equals("borrow")) {
                    Intent intent = new Intent(MainActivity.this, BorrowActivity.class);
                    try {
                        JSONObject jsonObject = new JSONObject(files);
                        if (jsonObject.getString("lender").equals("无")) {
                            intent.putExtra("file", files);
                            startActivity(intent);
                        } else {
                            new AlertDialog.Builder(this)
                                    .setTitle("此书已由" + jsonObject.getString("lender") + "借阅")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //在Intent对象当中添加一个键值对
                }
                if (on_off.equals("repay")) {
                    Intent intent = new Intent(MainActivity.this, RepayActivity.class);
                    try {
                        JSONObject jsonObject = new JSONObject(files);
                        if (!jsonObject.getString("lender").equals("无")) {
                            intent.putExtra("file", files);
                            startActivity(intent);
                        } else {
                            new AlertDialog.Builder(this)
                                    .setTitle("此书已无人借阅")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //在Intent对象当中添加一个键值对
                }
            } else {
                Toast.makeText(MainActivity.this, "您所查询的书籍不存在...", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}