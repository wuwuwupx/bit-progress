package com.bitprogress.model.quartzjob.pojo.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.util.Objects;

/**
 * @author wuwuwupx
 * @Description: 触发器类型
 **/
public enum TriggerType implements IEnum<Integer> {

    /**
     * 简单触发类型
     */
    SIMPLE(0, "simple"),

    /**
     * 表达式触发类型
     */
    CRON(1, "cron"),
    ;

    private Integer value;
    private String name;

    TriggerType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }

    public static TriggerType valueOf(Integer value) {
        for (TriggerType triggerType : TriggerType.values()) {
            if (Objects.equals(triggerType.getValue(), value)) {
                return triggerType;
            }
        }
        return null;
    }

}