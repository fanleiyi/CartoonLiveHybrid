package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;
import com.fanleiyi.tarena.cartoonlivehybrid.bean.MyUser;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by tarena on 2017/7/25.
 */
@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewById(R.id.et_register_username)
    EditText etUsername;
    @ViewById(R.id.et_register_password)
    EditText etPassword;
    @ViewById(R.id.et_register_confirm_password)
    EditText etPassword_Confirm;
    @ViewById(R.id.et_register_name)
    EditText etNikename;
    private String username;
    private String password;

    @Click(R.id.btn_register_submit)
    public void register(){
        // 构建实体类(MyUser)对象
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
//        if (TextUtils.isEmpty(username)){
//            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (TextUtils.isEmpty(password)){
//            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (!etUsername.getText().toString().equals(etPassword_Confirm.getText().toString())){
//            Toast.makeText(this,"两次密码输入不一致！",Toast.LENGTH_LONG).show();
//            return;
//        }
       String nickName=etNikename.getText().toString();
//        if (TextUtils.isEmpty(nickName)){
//            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_LONG).show();
//            return;
//        }
        final MyUser user = new MyUser();

        user.setUsername(etUsername.getText().toString());
        //是否MD5加密？取决于同学自己的设计
        user.setPassword(etPassword.getText().toString());
        user.setNick(nickName);
        
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e==null){
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser s, BmobException e) {
                            if (e==null){
                                MyApp.user=user;
                                //登录环信
                                loginEaseMob();
                                //跳转界面，跳转到MainActivity
                                startActivity(new Intent(RegisterActivity.this,MainFragmentActivity_.class));
                            }else {
                                Toast.makeText(RegisterActivity.this,"登录失败"+e.getMessage(),Toast.LENGTH_LONG);
                            }
                        }


                    });
                }else {
                    Toast.makeText(RegisterActivity.this,"注册失败"+e.getMessage(),Toast.LENGTH_LONG);
                }
            }
        });

    }

    private void loginEaseMob() {
        EMClient.getInstance().login(username,password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                Toast.makeText(RegisterActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String error) {
                Log.d("main", "登录聊天服务器失败！" + error);
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
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                loginEaseMob();
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }



}
