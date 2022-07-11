package com.qinweizhao.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinweizhao.blog.mapper.TagMapper;
import com.qinweizhao.blog.model.entity.Tag;
import com.qinweizhao.blog.service.OptionService;
import com.qinweizhao.blog.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TagService implementation class.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-14
 */
@Slf4j
@Service
@AllArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    private final OptionService optionService;


//    @Override
//    @Transactional
//    public Tag create(Tag tag) {
//        // Check if the tag is exist
//        long count = tagRepository.countByNameOrSlug(tag.getName(), tag.getSlug());
//
//        log.debug("Tag count: [{}]", count);
//
//        if (count > 0) {
//            // If the tag has exist already
//            throw new AlreadyExistsException("该标签已存在").setErrorData(tag);
//        }
//
//        // Get tag name
//        return super.create(tag);
//    }
//
//    @Override
//    public Tag getBySlugOfNonNull(String slug) {
//        return tagRepository.getBySlug(slug).orElseThrow(() -> new NotFoundException("查询不到该标签的信息").setErrorData(slug));
//    }
//
//    @Override
//    public Tag getBySlug(String slug) {
//        return tagRepository.getBySlug(slug).orElse(null);
//    }
//
//    @Override
//    public Tag getByName(String name) {
//        return tagRepository.getByName(name).orElse(null);
//    }
//
//    @Override
//    public TagDTO convertTo(Tag tag) {
//        Assert.notNull(tag, "Tag must not be null");
//
//        TagDTO tagDTO = new TagDTO().convertFrom(tag);
//
//        StringBuilder fullPath = new StringBuilder();
//
//        if (optionService.isEnabledAbsolutePath()) {
//            fullPath.append(optionService.getBlogBaseUrl());
//        }
//
//        fullPath.append(URL_SEPARATOR)
//                .append(optionService.getTagsPrefix())
//                .append(URL_SEPARATOR)
//                .append(tag.getSlug())
//                .append(optionService.getPathSuffix());
//
//        tagDTO.setFullPath(fullPath.toString());
//
//        return tagDTO;
//    }
//
//    @Override
//    public List<TagDTO> convertTo(List<Tag> tags) {
//        if (CollectionUtils.isEmpty(tags)) {
//            return Collections.emptyList();
//        }
//
//        return tags.stream()
//                .map(this::convertTo)
//                .collect(Collectors.toList());
//    }
}
