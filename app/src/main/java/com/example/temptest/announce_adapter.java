package com.example.temptest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class announce_adapter extends BaseAdapter {
    Context context;

    public announce_adapter(Context context, ArrayList<announce_list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    ArrayList<announce_list_item> list_itemArrayList;

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.ann_list_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        TextView board_num = (TextView)v.findViewById(R.id.board_num);

        title.setText(list_itemArrayList.get(i).getTitle());
        board_num.setText(list_itemArrayList.get(i).getBoard_num());
        return v;
    }
}
