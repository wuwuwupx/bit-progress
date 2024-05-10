package com.bitprogress.dto;

import com.bitprogress.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * id-name
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNameDTO extends DTO {

    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String name;

}
