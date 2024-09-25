package com.bitprogress.basemodel.filler;

/**
 * simple filler interface specification for fill name by id
 */
public interface NameByIdFiller extends IdByNameIFiller {

    /**
     * set name
     *
     * @param name name
     */
    void setName(String name);

    /**
     * get id
     *
     * @return id
     */
    Long getId();

}
