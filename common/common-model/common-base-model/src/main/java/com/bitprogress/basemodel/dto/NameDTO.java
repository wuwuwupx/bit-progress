package com.bitprogress.basemodel.dto;

import com.bitprogress.basemodel.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * name
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameDTO extends DTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * name
     */
    private String name;

}
