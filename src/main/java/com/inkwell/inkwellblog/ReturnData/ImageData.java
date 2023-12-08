package com.inkwell.inkwellblog.ReturnData;

public class ImageData {
    private String url;
    private String alt;
    private String href;
    //setter
    public void setUrl(String url) {
        this.url = url;
    }
    public void setAlt(String alt) {
        this.alt = alt;
    }
    public  void setHref(String href) {
        this.href = href;
    }
    //getter
    public String getUrl() {
        return url;
    }
    public String getAlt() {
        return alt;
    }
    public String getHref() {
        return  href;
    }
}
