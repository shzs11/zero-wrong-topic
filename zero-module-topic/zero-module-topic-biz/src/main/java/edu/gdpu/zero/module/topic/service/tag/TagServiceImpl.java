package edu.gdpu.zero.module.topic.service.tag;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.tag.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.tag.TagConvert;
import edu.gdpu.zero.module.topic.dal.mysql.tag.TagMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 标签 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long createTag(TagCreateReqVO createReqVO) {
        // 插入
        TagDO tag = TagConvert.INSTANCE.convert(createReqVO);
        tagMapper.insert(tag);
        // 返回
        return tag.getId();
    }

    @Override
    public void updateTag(TagUpdateReqVO updateReqVO) {
        // 校验存在
        validateTagExists(updateReqVO.getId());
        // 更新
        TagDO updateObj = TagConvert.INSTANCE.convert(updateReqVO);
        tagMapper.updateById(updateObj);
    }

    @Override
    public void deleteTag(Long id) {
        // 校验存在
        validateTagExists(id);
        // 删除
        tagMapper.deleteById(id);
    }

    private void validateTagExists(Long id) {
        if (tagMapper.selectById(id) == null) {
            throw exception(TAG_NOT_EXISTS);
        }
    }

    @Override
    public TagDO getTag(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public List<TagDO> getTagList(Collection<Long> ids) {
        return tagMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TagDO> getTagPage(TagPageReqVO pageReqVO) {
        return tagMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TagDO> getTagList(TagExportReqVO exportReqVO) {
        return tagMapper.selectList(exportReqVO);
    }

}
