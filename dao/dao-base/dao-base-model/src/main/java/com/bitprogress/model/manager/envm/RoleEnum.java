package com.bitprogress.model.manager.envm;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * manager 角色
 * @author xiaolaba
 */
public enum RoleEnum implements IEnum<Integer>, Serializable {

    /**
     * 超管ROOT，拥有最高权限
     */
    ROOT(0, "超管"),

    /**
     * 普通管理员ADMIN
     */
    ADMIN(1, "管理员"),

    /**
     * 营运人员OPERATOR
     */
    OPERATOR(2, "管理员"),

    ;

    private Integer value;
    private String name;

    RoleEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getDisplayName() {
        return this.name;
    }

}
