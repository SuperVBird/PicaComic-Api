package com.flannep.picacomic.api.results.book;

import com.flannep.picacomic.api.others.pages.PageInfo;
import com.flannep.picacomic.api.others.pages.Pageable;
import com.flannep.picacomic.api.resources.Episode;
import com.flannep.picacomic.api.results.PicaResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 代表指定本子的章节信息
 */
public class PicaEpisodeInfoResult extends PicaResult implements Pageable {

    public PicaEpisodeInfoResult(JSONObject result) {
        super(result);
    }

    /**
     * 获取章节信息
     *
     * @return
     */
    public List<Episode> getEpisodes() {
        List<Episode> episodeList = new ArrayList<>();

        JSONArray epiArr = getEps().getJSONArray("docs");
        for (int i = 0; i < epiArr.size(); i++) {
            episodeList.add(new Episode(epiArr.getJSONObject(i)));
        }
        return episodeList;
    }


    /**
     * 获取页码信息
     *
     * @return
     */
    @Override
    public PageInfo getPageInfo() {
        JSONObject pageJson = JSONObject.fromObject(getEps());
        pageJson.remove("docs");
        return new PageInfo(pageJson);
    }

    private JSONObject getEps() {
        return getData().getJSONObject("eps");
    }

}
