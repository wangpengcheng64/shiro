package com.wpc.shiro.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *
 */
public class JSONUtil {

    /**
     * 普通格式化，空字段将被过滤掉
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        return JSONObject.toJSONString(object);
    }

    /**
     * 带null的格式化
     * @param object
     * @return
     */
    public static String toJSONStringWithNulls(Object object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
    }
}
