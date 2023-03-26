package edu.gdpu.zero.module.topic.dal.mysql.interlocution;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;

/**
 * 问答题 Mapper
 *
 * @author zero
 */
@Mapper
public interface InterlocutionMapper extends BaseMapperX<InterlocutionDO> {

    default PageResult<InterlocutionDO> selectPage(InterlocutionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InterlocutionDO>()
                .likeIfPresent(InterlocutionDO::getContent, reqVO.getContent())
                .eqIfPresent(InterlocutionDO::getTags, reqVO.getTags())
                .eqIfPresent(InterlocutionDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(InterlocutionDO::getIsWrong, reqVO.getIsWrong())
                .eqIfPresent(InterlocutionDO::getKnowledgeId, reqVO.getKnowledgeId())
                .eqIfPresent(InterlocutionDO::getDifficulty, reqVO.getDifficulty())
                .betweenIfPresent(InterlocutionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InterlocutionDO::getId));
    }

    default List<InterlocutionDO> selectList(InterlocutionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<InterlocutionDO>()
                .likeIfPresent(InterlocutionDO::getContent, reqVO.getContent())
                .eqIfPresent(InterlocutionDO::getTags, reqVO.getTags())
                .eqIfPresent(InterlocutionDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(InterlocutionDO::getIsWrong, reqVO.getIsWrong())
                .eqIfPresent(InterlocutionDO::getKnowledgeId, reqVO.getKnowledgeId())
                .eqIfPresent(InterlocutionDO::getDifficulty, reqVO.getDifficulty())
                .betweenIfPresent(InterlocutionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InterlocutionDO::getId));
    }

}
