package edu.gdpu.zero.module.system.dal.mysql.sensitiveword;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.mybatis.core.mapper.BaseMapperX;
import edu.gdpu.zero.framework.mybatis.core.query.LambdaQueryWrapperX;
import edu.gdpu.zero.module.system.controller.admin.sensitiveword.vo.SensitiveWordExportReqVO;
import edu.gdpu.zero.module.system.controller.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import edu.gdpu.zero.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 敏感词 Mapper
 *
 * @author 永不言败
 */
@Mapper
public interface SensitiveWordMapper extends BaseMapperX<SensitiveWordDO> {

    default PageResult<SensitiveWordDO> selectPage(SensitiveWordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SensitiveWordDO>()
                .likeIfPresent(SensitiveWordDO::getName, reqVO.getName())
                .likeIfPresent(SensitiveWordDO::getTags, reqVO.getTag())
                .eqIfPresent(SensitiveWordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SensitiveWordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SensitiveWordDO::getId));
    }

    default List<SensitiveWordDO> selectList(SensitiveWordExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SensitiveWordDO>()
                .likeIfPresent(SensitiveWordDO::getName, reqVO.getName())
                .likeIfPresent(SensitiveWordDO::getTags, reqVO.getTag())
                .eqIfPresent(SensitiveWordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SensitiveWordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SensitiveWordDO::getId));
    }

    default SensitiveWordDO selectByName(String name) {
        return selectOne(SensitiveWordDO::getName, name);
    }

}
