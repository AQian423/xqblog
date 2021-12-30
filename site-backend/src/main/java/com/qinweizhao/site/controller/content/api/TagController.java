package com.qinweizhao.site.controller.content.api;

import static org.springframework.data.domain.Sort.Direction.DESC;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.qinweizhao.site.model.dto.TagDTO;
import com.qinweizhao.site.model.entity.Post;
import com.qinweizhao.site.model.entity.Tag;
import com.qinweizhao.site.model.enums.PostStatus;
import com.qinweizhao.site.model.vo.PostListVO;
import com.qinweizhao.site.service.PostService;
import com.qinweizhao.site.service.PostTagService;
import com.qinweizhao.site.service.TagService;

/**
 * Content tag controller.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-02
 */
@RestController("ApiContentTagController")
@RequestMapping("/api/content/tags")
public class TagController {

    private final TagService tagService;

    private final PostTagService postTagService;

    private final PostService postService;

    public TagController(TagService tagService,
        PostTagService postTagService,
        PostService postService) {
        this.tagService = tagService;
        this.postTagService = postTagService;
        this.postService = postService;
    }

    @GetMapping
    @ApiOperation("Lists tags")
    public List<? extends TagDTO> listTags(
        @SortDefault(sort = "updateTime", direction = DESC) Sort sort,
        @ApiParam("If the param is true, post count of tag will be returned")
        @RequestParam(name = "more", required = false, defaultValue = "false") Boolean more) {
        if (more) {
            return postTagService.listTagWithCountDtos(sort);
        }
        return tagService.convertTo(tagService.listAll(sort));
    }

    @GetMapping("{slug}/posts")
    @ApiOperation("Lists posts by tag slug")
    public Page<PostListVO> listPostsBy(@PathVariable("slug") String slug,
        @PageableDefault(sort = {"topPriority", "updateTime"}, direction = DESC)
            Pageable pageable) {
        // Get tag by slug
        Tag tag = tagService.getBySlugOfNonNull(slug);

        // Get posts, convert and return
        Page<Post> postPage =
            postTagService.pagePostsBy(tag.getId(), PostStatus.PUBLISHED, pageable);
        return postService.convertToListVo(postPage);
    }
}
