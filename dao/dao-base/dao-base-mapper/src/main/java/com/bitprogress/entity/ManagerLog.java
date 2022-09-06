package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bitprogress.model.managerlog.envm.MethodEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wuwuwupx
 * created on 2021-04-26
 */
@ApiModel(value = "管理员操作日志")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_manager_log")
public class ManagerLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员操作日志ID")
    @TableId(value = "manager_log_id", type = IdType.AUTO)
    private Long managerLogId;

    @ApiModelProperty(value = "管理员ID")
    private Long managerId;

    @ApiModelProperty(value = "请求方法类型")
    private MethodEnum method;

    @ApiModelProperty(value = "请求uri")
    private String uri;

    @ApiModelProperty(value = "参数列表")
    private String args;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除：true：已删除，false：未删除")
    @TableLogic
    private Boolean deleted;


}
