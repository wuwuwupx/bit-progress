package com.bitprogress.model.quartzjob.pojo.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.util.Objects;

/**
 * @author wuwuwupx
 *  触发器状态
 */
public enum TriggerStateEnum implements IEnum<Integer> {

    /**
     * 不存在
     */
    NONE(0, "不存在"),

    /**
     * 正常状态
     */
    NORMAL(1, "正常"),

    /**
     * 暂停
     */
    PAUSED(2, "暂停"),

    /**
     * 完成
     */
    COMPLETE(3, "完成"),

    /**
     * 错误
     */
    ERROR(4, "错误"),

    /**
     * 阻塞
     */
    BLOCKED(5, "阻塞"),

    ;

    private Integer value;
    private String name;

    TriggerStateEnum(Integer value, String name) {
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

    public static TriggerStateEnum valueOf(Integer value) {
        for (TriggerStateEnum triggerState : TriggerStateEnum.values()) {
            if (Objects.equals(triggerState.getValue(), value)) {
                return triggerState;
            }
        }
        return null;
    }

}
