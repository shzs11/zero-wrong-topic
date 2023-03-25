package edu.gdpu.zero.module.topic.service.judgment;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.judgment.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.judgment.JudgmentConvert;
import edu.gdpu.zero.module.topic.dal.mysql.judgment.JudgmentMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 判断题 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class JudgmentServiceImpl implements JudgmentService {

    @Resource
    private JudgmentMapper judgmentMapper;

    @Override
    public Long createJudgment(JudgmentCreateReqVO createReqVO) {
        // 插入
        JudgmentDO judgment = JudgmentConvert.INSTANCE.convert(createReqVO);
        judgment.setIsWrong(new Byte("0"));
        judgmentMapper.insert(judgment);
        // 返回
        return judgment.getId();
    }

    @Override
    public void updateJudgment(JudgmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateJudgmentExists(updateReqVO.getId());
        // 更新
        JudgmentDO updateObj = JudgmentConvert.INSTANCE.convert(updateReqVO);
        judgmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteJudgment(Long id) {
        // 校验存在
        validateJudgmentExists(id);
        // 删除
        judgmentMapper.deleteById(id);
    }

    private void validateJudgmentExists(Long id) {
        if (judgmentMapper.selectById(id) == null) {
            throw exception(JUDGMENT_NOT_EXISTS);
        }
    }

    @Override
    public JudgmentDO getJudgment(Long id) {
        return judgmentMapper.selectById(id);
    }

    @Override
    public List<JudgmentDO> getJudgmentList(Collection<Long> ids) {
        return judgmentMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<JudgmentDO> getJudgmentPage(JudgmentPageReqVO pageReqVO) {
        return judgmentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<JudgmentDO> getJudgmentList(JudgmentExportReqVO exportReqVO) {
        return judgmentMapper.selectList(exportReqVO);
    }

}
