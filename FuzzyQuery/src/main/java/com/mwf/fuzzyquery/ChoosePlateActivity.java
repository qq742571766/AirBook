package com.mwf.fuzzyquery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mwf.fuzzyquery.fakesearchview.FakeSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ChoosePlateActivity extends Activity implements FakeSearchView.OnSearchListener, View.OnClickListener {
    private CarAdapter mCarAdapter;
    private InputMethodManager inputMethodManager;
    private EditText mEditText;
    private ListView cars;
    private FakeSearchView fakeSearchView;
    private TextView tv_cancel;
    private LinearLayout activity_main;
    private String emp;
    private ArrayList<List_Emp> empService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_plate);
        Intent intent = getIntent();
        emp = (String) intent.getStringExtra("empService");
        Gson gson = new Gson();
        empService = gson.fromJson(emp, new TypeToken<ArrayList<List_Emp>>() {
        }.getType());
        ButterKnife.bind(this);
        //初始化键盘
        cars = (ListView) findViewById(R.id.car_list);
        fakeSearchView = (FakeSearchView) findViewById(R.id.fakeSearchView);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        fakeSearchView.setOnSearchListener(this);
        mEditText = fakeSearchView.getSearch();
        SoftKeyBoardListener.setListener(ChoosePlateActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mEditText.requestFocus();
            }

            @Override
            public void keyBoardHide(int height) {
                mEditText.clearFocus();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mCarAdapter = new CarAdapter(this, getCars());
        cars.setAdapter(mCarAdapter);
        mCarAdapter.setOnViewClickListener(mViewClickListener);

    }

    public static String bean;
    CarAdapter.OnViewClickListener mViewClickListener = new CarAdapter.OnViewClickListener() {
        @Override
        public void OnItemClick(View view, VehDataBean bean) {
            //隐藏键盘
            inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ChoosePlateActivity.bean = bean.getVehnumber();
            finish();
        }

    };

    @Override
    public void onSearch(FakeSearchView fakeSearchView, CharSequence constraint) {
        ((CarAdapter) cars.getAdapter()).getFilter().filter(constraint);
    }

    @Override
    public void onSearchHint(FakeSearchView fakeSearchView, CharSequence constraint) {
        ((CarAdapter) cars.getAdapter()).getFilter().filter(constraint);
    }

    List<VehDataBean> getCars() {
        List<VehDataBean> cars = new ArrayList<>();
        for (int i = 0; i < empService.size(); i++) {
            cars.add(new VehDataBean(empService.get(i).getEmpname()+","+empService.get(i).getPhonetic()+","+empService.get(i).getQrcode(),empService.get(i).getEmpname()));
        }
        return cars;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_cancel) {//隐藏键盘
            inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        //隐藏键盘
        inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        super.onDestroy();
    }
}