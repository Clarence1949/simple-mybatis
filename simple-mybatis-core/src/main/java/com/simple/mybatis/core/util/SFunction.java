package com.simple.mybatis.core.util;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author FanXing
 * @date 2023年02月17日 9:29
 */
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
