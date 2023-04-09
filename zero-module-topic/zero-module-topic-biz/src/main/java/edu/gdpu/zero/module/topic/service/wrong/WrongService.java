package edu.gdpu.zero.module.topic.service.wrong;

import java.util.*;
import javax.validation.*;
import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

/**
 * 错题关联 Service 接口
 *
 * @author zero
 */
public interface WrongService {

    /**
     * 创建错题关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWrong(@Valid WrongCreateReqVO createReqVO);

    /**
     * 更新错题关联
     *
     * @param updateReqVO 更新信息
     */
    void updateWrong(@Valid WrongUpdateReqVO updateReqVO);

    /**
     * 删除错题关联
     *
     * @param id 编号
     */
    void deleteWrong(Long id);

    /**
     * 删除错题关联
     *
     * @param id 编号
     */
    void deleteJudgeWrong(Long id);

    /**
     * 获得错题关联
     *
     * @param id 编号
     * @return 错题关联
     */
    WrongDO getWrong(Long id);

    /**
     * 获得错题选择题
     *
     * @param id 编号
     * @return 错题关联
     */
    WrongRespVO getWrong2(Long id);


    /**
     * 获得错题选择题
     *
     * @param id 编号
     * @return 错题关联
     */
    WrongJudgeRespVO getJudgeWrong(Long id);

    /**
     * 获得错题关联列表
     *
     * @param ids 编号
     * @return 错题关联列表
     */
    List<WrongDO> getWrongList(Collection<Long> ids);

    /**
     * 获得错题关联分页
     *
     * @param pageReqVO 分页查询
     * @return 错题关联分页
     */
    PageResult<WrongDO> getWrongPage(WrongPageReqVO pageReqVO);

    /**
     * 获得错题
     *
     * @param pageReqVO 分页查询
     * @return 错题关联分页
     */
    PageResult<WrongRespVO> getWrongPage2(WrongPageReqVO pageReqVO);

    /**
     * 获得判断题错题
     *
     * @param pageReqVO 分页查询
     * @return 错题关联分页
     */
    PageResult<WrongJudgeRespVO> getJudgeWrongPage(WrongPageReqVO pageReqVO);

    /**
     * 获得错题关联列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 错题关联列表
     */
    List<WrongDO> getWrongList(WrongExportReqVO exportReqVO);

}
