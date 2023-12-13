package com.inkwell.inkwellblog.ReturnData.Article;

import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.DetailData;

public class ArticleDetailData extends BaseReturnData {
    private DetailData data;

    public DetailData getData() {
        return data;
    }

    public void setData(DetailData data) {
        this.data = data;
    }
}
