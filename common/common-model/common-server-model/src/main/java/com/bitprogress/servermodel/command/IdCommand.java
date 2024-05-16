package com.bitprogress.servermodel.command;

import com.bitprogress.basemodel.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = "id操作指令信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdCommand extends Command {

    @Schema(description = "id")
    @NotNull(message = "id不能为空")
    private Long id;

}
