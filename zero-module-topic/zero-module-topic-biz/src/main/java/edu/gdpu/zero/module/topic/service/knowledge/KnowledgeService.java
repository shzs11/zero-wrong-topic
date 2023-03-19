package edu.gdpu.zero.module.topic.service.knowledge;

import java.util.*;
import javax.validation.*;
import edu.gdpu.zero.module.topic.controller.admin.knowledge.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

/**
 * 知识点 Service 接口
 *
 * @author zero
 */
public interface KnowledgeService {

    /**
     * 创建知识点
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createKnowledge(@Valid KnowledgeCreateReqVO createReqVO);

    /**
     * 更新知识点
     *
     * @param updateReqVO 更新信息
     */
    void updateKnowledge(@Valid KnowledgeUpdateReqVO updateReqVO);

    /**
     * 删除知识点
     *
     * @param id 编号
     */
    void deleteKnowledge(Long id);

    /**
     * 获得知识点
     *
     * @param id 编号
     * @return 知识点
     */
    KnowledgeDO getKnowledge(Long id);

    /**
     * 获得知识点列表
     *
     * @param ids 编号
     * @return 知识点列表
     */
    List<KnowledgeDO> getKnowledgeList(Collection<Long> ids);

    /**
     * 获得知识点分页
     *
     * @param pageReqVO 分页查询
     * @return 知识点分页
     */
    PageResult<KnowledgeDO> getKnowledgePage(KnowledgePageReqVO pageReqVO);

    /**
     * 获得知识点列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 知识点列表
     */
    List<KnowledgeDO> getKnowledgeList(KnowledgeExportReqVO exportReqVO);

}
