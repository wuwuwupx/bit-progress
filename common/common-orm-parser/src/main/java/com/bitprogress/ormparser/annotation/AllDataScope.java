package com.bitprogress.ormparser.annotation;

import java.lang.annotation.*;

/**
 * 全部数据权限
 * 优先级低于 {@link CurrentDataScope} 和 {@link LimitedDataScope}
 * 优先级高于 {@link MarkDataScopeType}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllDataScope {
}
