package com.air.airbook.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.air.airbook.R;
import com.air.airbook.base.BaseActivity;
import com.air.airbook.fragment.MyListFragment;

public class BookListActivity extends BaseActivity {
    private DrawerLayout activity_function;
    private NavigationView navigationView;
    private String category;
    private MyListFragment fragment;
    private String tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        initView();
        //加入fragment
        if (fragment == null) {
            fragment = new MyListFragment();
        }
        changFragment(fragment);
        initTitle("借书信息 - 已借出");
//        fragment.initdata("已借出", "");
        initNavigation();
    }

    private void initView() {
        activity_function = (DrawerLayout) findViewById(R.id.activity_function);
        navigationView = (NavigationView) findViewById(R.id.nav);
        initActionBar(R.drawable.air_back, "借书信息", R.drawable.air_search, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_actionbar_left:
                        BookListActivity.this.finish();
                        break;
                    case R.id.iv_actionbar_right:
                        activity_function.openDrawer(Gravity.RIGHT);
                        break;
                }
            }
        });
        View headerLayout = navigationView.getHeaderView(0);
        final TextView tv1 = headerLayout.findViewById(R.id.tv1);
        final TextView tv2 = headerLayout.findViewById(R.id.tv2);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv = "已借出";
                fragment.initdata("已借出", "");
                initTitle("借书信息 - 已借出");
                tv1.setTextColor(Color.parseColor("#FFFFFF"));
                tv2.setTextColor(Color.parseColor("#FC9800"));
                tv1.setBackgroundColor(Color.parseColor("#FC9800"));
                tv2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv = "未借出";
                fragment.initdata("未借出", "");
                initTitle("借书信息 - 未借出");
                tv1.setTextColor(Color.parseColor("#FC9800"));
                tv2.setTextColor(Color.parseColor("#FFFFFF"));
                tv1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv2.setBackgroundColor(Color.parseColor("#FC9800"));
            }
        });
    }

    //切换fragment
    public void changFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_commcontent_main, fragment);
        transaction.commit();
    }

    //Navigation点击
    private void initNavigation() {
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cwgl:
                        category = "财务管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.cggl:
                        category = "采购管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.ccgl:
                        category = "仓储管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.cqxg:
                        category = "产品相关";
                        activity_function.closeDrawers();
                        break;
                    case R.id.jxzz:
                        category = "机械制造";
                        activity_function.closeDrawers();
                        break;
                    case R.id.lcgl:
                        category = "流程管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.qygl:
                        category = "企业管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.rlzy:
                        category = "人力资源";
                        activity_function.closeDrawers();
                        break;
                    case R.id.yjxg:
                        category = "软件相关";
                        activity_function.closeDrawers();
                        break;
                    case R.id.scgl:
                        category = "生产管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.zlgl:
                        category = "质量管理";
                        activity_function.closeDrawers();
                        break;
                    case R.id.qywh:
                        category = "企业文化";
                        activity_function.closeDrawers();
                        break;
                    case R.id.glzd:
                        category = "管理制度";
                        activity_function.closeDrawers();
                        break;
                    case R.id.qa:
                        category = "全案";
                        activity_function.closeDrawers();
                        break;
                    case R.id.qt:
                        category = "其它";
                        activity_function.closeDrawers();
                        break;
                }
                if (tv != null) {
                    initTitle("借书信息 - " + category + " - " + tv);
                } else {
                    initTitle("借书信息 - " + category);
                }
                fragment.initdata(tv, category);
                return true;
            }
        });
    }
}
