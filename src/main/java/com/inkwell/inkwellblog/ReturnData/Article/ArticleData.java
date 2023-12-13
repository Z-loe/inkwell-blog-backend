package com.inkwell.inkwellblog.ReturnData.Article;

import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.CategoryDate;

public class ArticleData extends BaseReturnData {
    private CategoryDate[] rows;

    public CategoryDate[] getRows() {
        return rows;
    }

    public void setRows(CategoryDate[] rows) {
        this.rows = rows;
    }
}
