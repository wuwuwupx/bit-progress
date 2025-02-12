package com.bitprogress.enums.time;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeekType implements ValueEnum, MessageEnum {

    PRE_WEEK(0, "上周"),
    THIS_WEEK(1, "本周"),
    NEXT_WEEK(2, "下周"),
    ;

    private final Integer value;
    private final String message;

}
