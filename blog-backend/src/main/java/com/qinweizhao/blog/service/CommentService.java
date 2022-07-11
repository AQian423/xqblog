package com.qinweizhao.blog.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qinweizhao.blog.model.base.BaseEntity;
import com.qinweizhao.blog.model.entity.Comment;

/**
 * Post comment service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-14
 */
public interface CommentService<C1 extends BaseMapper<Comment>, C extends BaseEntity> extends IService<Comment> {

//
//    /**
//     * 分页
//     *
//     * @param pageable     pageable
//     * @param commentQuery commentQuery
//     * @return Page
//     */
//    Page<Comment> page(Pageable pageable, CommentQuery commentQuery);
//
//    /**
//     * 验证 CommentBlackList 状态
//     */
//    void validateCommentBlackListStatus();
//
//
//    /**
//     * 校验目标
//     *
//     * @param postId postId
//     */
//    void validateTarget(Integer postId);
//
//    /**
//     * 通过状态统计
//     *
//     * @param published published
//     * @return long
//     */
//    long countByStatus(CommentStatus published);
}
