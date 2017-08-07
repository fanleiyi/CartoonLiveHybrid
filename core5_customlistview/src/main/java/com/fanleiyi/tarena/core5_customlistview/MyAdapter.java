package com.fanleiyi.tarena.core5_customlistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tarena on 2017/7/31.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;

    public MyAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv=new TextView(context);
        tv.setText(list.get(i));
        return tv;
    }
}
