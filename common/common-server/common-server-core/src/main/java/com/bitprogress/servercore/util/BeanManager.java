package com.bitprogress.servercore.util;

import lombok.Getter;
import org.springframework.context.ApplicationContext;

/**
 * @author wpx
 */
public class BeanManager {

    @Getter
    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (null == BeanManager.applicationContext) {
            BeanManager.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
