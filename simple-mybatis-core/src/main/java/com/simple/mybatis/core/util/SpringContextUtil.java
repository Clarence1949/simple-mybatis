package com.simple.mybatis.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FanXing
 * @date 2022年12月04日 16:10
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    private static final ConcurrentHashMap<Class<?>, Object> CLASS_BEAN_CACHE = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (!CLASS_BEAN_CACHE.containsKey(clazz)) {
            synchronized (CLASS_BEAN_CACHE) {
                if (!CLASS_BEAN_CACHE.containsKey(clazz)) {
                    CLASS_BEAN_CACHE.put(clazz, context.getBean(clazz));
                }
            }
        }
        return (T) CLASS_BEAN_CACHE.get(clazz);
    }

}
