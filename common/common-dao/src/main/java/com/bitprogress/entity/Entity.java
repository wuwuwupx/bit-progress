package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.bitprogress.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据库实体类
 */
@EqualsAndHashCode(callSuper = false)
@Data
public abstract class Entity extends DO {

    @TableId
    private Long id;

}
