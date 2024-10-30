package com.bitprogress.basemodel.qo;

import com.bitprogress.basemodel.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderColumnItem extends Item {

    /**
     * order by column
     */
    private String column;

    /**
     * asc or desc
     */
    private Boolean asc = true;

}
