package com.flannep.picacomic.api.utils;

import com.flannep.picacomic.api.PicaHeader;
import com.flannep.picacomic.api.results.PicaResult;
import net.sf.json.JSONObject;

import java.io.IOException;

public class PicaRequestUtil {

    public static PicaResult getPicaResult(PicaHeader header) throws IOException, Exception {
        String returnStr = NetUtil.getResult(header);
        JSONObject json = JSONObject.fromObject(returnStr);
        return new PicaResult(json);
    }

}
