package edu.gdpu.zero.module.topic.service.knowledge;

import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;
import edu.gdpu.zero.module.topic.dal.mysql.subject.SubjectMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.knowledge.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.knowledge.KnowledgeConvert;
import edu.gdpu.zero.module.topic.dal.mysql.knowledge.KnowledgeMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 知识点 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class KnowledgeServiceImpl implements KnowledgeService {

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Long createKnowledge(KnowledgeCreateReqVO createReqVO) {
        // 插入
        KnowledgeDO knowledge = KnowledgeConvert.INSTANCE.convert(createReqVO);
        knowledgeMapper.insert(knowledge);
        // 返回
        return knowledge.getId();
    }

    @Override
    public void updateKnowledge(KnowledgeUpdateReqVO updateReqVO) {
        // 校验存在
        validateKnowledgeExists(updateReqVO.getId());
        // 更新
        KnowledgeDO updateObj = KnowledgeConvert.INSTANCE.convert(updateReqVO);
        knowledgeMapper.updateById(updateObj);
    }

    @Override
    public void deleteKnowledge(Long id) {
        // 校验存在
        validateKnowledgeExists(id);
        // 删除
        knowledgeMapper.deleteById(id);
    }

    private void validateKnowledgeExists(Long id) {
        if (knowledgeMapper.selectById(id) == null) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
    }

    @Override
    public KnowledgeDO getKnowledge(Long id) {
        return knowledgeMapper.selectById(id);
    }

    @Override
    public List<KnowledgeDO> getKnowledgeList(Collection<Long> ids) {
        return knowledgeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<KnowledgeDO> getKnowledgePage(KnowledgePageReqVO pageReqVO) {

        PageResult<KnowledgeDO> knowledgeDOPageResult = knowledgeMapper.selectPage(pageReqVO);

        return knowledgeMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<KnowledgeRespVO> getKnowledgePage2(KnowledgePageReqVO pageReqVO) {

        PageResult<KnowledgeDO> knowledgeDOPageResult = knowledgeMapper.selectPage(pageReqVO);

        PageResult<KnowledgeRespVO> knowledgeDOPage = KnowledgeConvert.INSTANCE.convertPage(knowledgeDOPageResult);
        List<KnowledgeRespVO> list = knowledgeDOPage.getList();
        //翻译
        for(int i = 0;i < list.size();i++){
            KnowledgeRespVO knowledgeRespVO = list.get(i);
            //翻译科目id
            SubjectDO subjectDO = subjectMapper.selectById(knowledgeRespVO.getSubjectId());
            knowledgeRespVO.setNameOfSubject(subjectDO.getName());
            list.set(i,knowledgeRespVO);
        }
        PageResult<KnowledgeRespVO> result = new PageResult<>(list,knowledgeDOPage.getTotal());

        return result;
    }

    @Override
    public List<KnowledgeDO> getKnowledgeList(KnowledgeExportReqVO exportReqVO) {
        return knowledgeMapper.selectList(exportReqVO);
    }

}
