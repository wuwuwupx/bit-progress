package com.bitprogress.basemodel.filler;

/**
 * simple filler interface specification for fill id by name
 */
public interface IdByNameFiller extends IdByNameIFiller {

    /**
     * set id
     *
     * @param id id
     */
    void setId(Long id);

    /**
     * get name
     *
     * @return name
     */
    String getName();

}
