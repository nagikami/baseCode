package nagi.lucene.design.abstractdocument;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 保证类型安全的同时，为强类型语言提供弱类型语言的动态属性功能
 */
public interface Document {
    Void put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
