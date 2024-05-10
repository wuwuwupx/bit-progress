package com.bitprogress.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleKeyValueVO extends KeyValueVO<String, String> {
}
