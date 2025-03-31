package com.bitprogress.basemodel.map;

import com.bitprogress.basemodel.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * simple map to object
 */
public interface FillByMap extends IMap {

    Logger log = LoggerFactory.getLogger(FillByMap.class);

    /**
     * map to object
     */
    default void fillObject(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        Class<? extends FillByMap> fillClass = getClass();
        map.forEach((key, value) -> {
            try {
                Field field = fillClass.getDeclaredField(key);
                boolean canAccess = field.canAccess(this);
                field.setAccessible(true);
                field.set(this, value);
                field.setAccessible(canAccess);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("fillObject error", e);
            }
        });
    }

}
