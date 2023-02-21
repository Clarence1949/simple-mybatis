package com.simple.mybatis.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.invoke.SerializedLambda;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FanXing
 * @date 2023年02月17日 9:31
 */
@UtilityClass
public class LambdaUtil {
    private static final Map<SFunction, String> FUNCTION_CACHE = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String propertyToString(SFunction func) {
        if (Objects.isNull(FUNCTION_CACHE.get(func)))
            synchronized (FUNCTION_CACHE) {
                if (Objects.isNull(FUNCTION_CACHE.get(func))) {
                    String str = objectMapper.writeValueAsString(func);
                    SerializedLambda lambda = objectMapper.readValue(str, SerializedLambda.class);
                    System.out.println(lambda);
                }
            }
        return FUNCTION_CACHE.get(func);
    }

}
