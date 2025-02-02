package com.qinweizhao.blog.model.support;

import lombok.Data;

/**
 * @author ryanwang
 * @since 2020-03-06
 */
@Data
public class RainbowPage {

    private Integer page;

    private String fullPath;

    private Boolean isCurrent;
}
