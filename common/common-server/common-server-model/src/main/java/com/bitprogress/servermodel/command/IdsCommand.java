package com.bitprogress.servermodel.command;

import com.bitprogress.basemodel.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Set;

@Schema(description = "id列表操作指令信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsCommand extends Command {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id列表")
    @NotNull(message = "id列表不能为空")
    @NotEmpty(message = "id列表不能为空")
    private Set<Long> ids;

}
