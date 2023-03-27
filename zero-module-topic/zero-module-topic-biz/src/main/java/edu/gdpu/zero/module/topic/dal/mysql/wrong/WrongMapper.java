package edu.gdpu.zero.module.topic.dal.mysql.wrong;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;

/**
 * 错题关联 Mapper
 *
 * @author zero
 */
@Mapper
public interface WrongMapper extends BaseMapperX<WrongDO> {

    default PageResult<WrongDO> selectPage(WrongPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WrongDO>()
                .eqIfPresent(WrongDO::getTopicId, reqVO.getTopicId())
                .eqIfPresent(WrongDO::getCorrectAnswer, reqVO.getCorrectAnswer())
                .eqIfPresent(WrongDO::getUserId, reqVO.getUserId())
                .eqIfPresent(WrongDO::getTopicType, reqVO.getTopicType())
                .eqIfPresent(WrongDO::getSummary, reqVO.getSummary())
                .eqIfPresent(WrongDO::getPracticeCount, reqVO.getPracticeCount())
                .betweenIfPresent(WrongDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WrongDO::getId));
    }

    default List<WrongDO> selectList(WrongExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WrongDO>()
                .eqIfPresent(WrongDO::getTopicId, reqVO.getTopicId())
                .eqIfPresent(WrongDO::getCorrectAnswer, reqVO.getCorrectAnswer())
                .eqIfPresent(WrongDO::getUserId, reqVO.getUserId())
                .eqIfPresent(WrongDO::getTopicType, reqVO.getTopicType())
                .eqIfPresent(WrongDO::getSummary, reqVO.getSummary())
                .eqIfPresent(WrongDO::getPracticeCount, reqVO.getPracticeCount())
                .betweenIfPresent(WrongDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WrongDO::getId));
    }

}
