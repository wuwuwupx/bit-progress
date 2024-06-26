package com.bitprogress.servermodel.qo;

import com.bitprogress.basemodel.QO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 时间查询
 */
@Schema(description = "时间点查询信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeQO extends QO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "查询时间")
    private LocalDateTime time;

}
