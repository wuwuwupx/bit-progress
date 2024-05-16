package com.bitprogress.excelmodel.model;

import com.bitprogress.basemodel.EO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * excel export write object
 * 用于excel 导出数据
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class ExportEO extends EO {
}
