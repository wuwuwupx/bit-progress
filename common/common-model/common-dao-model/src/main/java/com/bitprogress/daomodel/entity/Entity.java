package com.bitprogress.daomodel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.bitprogress.basemodel.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 数据库实体类
 */
@EqualsAndHashCode(callSuper = false)
@Data
public abstract class Entity extends DO {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

}
