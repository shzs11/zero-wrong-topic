package edu.gdpu.zero.module.topic.dal.mysql.tag;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.tag.vo.*;

/**
 * 标签 Mapper
 *
 * @author zero
 */
@Mapper
public interface TagMapper extends BaseMapperX<TagDO> {

    default PageResult<TagDO> selectPage(TagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagDO>()
                .likeIfPresent(TagDO::getName, reqVO.getName())
                .betweenIfPresent(TagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagDO::getId));
    }

    default List<TagDO> selectList(TagExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TagDO>()
                .likeIfPresent(TagDO::getName, reqVO.getName())
                .betweenIfPresent(TagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagDO::getId));
    }

}
