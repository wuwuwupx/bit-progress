package com.bitprogress.basemodel.map;

import com.bitprogress.basemodel.IMap;

import java.util.Map;

/**
 * simple to map
 */
public interface ObjectToMap extends IMap {

    /**
     * to map
     */
    Map<String, Object> toMap();

}
