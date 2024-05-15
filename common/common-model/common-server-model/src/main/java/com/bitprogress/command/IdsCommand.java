package com.bitprogress.command;

import com.bitprogress.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "id列表操作指令信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsCommand extends Command {

    @Schema(description = "id列表")
    @NotNull(message = "id列表不能为空")
    @NotEmpty(message = "id列表不能为空")
    private Set<Long> ids;

}
