package edu.gdpu.zero.module.topic.service.interlocution;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.interlocution.InterlocutionConvert;
import edu.gdpu.zero.module.topic.dal.mysql.interlocution.InterlocutionMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 问答题 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class InterlocutionServiceImpl implements InterlocutionService {

    @Resource
    private InterlocutionMapper interlocutionMapper;

    @Override
    public Long createInterlocution(InterlocutionCreateReqVO createReqVO) {
        // 插入
        InterlocutionDO interlocution = InterlocutionConvert.INSTANCE.convert(createReqVO);
        interlocution.setIsWrong(new Byte("0"));
        interlocution.setCommentId(0L);
        interlocutionMapper.insert(interlocution);
        // 返回
        return interlocution.getId();
    }

    @Override
    public void updateInterlocution(InterlocutionUpdateReqVO updateReqVO) {
        // 校验存在
        validateInterlocutionExists(updateReqVO.getId());
        // 更新
        InterlocutionDO updateObj = InterlocutionConvert.INSTANCE.convert(updateReqVO);
        interlocutionMapper.updateById(updateObj);
    }

    @Override
    public void deleteInterlocution(Long id) {
        // 校验存在
        validateInterlocutionExists(id);
        // 删除
        interlocutionMapper.deleteById(id);
    }

    private void validateInterlocutionExists(Long id) {
        if (interlocutionMapper.selectById(id) == null) {
            throw exception(INTERLOCUTION_NOT_EXISTS);
        }
    }

    @Override
    public InterlocutionDO getInterlocution(Long id) {
        return interlocutionMapper.selectById(id);
    }

    @Override
    public List<InterlocutionDO> getInterlocutionList(Collection<Long> ids) {
        return interlocutionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<InterlocutionDO> getInterlocutionPage(InterlocutionPageReqVO pageReqVO) {
        return interlocutionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InterlocutionDO> getInterlocutionList(InterlocutionExportReqVO exportReqVO) {
        return interlocutionMapper.selectList(exportReqVO);
    }

}
