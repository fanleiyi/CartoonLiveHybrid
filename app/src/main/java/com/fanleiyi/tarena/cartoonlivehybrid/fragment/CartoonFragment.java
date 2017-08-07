package com.fanleiyi.tarena.cartoonlivehybrid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.UrlFactory;
import com.fanleiyi.tarena.cartoonlivehybrid.activitys.ReaderActivity_;
import com.fanleiyi.tarena.cartoonlivehybrid.biz.DownloadBiz;

import java.util.ArrayList;

/**
 * Created by tarena on 2017/7/25.
 */

public class CartoonFragment extends Fragment {
    View view;
    WebView webView;
    private String url;
    MyReceiver myReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // view =View.inflate(getActivity(), R.layout.fragment_cartoon,null);
        view = inflater.inflate(R.layout.fragment_cartoon,null);
        myReceiver=new MyReceiver();
        IntentFilter filter=new IntentFilter("downloadOver");
        getActivity().registerReceiver(myReceiver,filter);
        // 远程公网ip：124.207.192.18
        url = UrlFactory.getIndex();
        webView= (WebView) view.findViewById(R.id.webViewId);
        webView.loadUrl(url);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String tag="tarena:download/";
                if (url.contains(tag)) {
                    String data =url.substring(tag.length());
                    String cartoonId=data.split("#")[0];
                    String chapterId=data.split("#")[1];
                    DownloadBiz.getImage(getActivity(),cartoonId,chapterId);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> list= (ArrayList<String>) intent.getSerializableExtra("list");
            Intent toReader=new Intent(getActivity(), ReaderActivity_.class);
            toReader.putExtra("list",list);
            startActivity(toReader);
        }
    }

}
