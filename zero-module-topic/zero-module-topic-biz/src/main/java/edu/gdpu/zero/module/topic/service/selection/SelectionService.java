package edu.gdpu.zero.module.topic.service.selection;

import java.util.*;
import javax.validation.*;
import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

/**
 * 选择题 Service 接口
 *
 * @author zero
 */
public interface SelectionService {

    /**
     * 创建选择题
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSelection(@Valid SelectionCreateReqVO createReqVO);

    /**
     * 更新选择题
     *
     * @param updateReqVO 更新信息
     */
    void updateSelection(@Valid SelectionUpdateReqVO updateReqVO);

    /**
     * 删除选择题
     *
     * @param id 编号
     */
    void deleteSelection(Long id);

    /**
     * 获得选择题
     *
     * @param id 编号
     * @return 选择题
     */
    SelectionDO getSelection(Long id);

    /**
     * 获得选择题列表
     *
     * @param ids 编号
     * @return 选择题列表
     */
    List<SelectionDO> getSelectionList(Collection<Long> ids);

    /**
     * 获得选择题分页
     *
     * @param pageReqVO 分页查询
     * @return 选择题分页
     */
    PageResult<SelectionDO> getSelectionPage(SelectionPageReqVO pageReqVO);

    /**
     * 获得选择题列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 选择题列表
     */
    List<SelectionDO> getSelectionList(SelectionExportReqVO exportReqVO);

}
