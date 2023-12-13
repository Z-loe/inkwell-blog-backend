package com.inkwell.inkwellblog.RequestParam.Article;

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
    public void setCategoryId(String categoryId) {this.categoryId = categoryId;}
    public String getCategoryId() {return categoryId;}


    private String content;
    public void setContent(String content){this.content = content;}
    public String getContent(){return content;}
}
