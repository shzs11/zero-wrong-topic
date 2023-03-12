package edu.gdpu.zero.module.topic.dal.mysql.subject;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;
import org.apache.ibatis.annotations.Mapper;
import edu.gdpu.zero.module.topic.controller.admin.subject.vo.*;

/**
 * 科目 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SubjectMapper extends BaseMapperX<SubjectDO> {

    default PageResult<SubjectDO> selectPage(SubjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SubjectDO>()
                .likeIfPresent(SubjectDO::getName, reqVO.getName())
                .eqIfPresent(SubjectDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(SubjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SubjectDO::getId));
    }

    default List<SubjectDO> selectList(SubjectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SubjectDO>()
                .likeIfPresent(SubjectDO::getName, reqVO.getName())
                .eqIfPresent(SubjectDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(SubjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SubjectDO::getId));
    }

}
