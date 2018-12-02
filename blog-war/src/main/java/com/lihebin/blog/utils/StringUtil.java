package com.lihebin.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
/**
 * Created by lihebin on 2018/12/2.
 */
public class StringUtil {

    private final static Logger log = LoggerFactory.getLogger(StringUtil.class);

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean empty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean listEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static String randomPassword() {
        return (new Random().nextInt(899999) + 100000) + "";
    }


    public static String randomParam() {
        return (new Random().nextInt(899) + 100) + "";
    }


    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }


    public static String concatObject(String type, Object... params) {
        StringBuilder sb = new StringBuilder();
        if (type == null) {
            for (Object param : params) {
                sb.append(String.valueOf(param));
            }
        } else {
            for (Object param : params) {
                if (param.equals(params[params.length - 1])) {
                    sb.append(String.valueOf(param));
                    break;
                }
                sb.append(String.valueOf(param + type));
            }
        }
        return sb.toString();

    }


    public static Map hashMap(Object... objects) {
        Map result = new LinkedHashMap();
        for (int i = 0; i < objects.length / 2; ++i) {
            result.put(objects[2 * i], objects[2 * i + 1]);
        }
        return result;
    }


    public static List arrayList(Object... objects) {
        List result = new ArrayList();
        for (Object object : objects) {
            result.add(object);
        }
        return result;
    }


    public static Map getMapFromJsonObject(Object jsonObject) {
        try {
            if (jsonObject == null) {
                return null;
            } else if (jsonObject instanceof String) {
                return objectMapper.readValue(((String) jsonObject).getBytes("utf-8"), Map.class);
            } else if (jsonObject instanceof byte[]) {
                return objectMapper.readValue((byte[]) jsonObject, Map.class);
            } else if (jsonObject instanceof Map) {
                return (Map) jsonObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("can not transfer to map: " + jsonObject);
            return null;
        }
    }

    public static String getJsonStringFromMap(Map params) {
        try {
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            return objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            log.error("can not transfer to string: {}" + params);
            return null;
        }
    }
}
