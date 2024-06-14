package com.bitprogress.servermodel.vo.kv;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Schema(description = "字符串类型键值对展示信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleKeyValueVO extends KeyValueVO<String, String> {

    @Serial
    private static final long serialVersionUID = 1L;

}
