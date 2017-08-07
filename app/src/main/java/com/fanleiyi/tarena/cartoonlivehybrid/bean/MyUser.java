package com.fanleiyi.tarena.cartoonlivehybrid.bean;


import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
    //性别，位置，拼音名称和拼音首字母
    String avatar;
    String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
