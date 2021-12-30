package com.qinweizhao.site.controller.content.model;

import static com.qinweizhao.site.model.support.HaloConst.POST_PASSWORD_TEMPLATE;
import static com.qinweizhao.site.model.support.HaloConst.SUFFIX_FTL;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.qinweizhao.site.cache.AbstractStringCacheStore;
import com.qinweizhao.site.exception.ForbiddenException;
import com.qinweizhao.site.exception.NotFoundException;
import com.qinweizhao.site.model.entity.Category;
import com.qinweizhao.site.model.entity.Post;
import com.qinweizhao.site.model.entity.PostMeta;
import com.qinweizhao.site.model.entity.Tag;
import com.qinweizhao.site.model.enums.EncryptTypeEnum;
import com.qinweizhao.site.model.enums.PostEditorType;
import com.qinweizhao.site.model.enums.PostStatus;
import com.qinweizhao.site.model.vo.ArchiveYearVO;
import com.qinweizhao.site.model.vo.PostListVO;
import com.qinweizhao.site.service.AuthenticationService;
import com.qinweizhao.site.service.CategoryService;
import com.qinweizhao.site.service.OptionService;
import com.qinweizhao.site.service.PostCategoryService;
import com.qinweizhao.site.service.PostMetaService;
import com.qinweizhao.site.service.PostService;
import com.qinweizhao.site.service.PostTagService;
import com.qinweizhao.site.service.TagService;
import com.qinweizhao.site.service.ThemeService;
import com.qinweizhao.site.utils.MarkdownUtils;

/**
 * Post Model
 *
 * @author ryanwang
 * @date 2020-01-07
 */
@Component
public class PostModel {

    private final PostService postService;

    private final ThemeService themeService;

    private final PostCategoryService postCategoryService;

    private final CategoryService categoryService;

    private final PostTagService postTagService;

    private final TagService tagService;

    private final PostMetaService postMetaService;

    private final OptionService optionService;

    private final AbstractStringCacheStore cacheStore;

    private final AuthenticationService authenticationService;

    public PostModel(PostService postService,
        ThemeService themeService,
        PostCategoryService postCategoryService,
        CategoryService categoryService,
        PostMetaService postMetaService,
        PostTagService postTagService,
        TagService tagService,
        OptionService optionService,
        AbstractStringCacheStore cacheStore,
        AuthenticationService authenticationService) {
        this.postService = postService;
        this.themeService = themeService;
        this.postCategoryService = postCategoryService;
        this.categoryService = categoryService;
        this.postMetaService = postMetaService;
        this.postTagService = postTagService;
        this.tagService = tagService;
        this.optionService = optionService;
        this.cacheStore = cacheStore;
        this.authenticationService = authenticationService;
    }

    public String content(Post post, String token, Model model) {
        if (PostStatus.RECYCLE.equals(post.getStatus())) {
            // Articles in the recycle bin are not allowed to be accessed.
            throw new NotFoundException("查询不到该文章的信息");
        } else if (StringUtils.isNotBlank(token)) {
            // If the token is not empty, it means it is an admin request,
            // then verify the token.

            // verify token
            String cachedToken = cacheStore.getAny(token, String.class)
                .orElseThrow(() -> new ForbiddenException("您没有该文章的访问权限"));
            if (!cachedToken.equals(token)) {
                throw new ForbiddenException("您没有该文章的访问权限");
            }
        } else if (PostStatus.DRAFT.equals(post.getStatus())) {
            // Drafts are not allowed bo be accessed by outsiders.
            throw new NotFoundException("查询不到该文章的信息");
        } else if (PostStatus.INTIMATE.equals(post.getStatus())
            && !authenticationService.postAuthentication(post, null)
        ) {
            // Encrypted articles must has the correct password before they can be accessed.

            model.addAttribute("slug", post.getSlug());
            model.addAttribute("type", EncryptTypeEnum.POST.getName());
            if (themeService.templateExists(POST_PASSWORD_TEMPLATE + SUFFIX_FTL)) {
                return themeService.render(POST_PASSWORD_TEMPLATE);
            }
            return "common/template/" + POST_PASSWORD_TEMPLATE;
        }

        post = postService.getById(post.getId());

        if (post.getEditorType().equals(PostEditorType.MARKDOWN)) {
            post.setFormatContent(MarkdownUtils.renderHtml(post.getOriginalContent()));
        } else {
            post.setFormatContent(post.getOriginalContent());
        }

        postService.publishVisitEvent(post.getId());

        postService.getPrevPost(post).ifPresent(
            prevPost -> model.addAttribute("prevPost", postService.convertToDetailVo(prevPost)));
        postService.getNextPost(post).ifPresent(
            nextPost -> model.addAttribute("nextPost", postService.convertToDetailVo(nextPost)));

        List<Category> categories = postCategoryService.listCategoriesBy(post.getId(), false);
        List<Tag> tags = postTagService.listTagsBy(post.getId());
        List<PostMeta> metas = postMetaService.listBy(post.getId());

        // Generate meta keywords.
        if (StringUtils.isNotEmpty(post.getMetaKeywords())) {
            model.addAttribute("meta_keywords", post.getMetaKeywords());
        } else {
            model.addAttribute("meta_keywords",
                tags.stream().map(Tag::getName).collect(Collectors.joining(",")));
        }

        // Generate meta description.
        if (StringUtils.isNotEmpty(post.getMetaDescription())) {
            model.addAttribute("meta_description", post.getMetaDescription());
        } else {
            model.addAttribute("meta_description",
                postService.generateDescription(post.getFormatContent()));
        }

        model.addAttribute("is_post", true);
        model.addAttribute("post", postService.convertToDetailVo(post));
        model.addAttribute("categories", categoryService.convertTo(categories));
        model.addAttribute("tags", tagService.convertTo(tags));
        model.addAttribute("metas", postMetaService.convertToMap(metas));

        if (themeService.templateExists(
            ThemeService.CUSTOM_POST_PREFIX + post.getTemplate() + SUFFIX_FTL)) {
            return themeService.render(ThemeService.CUSTOM_POST_PREFIX + post.getTemplate());
        }

        return themeService.render("post");
    }

    public String list(Integer page, Model model) {
        int pageSize = optionService.getPostPageSize();
        Pageable pageable = PageRequest
            .of(page >= 1 ? page - 1 : page, pageSize, postService.getPostDefaultSort());

        Page<Post> postPage = postService.pageBy(PostStatus.PUBLISHED, pageable);
        Page<PostListVO> posts = postService.convertToListVo(postPage);

        model.addAttribute("is_index", true);
        model.addAttribute("posts", posts);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("index");
    }

    public String archives(Integer page, Model model) {
        int pageSize = optionService.getArchivesPageSize();
        Pageable pageable = PageRequest
            .of(page >= 1 ? page - 1 : page, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));

        Page<Post> postPage = postService.pageBy(PostStatus.PUBLISHED, pageable);

        Page<PostListVO> posts = postService.convertToListVo(postPage);

        List<ArchiveYearVO> archives = postService.convertToYearArchives(postPage.getContent());

        model.addAttribute("is_archives", true);
        model.addAttribute("posts", posts);
        model.addAttribute("archives", archives);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("archives");
    }
}
