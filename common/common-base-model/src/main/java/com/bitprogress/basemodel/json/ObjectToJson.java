package com.bitprogress.basemodel.json;

import com.bitprogress.basemodel.IJson;

/**
 * simple object to json
 */
public interface ObjectToJson extends IJson {

    /**
     * return json string
     */
    String toJson();

}
