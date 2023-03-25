package edu.gdpu.zero.module.topic.service.judgment;

import java.util.*;
import javax.validation.*;
import edu.gdpu.zero.module.topic.controller.admin.judgment.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

/**
 * 判断题 Service 接口
 *
 * @author zero
 */
public interface JudgmentService {

    /**
     * 创建判断题
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createJudgment(@Valid JudgmentCreateReqVO createReqVO);

    /**
     * 更新判断题
     *
     * @param updateReqVO 更新信息
     */
    void updateJudgment(@Valid JudgmentUpdateReqVO updateReqVO);

    /**
     * 删除判断题
     *
     * @param id 编号
     */
    void deleteJudgment(Long id);

    /**
     * 获得判断题
     *
     * @param id 编号
     * @return 判断题
     */
    JudgmentDO getJudgment(Long id);

    /**
     * 获得判断题列表
     *
     * @param ids 编号
     * @return 判断题列表
     */
    List<JudgmentDO> getJudgmentList(Collection<Long> ids);

    /**
     * 获得判断题分页
     *
     * @param pageReqVO 分页查询
     * @return 判断题分页
     */
    PageResult<JudgmentDO> getJudgmentPage(JudgmentPageReqVO pageReqVO);

    /**
     * 获得判断题分页2
     *
     * @param pageReqVO 分页查询
     * @return 判断题分页
     */
    PageResult<JudgmentRespVO> getJudgmentPage2(JudgmentPageReqVO pageReqVO);

    /**
     * 获得判断题列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 判断题列表
     */
    List<JudgmentDO> getJudgmentList(JudgmentExportReqVO exportReqVO);

}
