package com.bitprogress.command;

import com.bitprogress.Command;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("id操作指令信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdCommand extends Command {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

}
