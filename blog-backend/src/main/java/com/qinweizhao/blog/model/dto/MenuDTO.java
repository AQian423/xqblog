package com.qinweizhao.blog.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Menu output dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 4/3/19
 */
@Data
public class MenuDTO {

    private Integer id;

    private String name;

    private String url;

    private Integer priority;

    private String target;

    private String icon;

    private Integer parentId;

    private String team;

}
