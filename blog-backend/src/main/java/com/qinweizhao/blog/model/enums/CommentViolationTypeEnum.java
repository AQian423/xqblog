package com.qinweizhao.blog.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 评论违规类型
 *
 * @author Lei XinXin
 * @since 2020/1/4
 */
@Getter
public enum CommentViolationTypeEnum {

    /**
     * 普通
     */
    NORMAL(0),
    /**
     * 频繁
     */
    FREQUENTLY(1);

    @EnumValue
    private final int type;

    CommentViolationTypeEnum(int type) {
        this.type = type;
    }
}
