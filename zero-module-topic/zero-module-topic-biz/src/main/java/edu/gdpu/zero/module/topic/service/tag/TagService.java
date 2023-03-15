package edu.gdpu.zero.module.topic.service.tag;

import java.util.*;
import javax.validation.*;
import edu.gdpu.zero.module.topic.controller.admin.tag.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

/**
 * 标签 Service 接口
 *
 * @author zero
 */
public interface TagService {

    /**
     * 创建标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTag(@Valid TagCreateReqVO createReqVO);

    /**
     * 更新标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTag(@Valid TagUpdateReqVO updateReqVO);

    /**
     * 删除标签
     *
     * @param id 编号
     */
    void deleteTag(Long id);

    /**
     * 获得标签
     *
     * @param id 编号
     * @return 标签
     */
    TagDO getTag(Long id);

    /**
     * 获得标签列表
     *
     * @param ids 编号
     * @return 标签列表
     */
    List<TagDO> getTagList(Collection<Long> ids);

    /**
     * 获得标签分页
     *
     * @param pageReqVO 分页查询
     * @return 标签分页
     */
    PageResult<TagDO> getTagPage(TagPageReqVO pageReqVO);

    /**
     * 获得标签列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 标签列表
     */
    List<TagDO> getTagList(TagExportReqVO exportReqVO);

}
