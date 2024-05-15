package com.bitprogress.qo;

import com.bitprogress.QO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "id列表查询信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsQO extends QO {

    @Schema(description = "id列表")
    private Set<Long> ids;

}
