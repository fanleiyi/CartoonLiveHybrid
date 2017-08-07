package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.fragment.CartoonFragment;
import com.fanleiyi.tarena.cartoonlivehybrid.fragment.DiscoverFragment;
import com.fanleiyi.tarena.cartoonlivehybrid.fragment.MeFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by tarena on 2017/7/25.
 */
@EActivity(R.layout.main_fragment)
public class MainFragmentActivity extends BaseActivity {
    CartoonFragment cartoonFragment;
    Button[] buttons=new Button[3];
    Fragment[] fragments=new Fragment[3];
    // 单击的
    int clickBtnIndex=0;
    // 正在显示的fragment下标
    int currentShowFragmentIndex=0;
    @AfterViews
    public void init(){
        cartoonFragment=new CartoonFragment();
        initBtn();
        FragmentManager fragmentManager=getSupportFragmentManager();
        // 开始事务
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        // 动作1：add
        fragmentTransaction.add(R.id.fragment_container,cartoonFragment);
        // 动作2：show
        fragmentTransaction.show(cartoonFragment);
        // 提交
        fragmentTransaction.commit();
    }

    private void initBtn() {
        buttons[0]= (Button) findViewById(R.id.btn_main_fragment_cartoon);
        buttons[1]= (Button) findViewById(R.id.btn_main_fragment_discover);
        buttons[2]= (Button) findViewById(R.id.btn_main_fragment_me);
        buttons[currentShowFragmentIndex].setSelected(true);
        fragments[0]=cartoonFragment;
        fragments[1]=new DiscoverFragment();
        fragments[2]=new MeFragment();
        MyListner myListner=new MyListner();
        for(Button btn:buttons){
            btn.setOnClickListener(myListner);
        }
    }

    class MyListner implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            try {
                    // 判断单击的是哪个button
                switch (view.getId()){
                    case R.id.btn_main_fragment_cartoon:
                        clickBtnIndex=0;
                        break;
                    case R.id.btn_main_fragment_discover:
                        clickBtnIndex=1;
                        break;
                    case R.id.btn_main_fragment_me:
                        clickBtnIndex=2;
                        break;
                }
                // 判断是不是当前按钮
                if (clickBtnIndex!=currentShowFragmentIndex) {
                    // 开始事务
                    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                    // 动作1：隐藏之前的fragment
                    ft.hide(fragments[currentShowFragmentIndex]);
                    // 动作2：增加新的fragment
                    Fragment showFragment=fragments[clickBtnIndex];
                    if (showFragment.isAdded()==false){
                        ft.add(R.id.fragment_container,showFragment);
                    }
                    // 动作3：显示
                    ft.show(showFragment);
                    // 所有动作没出异常，提交动作
                    ft.commit();
                    buttons[clickBtnIndex].setSelected(true);
                    buttons[currentShowFragmentIndex].setSelected(false);
                    currentShowFragmentIndex=clickBtnIndex;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }



}
