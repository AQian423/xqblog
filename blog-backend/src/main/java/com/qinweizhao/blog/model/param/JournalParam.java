package com.qinweizhao.blog.model.param;

import com.qinweizhao.blog.model.enums.JournalType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Journal param.
 *
 * @author johnniang
 * @author ryanwang
 * @since 2019-4-25
 */
@Data
public class JournalParam {

    @NotBlank(message = "内容不能为空")
    @Size(max = 511, message = "内容的字符长度不能超过 {max}")
    private String sourceContent;

    private JournalType type = JournalType.PUBLIC;
}
