package edu.gdpu.zero.module.topic.dal.mysql.knowledge;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.knowledge.vo.*;

/**
 * 知识点 Mapper
 *
 * @author zero
 */
@Mapper
public interface KnowledgeMapper extends BaseMapperX<KnowledgeDO> {

    default PageResult<KnowledgeDO> selectPage(KnowledgePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<KnowledgeDO>()
                .eqIfPresent(KnowledgeDO::getSubjectId, reqVO.getSubjectId())
                .likeIfPresent(KnowledgeDO::getName, reqVO.getName())
                .betweenIfPresent(KnowledgeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(KnowledgeDO::getId));
    }

    default List<KnowledgeDO> selectList(KnowledgeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<KnowledgeDO>()
                .eqIfPresent(KnowledgeDO::getSubjectId, reqVO.getSubjectId())
                .likeIfPresent(KnowledgeDO::getName, reqVO.getName())
                .betweenIfPresent(KnowledgeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(KnowledgeDO::getId));
    }

}
