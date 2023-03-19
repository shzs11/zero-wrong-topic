package edu.gdpu.zero.module.topic.service.selection;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.selection.SelectionConvert;
import edu.gdpu.zero.module.topic.dal.mysql.selection.SelectionMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 选择题 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class SelectionServiceImpl implements SelectionService {

    @Resource
    private SelectionMapper selectionMapper;

    @Override
    public Long createSelection(SelectionCreateReqVO createReqVO) {
        // 插入
        SelectionDO selection = SelectionConvert.INSTANCE.convert(createReqVO);
        selectionMapper.insert(selection);
        // 返回
        return selection.getId();
    }

    @Override
    public void updateSelection(SelectionUpdateReqVO updateReqVO) {
        // 校验存在
        validateSelectionExists(updateReqVO.getId());
        // 更新
        SelectionDO updateObj = SelectionConvert.INSTANCE.convert(updateReqVO);
        selectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteSelection(Long id) {
        // 校验存在
        validateSelectionExists(id);
        // 删除
        selectionMapper.deleteById(id);
    }

    private void validateSelectionExists(Long id) {
        if (selectionMapper.selectById(id) == null) {
            throw exception(SELECTION_NOT_EXISTS);
        }
    }

    @Override
    public SelectionDO getSelection(Long id) {
        return selectionMapper.selectById(id);
    }

    @Override
    public List<SelectionDO> getSelectionList(Collection<Long> ids) {
        return selectionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SelectionDO> getSelectionPage(SelectionPageReqVO pageReqVO) {
        return selectionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SelectionDO> getSelectionList(SelectionExportReqVO exportReqVO) {
        return selectionMapper.selectList(exportReqVO);
    }

}
