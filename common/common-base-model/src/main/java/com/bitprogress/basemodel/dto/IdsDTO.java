package com.bitprogress.basemodel.dto;

import com.bitprogress.basemodel.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsDTO extends DTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ids
     */
    private Set<Long> ids;

}
