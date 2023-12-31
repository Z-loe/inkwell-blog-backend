package com.inkwell.inkwellblog.ReturnData;

public class UserData extends BaseReturnData {
    private String account;
    private String uid;
    private String nickname;
    private int userType;
    private String token;
    private String avatar;

    public String getAccount() {
        return account;
    }

    public String getUid() {
        return uid;
    }

    public String getNickname() { return nickname; }

    public int getUserType() { return userType; }

    public String getToken() {
        return token;
    }

    public void setAccount(String userId) {
        this.account = userId;
    }

    public void setUid(String UID) {
        this.uid = UID;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserType(int userType) {this.userType = userType;}

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
