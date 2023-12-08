package com.inkwell.inkwellblog.RequestParam;

public class AddArticleParam
{
    private String title;
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }


    private String categoryId;
    public void setCategoryId(String name) {this.categoryId = categoryId;}
    public String getCategoryId() {return categoryId;}


    private String content;
    public void setContent(String content){this.content = content;}
    public String getContent(){return content;}
}
