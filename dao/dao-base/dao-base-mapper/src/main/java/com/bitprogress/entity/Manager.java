package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bitprogress.model.manager.envm.RoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wuwuwupx
 * created on 2021-06-10
 */
@ApiModel(value = "管理员")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员ID")
    @TableId(value = "manager_id", type = IdType.AUTO)
    private Long managerId;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色。ROOT：超级管理员，ADMIN：普通管理员")
    private RoleEnum role;

    @ApiModelProperty(value = "是否已被禁用。false：未禁用(默认)，true：已禁用")
    private Boolean disabled;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除状态true:已删除,false:未删除")
    @TableLogic
    private Boolean deleted;

}
