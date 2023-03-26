package edu.gdpu.zero.module.topic.service.interlocution;

import java.util.*;
import javax.validation.*;
import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

/**
 * 问答题 Service 接口
 *
 * @author zero
 */
public interface InterlocutionService {

    /**
     * 创建问答题
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInterlocution(@Valid InterlocutionCreateReqVO createReqVO);

    /**
     * 更新问答题
     *
     * @param updateReqVO 更新信息
     */
    void updateInterlocution(@Valid InterlocutionUpdateReqVO updateReqVO);

    /**
     * 删除问答题
     *
     * @param id 编号
     */
    void deleteInterlocution(Long id);

    /**
     * 获得问答题
     *
     * @param id 编号
     * @return 问答题
     */
    InterlocutionDO getInterlocution(Long id);

    /**
     * 获得问答题列表
     *
     * @param ids 编号
     * @return 问答题列表
     */
    List<InterlocutionDO> getInterlocutionList(Collection<Long> ids);

    /**
     * 获得问答题分页
     *
     * @param pageReqVO 分页查询
     * @return 问答题分页
     */
    PageResult<InterlocutionDO> getInterlocutionPage(InterlocutionPageReqVO pageReqVO);

    /**
     * 获得问答题列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 问答题列表
     */
    List<InterlocutionDO> getInterlocutionList(InterlocutionExportReqVO exportReqVO);

}
