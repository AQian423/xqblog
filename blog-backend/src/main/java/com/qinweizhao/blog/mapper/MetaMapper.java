package com.qinweizhao.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qinweizhao.blog.model.entity.Meta;
import com.qinweizhao.blog.utils.LambdaQueryWrapperX;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qinweizhao
 * @since 2022-07-08
 */
public interface MetaMapper extends BaseMapper<Meta> {


    /**
     * 通过 postIds 获取元数据集合
     * @param postIds postIds
     * @return List
     */
    default List<Meta> selectListByPostIds(Set<Integer> postIds) {
        return this.selectList(new LambdaQueryWrapperX<Meta>()
                .in(Meta::getPostId, postIds)
        );
    }

}
