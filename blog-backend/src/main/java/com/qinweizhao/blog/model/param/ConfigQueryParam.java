package com.qinweizhao.blog.model.param;

import com.qinweizhao.blog.model.core.PageParam;
import com.qinweizhao.blog.model.enums.ConfigType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Option query params.
 *
 * @author ryanwang
 * @author qinweizhao
 * @since 2019-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigQueryParam extends PageParam {

    private String keyword;

    private ConfigType type;
}
