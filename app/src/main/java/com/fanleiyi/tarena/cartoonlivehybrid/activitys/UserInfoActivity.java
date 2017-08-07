package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.app.MyApp;
import com.fanleiyi.tarena.cartoonlivehybrid.bean.MyUser;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

@EActivity(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity {

    @ViewById(R.id.iv_userinfo_avatar)
    ImageView ivAvatar;


    @ViewById(R.id.tv_userinfo_nickname)
    TextView tvNickname;
    @ViewById(R.id.iv_userinfo_nicknameeditor)
    ImageView ivNicknameEditor;
    @ViewById(R.id.ll_userinfo_editnicknamecontainer)
    LinearLayout llNicknameContainer;
    @ViewById(R.id.et_userinfo_nickname)
    EditText etNickname;
    @ViewById(R.id.ib_userinfo_confirm)
    ImageButton ibConfirm;
    @ViewById(R.id.ib_userinfo_cancel)
    ImageButton ibCancel;

    @ViewById(R.id.tv_userinfo_username)
    TextView tvUsername;

    @ViewById(R.id.btn_userinfo_update)
    Button btnUpdate;

    //me：从SettingFragment跳转过来
    //friend: 从FriendFragmetn跳转过来
    //stranger: 从AddFriendActivity或者NewFriendActivity跳转过来
    String username;

    MyUser user;//根据username属性获得相对应的用户

    String cameraPath;//拍摄头像照片时SD卡的路径
    String avatarUrl;//上传头像照片完毕后，头像照片在服务器上的网址
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init(){
        user = MyApp.user;
        username=user.getUsername();
        initView();
    }

    private void initView() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", username);

        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e==null){
                    user = list.get(0);
                    //根据user中的内容设定界面
                    //设定用户头像
                    String avatar = user.getAvatar();
                    if (TextUtils.isEmpty(avatar)) {
                        ivAvatar.setImageResource(R.drawable.ic_launcher);
                    } else {
                        Picasso.with(MyApp.getInstance()).load(avatar).into(ivAvatar);

//                    ImageLoader.getInstance().displayImage(avatar, ivAvatar);
                    }


                    //user的昵称
                    String nickname = user.getNick();
                    tvNickname.setText(nickname);
                    llNicknameContainer.setVisibility(View.INVISIBLE);
                    //user的用户名
                    String username = user.getUsername();
                    tvUsername.setText(username);

                }else {
                    Log.i("TAG", "done: 查询用户失败"+e.getMessage());
                }
            }
        });




    }


    @Click(R.id.iv_userinfo_avatar)
    public void setAvatar(View v) {

        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        cameraPath = file.getAbsolutePath();
        imageUri = Uri.fromFile(file);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        Intent chooser = Intent.createChooser(intent1, "选择头像...");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(chooser, 101);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg1 == RESULT_OK && arg0 == 101) {
            String filePath = "";

            if (arg2 != null) {
                //图库选图
                //对于部分手机来说，在安卓原生的拍照程序基础上做了修改
                //导致拍照的照片会随着arg2返回到这里
                Uri uri = arg2.getData();

                if (uri != null) {
                    if (!uri.getPath().equals(imageUri.getPath())) {
                        //图库
                        Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore
								.Images.Media.DATA}, null, null, null);
                        cursor.moveToNext();
                        filePath = cursor.getString(0);
                        cursor.close();

                    } else {
                        //拍照
                        //拍照的路径依然是cameraPath
                        filePath = cameraPath;
                    }
                } else {
                    Bundle bundle = arg2.getExtras();
                    //bitmap是拍照回来的照片
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    //TODO 将bitmap存储到SD卡
                }

            } else {
                //相机拍照
                filePath = cameraPath;
            }

            final BmobFile bf = new BmobFile(new File(filePath));
            bf.uploadblock(new UploadFileListener() {

                @Override
                public void done(BmobException e) {
                    if (e==null){
                        avatarUrl = bf.getFileUrl();
                        Picasso.with(MyApp.getInstance()).load(avatarUrl).into(ivAvatar);
                    }else {
                        Log.i("TAG", "done: 上传头像失败！"+e.getMessage());
                    }
                }
            });
        }
    }

    @Click(R.id.iv_userinfo_nicknameeditor)
    public void setNickname(View v) {
        String nickname = tvNickname.getText().toString();
        tvNickname.setVisibility(View.INVISIBLE);
        llNicknameContainer.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(nickname)) {
            etNickname.setHint("请输入昵称...");
        } else {
            etNickname.setText(nickname);
        }
        ivNicknameEditor.setVisibility(View.GONE);
    }

    @Click(R.id.ib_userinfo_confirm)
    public void saveNickname(View v) {
        String nickname = etNickname.getText().toString();
        etNickname.setText("");
        tvNickname.setVisibility(View.VISIBLE);
        tvNickname.setText(nickname);
        llNicknameContainer.setVisibility(View.INVISIBLE);
        ivNicknameEditor.setVisibility(View.VISIBLE);
    }

    @Click(R.id.ib_userinfo_cancel)
    public void cancelNickname(View v) {
        etNickname.setText("");
        tvNickname.setVisibility(View.VISIBLE);
        llNicknameContainer.setVisibility(View.INVISIBLE);
        ivNicknameEditor.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btn_userinfo_update)
    public void update(View v) {
        if (avatarUrl != null) {
            user.setAvatar(avatarUrl);
        }

        user.setNick(tvNickname.getText().toString());


        user.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(UserInfoActivity.this,"更新资料完成",Toast.LENGTH_SHORT).show();
                }else {
                    Log.i("TAG", "done: 更新失败"+e.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 1500);
                }
            }
        });
    }

}
