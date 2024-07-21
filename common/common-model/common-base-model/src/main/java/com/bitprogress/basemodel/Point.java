package com.bitprogress.basemodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * point
 */
@Getter
@AllArgsConstructor
public abstract class Point implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
