package com.qinweizhao.blog.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 日志类型
 *
 * @author ryanwnag
 * @author qinweizhao
 */
public enum JournalType implements ValueEnum<Integer> {

    /**
     * 公开
     */
    PUBLIC(1),

    /**
     * 私密
     */
    INTIMATE(0);

    @EnumValue
    private final int value;

    JournalType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
