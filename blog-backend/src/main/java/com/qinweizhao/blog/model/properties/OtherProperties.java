package com.qinweizhao.blog.model.properties;

/**
 * Other properties.
 *
 * @author johnniang
 * @author ryanwang
 * @since 2019-04-01
 */
public enum OtherProperties implements PropertyEnum {

    /**
     * Global custom head.
     */
    CUSTOM_HEAD("blog_custom_head", String.class, ""),

    /**
     * Content page(post,sheet) custom head.
     */
    CUSTOM_CONTENT_HEAD("blog_custom_content_head", String.class, ""),

    /**
     * Statistics platform code,such as Google Analytics.
     */
    STATISTICS_CODE("blog_statistics_code", String.class, "");

    private final String value;

    private final Class<?> type;

    private final String defaultValue;

    OtherProperties(String value, Class<?> type, String defaultValue) {
        this.value = value;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }
}
