package com.inkwell.inkwellblog.RequestParam;

public class LoginParam {
    private String account;
    private String password;
    //setter
    public void setAccount(String account){
        this.account = account;
    }
    public void setPassword(String password){
        this.password = password;
    }
    //getter
    public String getAccount(){
        return account;
    }
    public String getPassword(){
        return password;
    }
}
