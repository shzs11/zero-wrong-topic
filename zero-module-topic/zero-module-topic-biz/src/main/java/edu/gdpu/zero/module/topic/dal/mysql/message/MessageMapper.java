package edu.gdpu.zero.module.topic.dal.mysql.message;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.message.MessageDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.message.vo.*;

/**
 * 错题 Mapper
 *
 * @author zero
 */
@Mapper
public interface MessageMapper extends BaseMapperX<MessageDO> {

    default PageResult<MessageDO> selectPage(MessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MessageDO>()
                .likeIfPresent(MessageDO::getName, reqVO.getName())
                .eqIfPresent(MessageDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MessageDO::getTags, reqVO.getTags())
                .eqIfPresent(MessageDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(MessageDO::getIsPublic, reqVO.getIsPublic())
                .betweenIfPresent(MessageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MessageDO::getId));
    }

    default List<MessageDO> selectList(MessageExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MessageDO>()
                .likeIfPresent(MessageDO::getName, reqVO.getName())
                .eqIfPresent(MessageDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MessageDO::getTags, reqVO.getTags())
                .eqIfPresent(MessageDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(MessageDO::getIsPublic, reqVO.getIsPublic())
                .betweenIfPresent(MessageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MessageDO::getId));
    }

}
