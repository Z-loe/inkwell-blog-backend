package com.inkwell.inkwellblog.ReturnData;

import java.util.List;
// wangEditor返回数据
public class WangEditorResponseData {

    private int errno;
    private String message;
    private List<ImageData> data;


    public int getErrno() {
        return errno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public List<ImageData> getData() {
        return data;
    }

    public void setData(List<ImageData> data) {
        this.data = data;
    }


}
