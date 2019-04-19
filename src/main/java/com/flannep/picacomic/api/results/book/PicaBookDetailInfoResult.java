package com.flannep.picacomic.api.results.book;

import com.flannep.picacomic.api.resources.BookDetailInfo;
import com.flannep.picacomic.api.results.PicaResult;
import net.sf.json.JSONObject;

/**
 * 代表了请求本子详细信息的返回结果
 */
public class PicaBookDetailInfoResult extends PicaResult {


    public PicaBookDetailInfoResult(JSONObject result) {
        super(result);
    }


    private JSONObject getComic() {
        return getData().getJSONObject("comic");
    }

    /**
     * 获取本子详细信息
     *
     * @return
     */
    public BookDetailInfo getBookDetailInfo() {
        return new BookDetailInfo(getComic());
    }

}
