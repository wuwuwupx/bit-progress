package com.bitprogress.okhttp.constant;

import okhttp3.MediaType;

/**
 * @author wuwuwupx
 *  OkHttp常量
 */
public class OkHttpConstants {

    /**
     * JSON类型的MediaType
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * image类型的MediaType
     */
    public static final MediaType MEDIA_TYPE_IMAGE = MediaType.get("image");

    /**
     * file类型的MediaType
     */
    public static final MediaType MEDIA_TYPE_FILE = MediaType.get("file/*");

    /**
     * form-data类型的MediaType
     */
    public static final MediaType MEDIA_TYPE_FORM_DATA = MediaType.parse("multipart/form-data");

    /**
     * octet-stream类型的MediaType
     */
    public static final MediaType MEDIA_TYPE_OCTET_STREAM = MediaType.parse("application/octet-stream");

}
