package com.bitprogress.basecontext.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeserializeType implements ValueEnum {

    /**
     * 默认类
     */
    CLASS(0),

    /**
     * TypeReference
     */
    TYPE_REFERENCE(1),

    ;

    private final Integer value;

}
