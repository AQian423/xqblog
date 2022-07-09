package com.qinweizhao.blog.model.params;

import com.qinweizhao.blog.model.entity.Category;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import com.qinweizhao.blog.model.dto.base.InputConverter;
import com.qinweizhao.blog.utils.SlugUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Category param.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-21
 */
@Data
public class CategoryParam implements InputConverter<Category> {

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 255, message = "分类名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "分类别名的字符长度不能超过 {max}")
    private String slug;

    @Size(max = 100, message = "分类描述的字符长度不能超过 {max}")
    private String description;

    @Size(max = 1023, message = "封面图链接的字符长度不能超过 {max}")
    private String thumbnail;

    private Integer parentId = 0;

    @Override
    public Category convertTo() {

        slug = StringUtils.isBlank(slug) ? SlugUtils.slug(name) : SlugUtils.slug(slug);

        if (null == thumbnail) {
            thumbnail = "";
        }

        return InputConverter.super.convertTo();
    }

    @Override
    public void update(Category category) {

        slug = StringUtils.isBlank(slug) ? SlugUtils.slug(name) : SlugUtils.slug(slug);

        if (null == thumbnail) {
            thumbnail = "";
        }

        InputConverter.super.update(category);
    }
}
