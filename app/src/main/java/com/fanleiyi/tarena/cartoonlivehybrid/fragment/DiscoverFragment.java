package com.fanleiyi.tarena.cartoonlivehybrid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.LiveActivity;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.LiveActivity_;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.LookActivity_;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.StartActivity;

/**
 * Created by tarena on 2017/7/25.
 */

public class DiscoverFragment extends Fragment {
    View view;
    LinearLayout ll_discover_live;
    LinearLayout ll_discover_look;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_discover,container,false);
        initViews();
        addListener();
        return view;
    }

    private void addListener() {
        ll_discover_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StartActivity.class));
            }
        });



        ll_discover_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LookActivity_.class));
            }
        });

    }

    private void initViews() {
        ll_discover_live= (LinearLayout) view.findViewById(R.id.ll_discover_live);
        ll_discover_look= (LinearLayout) view.findViewById(R.id.ll_discover_look);
    }
}
