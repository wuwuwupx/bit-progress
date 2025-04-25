package com.bitprogress.validation.validator;

import com.bitprogress.util.CollectionUtils;

import java.util.Collection;
import java.util.function.Predicate;

public interface CollectionItemValidator {

    /**
     * 验证集合中的元素是否满足条件
     *
     * @param required   是否必须
     * @param collection 集合
     * @param predicate  条件
     * @param <T>        泛型
     * @return 是否满足条件
     */
    default <T> boolean validCollection(Boolean required, Collection<T> collection, Predicate<T> predicate) {
        return required
                ? CollectionUtils.allMatch(collection, predicate)
                : CollectionUtils.isEmpty(collection) || CollectionUtils.allMatch(collection, predicate);
    }

}
