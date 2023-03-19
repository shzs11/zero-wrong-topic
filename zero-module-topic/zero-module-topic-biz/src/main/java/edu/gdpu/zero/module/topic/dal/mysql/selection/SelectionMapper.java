package edu.gdpu.zero.module.topic.dal.mysql.selection;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;

/**
 * 选择题 Mapper
 *
 * @author zero
 */
@Mapper
public interface SelectionMapper extends BaseMapperX<SelectionDO> {

    default PageResult<SelectionDO> selectPage(SelectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SelectionDO>()
                .likeIfPresent(SelectionDO::getName, reqVO.getName())
                .eqIfPresent(SelectionDO::getTags, reqVO.getTags())
                .eqIfPresent(SelectionDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(SelectionDO::getKnowledgeId, reqVO.getKnowledgeId())
                .eqIfPresent(SelectionDO::getDifficulty, reqVO.getDifficulty())
                .betweenIfPresent(SelectionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SelectionDO::getId));
    }

    default List<SelectionDO> selectList(SelectionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SelectionDO>()
                .likeIfPresent(SelectionDO::getName, reqVO.getName())
                .eqIfPresent(SelectionDO::getTags, reqVO.getTags())
                .eqIfPresent(SelectionDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(SelectionDO::getKnowledgeId, reqVO.getKnowledgeId())
                .eqIfPresent(SelectionDO::getDifficulty, reqVO.getDifficulty())
                .betweenIfPresent(SelectionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SelectionDO::getId));
    }

}
