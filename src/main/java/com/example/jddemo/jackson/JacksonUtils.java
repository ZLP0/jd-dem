package com.example.jddemo.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

public final class JacksonUtils {

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * 如果有不识别的属性，不会报错，只会忽略。
     */
    public static final ObjectMapper IGNORE_JSON_MAPPER;

    static {
        JSON_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        IGNORE_JSON_MAPPER = new ObjectMapper();
        IGNORE_JSON_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        IGNORE_JSON_MAPPER.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//单引号处理
    }

    /**
     * Constructor<br>
     */
    private JacksonUtils() {
    }

    /*------------------------------转字符串-------------------------------------------------------------------------*/
    /**
     * 将对象转为 json <br>
     *
     * @param object {@link Object}
     * @return json 字符串
     */
    public static String toJson(Object object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换Json出错", e);
        }
    }

    /**
     * 将对象转为 json <br>
     *
     * @param object {@link Object}
     * @return json 字符串
     */
    public static String toJsonNoException(Object object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /*---------------------转对象----------------------------------------------------------------------------------------*/
    /**
     * 将字符串 json 转为对象<br>
     *
     * @param <T>     转换后的类型
     * @param content json 字符串
     * @param type    转换后的类型
     * @return 转换后的结果
     */
    public static <T> T fromJson(String content, Class<T> type) {
        return toObject(JSON_MAPPER, content, type);
    }

    /**
     * 将字符串 json 转为对象，将忽略未知的字段<br>
     *
     * @param <T>     转换后的类型
     * @param content json 字符串
     * @param type    转换后的类型
     * @return 转换后的结果
     */
    public static <T> T fromIgnoreJson(String content, Class<T> type) {
        return toObject(IGNORE_JSON_MAPPER, content, type);
    }

    /**
     * 将 json 转对象 集合 List<Object>
     *
     * @param jsonValue
     * @param valueTypeRef
     * @param <T>
     * @return TypeReference<List < User>> typeRef = new TypeReference<List<User>>() {};
     * JacksonUtil.parseJson("[{}]",typeRef);
     */
    public static <T> T parseJson(String jsonValue, TypeReference<T> valueTypeRef) {
        if (jsonValue == null || valueTypeRef == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        try {
            return (T) IGNORE_JSON_MAPPER.readValue(jsonValue, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*-----------------------------------------------------------------------------------------------------------------*/
    /**
     * 将字符串 json 转为对象<br>
     *
     * @param <T>     转换后的类型
     * @param mapper  {@link ObjectMapper}
     * @param content json 字符串
     * @param type    转换后的类型
     * @return 转换后的结果
     */
    private static <T> T toObject(ObjectMapper mapper, String content, Class<T> type) {
        try {
            return mapper.readValue(content, type);
        } catch (IOException e) {
            throw new RuntimeException("json转换出错", e);
        }
    }


}