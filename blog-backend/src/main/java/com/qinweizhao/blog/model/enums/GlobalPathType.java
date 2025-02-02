package com.qinweizhao.blog.model.enums;

/**
 * Global path type.
 *
 * @author ryanwang
 * @since 2020-02-01
 */
public enum GlobalPathType implements ValueEnum<Integer> {

    /**
     * Relative path.
     */
    RELATIVE(0),

    /**
     * Absolute path.
     */
    ABSOLUTE(1);

    private final Integer value;

    GlobalPathType(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
