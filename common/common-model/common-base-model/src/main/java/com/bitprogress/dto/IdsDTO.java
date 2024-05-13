package com.bitprogress.dto;

import com.bitprogress.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsDTO extends DTO {

    /**
     * ids
     */
    private Set<Long> ids;

}