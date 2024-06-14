package com.bitprogress.basemodel.response;

import com.bitprogress.basemodel.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * result response
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class RequestResponse extends Response {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * request code
     */
    private Integer code;

    /**
     * response message
     */
    private String message;

}
