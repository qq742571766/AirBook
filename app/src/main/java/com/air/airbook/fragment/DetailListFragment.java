package com.air.airbook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.air.airbook.R;
import com.air.airbook.activity.BookListActivity;
import com.air.airbook.data.List_Emp;
import com.air.airbook.view.MyActionBar;
import com.google.zxing.client.android.utils.SocketModuleUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailListFragment extends Fragment implements View.OnClickListener {
    private View view;
    private List_Emp emp;
    private TextView tv_writer;
    private TextView tv_press;
    private TextView tv_remark;
    private TextView tv_status;
    private TextView tv_bookname;
    private MyListFragment fragment;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                JSONObject jsonObject = (JSONObject) msg.obj;
                try {
                    tv_bookname.setText(jsonObject.getString("bookname"));
                    tv_writer.setText(jsonObject.getString("writer"));
                    tv_press.setText(jsonObject.getString("press"));
                    tv_remark.setText(jsonObject.getString("remark"));
                    tv_status.setText(jsonObject.getString("lender"));
                    tv_time.setText(jsonObject.getString("lendtime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    };
    private MyActionBar ab;
    private TextView tv_time;

    @Override
    public void onAttach(Context context) {
        emp = getArguments().getParcelable("emp");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detaillist, null);
        ab = getActivity().findViewById(R.id.actionbar);
        ab.findViewById(R.id.iv_actionbar_right).setVisibility(View.INVISIBLE);
        ab.findViewById(R.id.iv_actionbar_left).setVisibility(View.INVISIBLE);
        initdata();
        initView();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ab = getActivity().findViewById(R.id.actionbar);
        ab.findViewById(R.id.iv_actionbar_right).setVisibility(View.VISIBLE);
        ab.findViewById(R.id.iv_actionbar_left).setVisibility(View.VISIBLE);
    }

    private void initdata() {
        SocketModuleUtils utils = new SocketModuleUtils("{\"act\":\"getBookInfoService_2\",\"barcode\":\"" + emp.getBarcode() + "\"}");
        utils.setOnButtonClickListener(new SocketModuleUtils.OnButtonClickListener() {
            @Override
            public void OnButtonClick(String file) {
                if (file != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(file);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = jsonObject;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initView() {
        tv_bookname = view.findViewById(R.id.tv_bookname);
        tv_writer = view.findViewById(R.id.tv_writer);
        tv_press = view.findViewById(R.id.tv_press);
        tv_remark = view.findViewById(R.id.tv_remark);
        tv_status = view.findViewById(R.id.tv_status);
        tv_time = view.findViewById(R.id.tv_time);
        Button btn_return = view.findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                if (fragment == null) {
                    fragment = new MyListFragment();
                }
                ab = getActivity().findViewById(R.id.actionbar);
                ab.findViewById(R.id.iv_actionbar_right).setVisibility(View.VISIBLE);
                ab.findViewById(R.id.iv_actionbar_left).setVisibility(View.VISIBLE);
                ((BookListActivity) getActivity()).changFragment(fragment);
                break;
        }
    }
}
