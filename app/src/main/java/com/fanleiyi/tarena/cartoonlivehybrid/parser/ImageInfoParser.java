package com.fanleiyi.tarena.cartoonlivehybrid.parser;

import com.alibaba.fastjson.JSON;
import com.fanleiyi.tarena.cartoonlivehybrid.entity.ImageInfoEntity;

/**
 * Created by tarena on 2017/7/25.
 */

public class ImageInfoParser {

    public static ImageInfoEntity parser(String json){
        return JSON.parseObject(json,ImageInfoEntity.class);
    }


}
