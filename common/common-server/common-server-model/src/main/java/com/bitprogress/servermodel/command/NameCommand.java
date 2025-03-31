package com.bitprogress.servermodel.command;

import com.bitprogress.basemodel.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Schema(description = "名称操作指令信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameCommand extends Command {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "名称")
    @NotNull(message = "名称不能为空")
    @NotBlank(message = "名称不能为空")
    private String name;

}
