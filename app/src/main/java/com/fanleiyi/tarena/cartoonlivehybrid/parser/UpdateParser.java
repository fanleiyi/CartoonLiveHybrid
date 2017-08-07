package com.fanleiyi.tarena.cartoonlivehybrid.parser;

import com.alibaba.fastjson.JSON;
import com.fanleiyi.tarena.cartoonlivehybrid.entity.VersionEntity;

/**
 * Created by tarena on 2017/7/26.
 */

public class UpdateParser {
    public static VersionEntity parser(String json){
        return JSON.parseObject(json,VersionEntity.class);
    }
}
