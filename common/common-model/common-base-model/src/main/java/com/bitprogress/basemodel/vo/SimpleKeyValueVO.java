package com.bitprogress.basemodel.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleKeyValueVO extends KeyValueVO<String, String> {
}
