package com.bitprogress.servermodel.vo.kv;

import com.bitprogress.basemodel.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = "键值对展示信息")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueVO<K, V> extends VO {

    /**
     * key
     */
    @Schema(description = "key")
    private K key;

    /**
     * value
     */
    @Schema(description = "value")
    private V value;

}
