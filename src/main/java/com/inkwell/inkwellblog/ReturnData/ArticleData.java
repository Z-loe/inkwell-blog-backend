package com.inkwell.inkwellblog.ReturnData;

public class ArticleData extends BaseReturnData{
    private CategoryDate[] rows;

    public CategoryDate[] getRows() {
        return rows;
    }

    public void setRows(CategoryDate[] rows) {
        this.rows = rows;
    }
}
