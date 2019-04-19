package com.flannep.picacomic.api.results.announcement;

import com.flannep.picacomic.api.others.pages.PageInfo;
import com.flannep.picacomic.api.others.pages.Pageable;
import com.flannep.picacomic.api.results.PicaResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 代表主页公告的数据
 */
public class PicaAnnouncementResult extends PicaResult implements Pageable {


    public PicaAnnouncementResult(JSONObject result) {
        super(result);
    }

    /**
     * 获取公告
     * 无法获取到返回长度为0的list
     *
     * @return
     */
    public List<Announcement> getAnnouncements() {
        List<Announcement> annoList = new ArrayList<>();
        JSONObject data = getData();
        if (data.containsKey("announcements")) {
            JSONArray annoArr = getData().getJSONObject("announcements").getJSONArray("docs");
            for (int i = 0; i < annoArr.size(); i++) {
                annoList.add(new Announcement(annoArr.getJSONObject(i)));
            }
        }
        return annoList;
    }

    /**
     * 获取页码信息
     *
     * @return
     */
    //todo 要过滤冗余数据
    @Override
    public PageInfo getPageInfo() {
        return new PageInfo(getData().getJSONObject("announcements"));
    }
}
