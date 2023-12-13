package com.inkwell.inkwellblog.RequestParam.User;

public class SignupParam {
    private String account;
    private String password;
    private String nickname;
    //setter
    public void setAccount(String account) {
        this.account = account;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    //getter
    public String getAccount() {
        return account;
    }
    public String getPassword() {
        return password;
    }
    public String getNickname() {
        return nickname;
    }
}
