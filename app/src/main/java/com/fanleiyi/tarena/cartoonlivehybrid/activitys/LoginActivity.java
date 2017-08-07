package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;
import com.fanleiyi.tarena.cartoonlivehybrid.bean.MyUser;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by tarena on 2017/7/24.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById(R.id.et_login_username)
    EditText etUsername;
    @ViewById(R.id.et_login_password)
    EditText etPassword;
    private String password;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Click(R.id.btn_login_register)
    public  void toLogin(){
        Intent intent=new Intent(this,RegisterActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.btn_login_submit)
    public  void toMain() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
//        if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            final MyUser user= new MyUser();
            user.setUsername(username);
            user.setPassword(password);
            user.login(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser s, BmobException e) {
                    if (e==null){
                        MyApp.user=user;
                        //登录环信
                        loginEaseMob();
                        Intent intent=new Intent(LoginActivity.this,MainFragmentActivity_.class);
                        startActivity(intent);
                    }else {
                        Log.i("TAG", "done: 登录失败"+e.getMessage());
                        Toast.makeText(LoginActivity.this,"登录失败！"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });


//        }else {
//            Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
//        }
    }

    private void loginEaseMob() {
        EMClient.getInstance().login(username,password, new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        Log.d("main", "登录聊天服务器成功！");


                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！" + message);

                        //登录失败则因为未注册
                        try {
                            // 调用sdk注册方法
                            EMClient.getInstance().createAccount(username,password);//同步方法
                        } catch (final Exception e) {
                            //注册失败
                            Log.i("ivan", "注册失败: " + e);
                            return;
                        }
                        //注册成功！
                        Log.i("ivan", "注册成功！");

                        loginEaseMob();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


