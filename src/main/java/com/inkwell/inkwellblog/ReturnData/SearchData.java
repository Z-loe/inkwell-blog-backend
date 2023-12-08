package com.inkwell.inkwellblog.ReturnData;

public class SearchData {
    private String keyword;
    private String categoryId;
    private int page;
    private int pageSize;
    private DetailData[] rows;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public DetailData[] getRows() {
        return rows;
    }

    public void setRows(DetailData[] rows) {
        this.rows = rows;
    }
}