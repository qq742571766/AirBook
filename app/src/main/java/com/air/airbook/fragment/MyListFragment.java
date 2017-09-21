package com.air.airbook.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.air.airbook.R;
import com.air.airbook.activity.BookListActivity;
import com.air.airbook.adapter.BookListAdapter;
import com.air.airbook.data.List_Emp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mwf.fuzzyquery.utils.SocketModuleUtils;

import java.util.ArrayList;
import java.util.List;

public class MyListFragment extends Fragment implements View.OnTouchListener {
    private View view;
    private Gson gson;
    private List_Emp emp;
    private List<List_Emp> list;
    private BookListAdapter adapter;
    private RecyclerView rv_history;
    private DetailListFragment fm;
    private ArrayList<List_Emp> list_emp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.adds(list);
                //添加适配器
                rv_history.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, null);
        view.setOnTouchListener(this);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initdata("已借出", "");
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        rv_history = view.findViewById(R.id.rv_history);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_history.setLayoutManager(manager);
        adapter = new BookListAdapter();
        adapter.setOnButtonClickListener(new BookListAdapter.OnButtonClickListener() {
            @Override
            public void OnButtonClick(List_Emp emp) {
                if (fm == null) {
                    fm = new DetailListFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("emp", emp);
                fm.setArguments(bundle);
                ((BookListActivity) getActivity()).changFragment(fm);
            }
        });
    }

    public void initdata(final String status, final String bookkind) {
        list = new ArrayList<>();
        gson = new Gson();
        SocketModuleUtils utils = new SocketModuleUtils("{\"act\":\"getBookInfoService_1\"}");
        utils.setOnButtonClickListener(new SocketModuleUtils.OnButtonClickListener() {
            @Override
            public void OnButtonClick(String file) {
                list_emp = gson.fromJson(file, new TypeToken<ArrayList<List_Emp>>() {
                }.getType());
                for (int i = 0; i < list_emp.size(); i++) {
                    emp = new List_Emp();
                    if (list_emp.get(i).getStatus().equals(status)) {
                        if (bookkind.equals("")) {
                            emp.setBookname(list_emp.get(i).getBookname());
                            emp.setBookkind(list_emp.get(i).getBookkind());
                            emp.setBarcode(list_emp.get(i).getBarcode());
                            emp.setLender(list_emp.get(i).getLender());
                            list.add(emp);
                        } else if (bookkind.equals(list_emp.get(i).getBookkind())) {
                            emp.setBookname(list_emp.get(i).getBookname());
                            emp.setBookkind(list_emp.get(i).getBookkind());
                            emp.setBarcode(list_emp.get(i).getBarcode());
                            emp.setLender(list_emp.get(i).getLender());
                            list.add(emp);
                        }
                    } else if (status == null || status.equals("")) {
                        if (bookkind.equals(list_emp.get(i).getBookkind())) {
                            emp.setBookname(list_emp.get(i).getBookname());
                            emp.setBookkind(list_emp.get(i).getBookkind());
                            emp.setBarcode(list_emp.get(i).getBarcode());
                            emp.setLender(list_emp.get(i).getLender());
                            list.add(emp);
                        }
                    }
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
