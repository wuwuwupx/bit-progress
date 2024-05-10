package com.bitprogress.command;

import com.bitprogress.Command;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@ApiModel("id列表操作指令信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsCommand extends Command {

    @ApiModelProperty(value = "id列表")
    @NotNull(message = "id列表不能为空")
    @NotEmpty(message = "id列表不能为空")
    private Set<Long> ids;

}
