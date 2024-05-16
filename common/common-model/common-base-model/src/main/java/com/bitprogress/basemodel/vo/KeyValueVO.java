package com.bitprogress.basemodel.vo;

import com.bitprogress.basemodel.VO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueVO<K, V> extends VO {

    /**
     * key
     */
    private K key;

    /**
     * value
     */
    private V value;

}
