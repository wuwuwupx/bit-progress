package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * @since 2021-11-19
 */
@ApiModel(value = "定时任务分组")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sdr_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "定时任务分组ID")
    @TableId(value = "group_id", type = IdType.AUTO)
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0：false，未删除；1：true，已删除")
    @TableLogic
    private Boolean deleted;

}
