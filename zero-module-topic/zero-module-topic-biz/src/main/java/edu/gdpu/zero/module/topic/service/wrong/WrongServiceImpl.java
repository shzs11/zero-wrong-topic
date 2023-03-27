package edu.gdpu.zero.module.topic.service.wrong;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.wrong.WrongConvert;
import edu.gdpu.zero.module.topic.dal.mysql.wrong.WrongMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 错题关联 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class WrongServiceImpl implements WrongService {

    @Resource
    private WrongMapper wrongMapper;

    @Override
    public Long createWrong(WrongCreateReqVO createReqVO) {
        // 插入
        WrongDO wrong = WrongConvert.INSTANCE.convert(createReqVO);
        wrongMapper.insert(wrong);
        // 返回
        return wrong.getId();
    }

    @Override
    public void updateWrong(WrongUpdateReqVO updateReqVO) {
        // 校验存在
        validateWrongExists(updateReqVO.getId());
        // 更新
        WrongDO updateObj = WrongConvert.INSTANCE.convert(updateReqVO);
        wrongMapper.updateById(updateObj);
    }

    @Override
    public void deleteWrong(Long id) {
        // 校验存在
        validateWrongExists(id);
        // 删除
        wrongMapper.deleteById(id);
    }

    private void validateWrongExists(Long id) {
        if (wrongMapper.selectById(id) == null) {
            throw exception(WRONG_NOT_EXISTS);
        }
    }

    @Override
    public WrongDO getWrong(Long id) {
        return wrongMapper.selectById(id);
    }

    @Override
    public List<WrongDO> getWrongList(Collection<Long> ids) {
        return wrongMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<WrongDO> getWrongPage(WrongPageReqVO pageReqVO) {
        return wrongMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WrongDO> getWrongList(WrongExportReqVO exportReqVO) {
        return wrongMapper.selectList(exportReqVO);
    }

}
