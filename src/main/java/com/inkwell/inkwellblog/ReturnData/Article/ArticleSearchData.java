package com.inkwell.inkwellblog.ReturnData.Article;

import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.SearchData;

public class ArticleSearchData extends BaseReturnData {
    private SearchData data;

    public SearchData getData() {
        return data;
    }

    public void setData(SearchData data) {
        this.data = data;
    }
}
