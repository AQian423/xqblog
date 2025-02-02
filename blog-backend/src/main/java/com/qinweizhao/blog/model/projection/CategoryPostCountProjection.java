package com.qinweizhao.blog.model.projection;

import lombok.Data;

/**
 * Category post count projection.
 *
 * @author johnniang
 * @since 19-4-23
 */
@Data
public class CategoryPostCountProjection {

    private Long postCount;

    private Integer categoryId;
}
