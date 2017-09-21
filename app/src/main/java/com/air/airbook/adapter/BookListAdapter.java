package com.air.airbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.air.airbook.R;
import com.air.airbook.data.List_Emp;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.mViewHolder> {
    private List<List_Emp> datas;
    private OnButtonClickListener listener = null;

    public void adds(List<List_Emp> datas) {
        this.datas = new ArrayList<>();
        this.datas.addAll(datas);
    }

    @Override
    public mViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_list, null);
        final mViewHolder holder = new mViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    List_Emp emp = datas.get(position);
                    listener.OnButtonClick(emp);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        List_Emp emp = datas.get(position);
        if (emp != null) {
            holder.tv_book_name.setText(emp.getBookname());
            holder.tv_book_category.setText(emp.getBookkind());
            holder.tv_book_number.setText(emp.getBarcode());
            if (emp.getLender() != null) {
                if (emp.getLender().equals("无")) {
                    holder.tv_book_person.setText(" ");
                } else {
                    holder.tv_book_person.setText(emp.getLender());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0) {
            return 0;
        } else {
            return datas.size();
        }
    }

    public void setOnButtonClickListener(OnButtonClickListener l) {
        this.listener = l;
    }

    public interface OnButtonClickListener {
        void OnButtonClick(List_Emp emp);
    }

    static class mViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_name;
        TextView tv_book_category;
        TextView tv_book_number;
        TextView tv_book_person;
        View newsview;

        mViewHolder(View itemView) {
            super(itemView);
            newsview = itemView;
            //书名
            tv_book_name = itemView.findViewById(R.id.tv_book_name);
            //类别
            tv_book_category = itemView.findViewById(R.id.tv_book_category);
            //编号
            tv_book_number = itemView.findViewById(R.id.tv_book_number);
            //借阅人
            tv_book_person = itemView.findViewById(R.id.tv_book_person);
        }
    }
}