package com.bitprogress.enums.time;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MomentType implements ValueEnum, MessageEnum {

    FIRST(1, "第一时刻"),
    LAST(2, "最后时刻")
    ;

    private final Integer value;

    private final String message;

}
