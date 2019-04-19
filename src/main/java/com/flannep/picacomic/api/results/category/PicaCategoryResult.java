package com.flannep.picacomic.api.results.category;

import com.flannep.picacomic.api.results.PicaResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PicaCategoryResult extends PicaResult {

    public PicaCategoryResult(JSONObject result) {
        super(result);
    }

    /**
     * 获取本子分类（搜索页的分类）
     *
     * @return
     */
    public List<Category> getCategories() {
        List<Category> caList = new ArrayList<>();
        if (getData().containsKey("categories")) {
            JSONArray arr = getData().getJSONArray("categories");
            for (int i = 0; i < arr.size(); i++) {
                caList.add(new Category(arr.getJSONObject(i)));
            }
        }
        return caList;
    }

}
