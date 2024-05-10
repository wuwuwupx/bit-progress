package com.bitprogress.dto;

import com.bitprogress.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdDTO extends DTO {

    /**
     * id
     */
    private Long id;

}
