package edu.gdpu.zero.module.topic.dal.mysql.judgment;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.judgment.vo.*;

/**
 * 判断题 Mapper
 *
 * @author zero
 */
@Mapper
public interface JudgmentMapper extends BaseMapperX<JudgmentDO> {

    default PageResult<JudgmentDO> selectPage(JudgmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<JudgmentDO>()
                .likeIfPresent(JudgmentDO::getName, reqVO.getName())
                .eqIfPresent(JudgmentDO::getAnswer, reqVO.getAnswer())
                .eqIfPresent(JudgmentDO::getTags, reqVO.getTags())
                .eqIfPresent(JudgmentDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(JudgmentDO::getKnowledgeId, reqVO.getKnowledgeId())
                .eqIfPresent(JudgmentDO::getDifficulty, reqVO.getDifficulty())
                .eqIfPresent(JudgmentDO::getIsWrong, reqVO.getIsWrong())
                .betweenIfPresent(JudgmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(JudgmentDO::getId));
    }

    default List<JudgmentDO> selectList(JudgmentExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<JudgmentDO>()
                .likeIfPresent(JudgmentDO::getName, reqVO.getName())
                .eqIfPresent(JudgmentDO::getAnswer, reqVO.getAnswer())
                .eqIfPresent(JudgmentDO::getTags, reqVO.getTags())
                .eqIfPresent(JudgmentDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(JudgmentDO::getKnowledgeId, reqVO.getKnowledgeId())
                .eqIfPresent(JudgmentDO::getDifficulty, reqVO.getDifficulty())
                .eqIfPresent(JudgmentDO::getIsWrong, reqVO.getIsWrong())
                .betweenIfPresent(JudgmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(JudgmentDO::getId));
    }

}
