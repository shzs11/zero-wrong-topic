package edu.gdpu.zero.module.topic.service.interlocution;

import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import edu.gdpu.zero.module.topic.dal.mysql.knowledge.KnowledgeMapper;
import edu.gdpu.zero.module.topic.dal.mysql.subject.SubjectMapper;
import edu.gdpu.zero.module.topic.dal.mysql.tag.TagMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.interlocution.InterlocutionConvert;
import edu.gdpu.zero.module.topic.dal.mysql.interlocution.InterlocutionMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 问答题 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class InterlocutionServiceImpl implements InterlocutionService {

    @Resource
    private InterlocutionMapper interlocutionMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long createInterlocution(InterlocutionCreateReqVO createReqVO) {
        // 插入
        InterlocutionDO interlocution = InterlocutionConvert.INSTANCE.convert(createReqVO);
        interlocution.setIsWrong(new Byte("0"));
        interlocution.setCommentId(0L);
        interlocutionMapper.insert(interlocution);
        // 返回
        return interlocution.getId();
    }

    @Override
    public void updateInterlocution(InterlocutionUpdateReqVO updateReqVO) {
        // 校验存在
        validateInterlocutionExists(updateReqVO.getId());
        // 更新
        InterlocutionDO updateObj = InterlocutionConvert.INSTANCE.convert(updateReqVO);
        interlocutionMapper.updateById(updateObj);
    }

    @Override
    public void deleteInterlocution(Long id) {
        // 校验存在
        validateInterlocutionExists(id);
        // 删除
        interlocutionMapper.deleteById(id);
    }

    private void validateInterlocutionExists(Long id) {
        if (interlocutionMapper.selectById(id) == null) {
            throw exception(INTERLOCUTION_NOT_EXISTS);
        }
    }

    @Override
    public InterlocutionDO getInterlocution(Long id) {
        return interlocutionMapper.selectById(id);
    }

    @Override
    public List<InterlocutionDO> getInterlocutionList(Collection<Long> ids) {
        return interlocutionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<InterlocutionDO> getInterlocutionPage(InterlocutionPageReqVO pageReqVO) {
        return interlocutionMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<InterlocutionRespVO> getInterlocutionPage2(InterlocutionPageReqVO pageReqVO) {

        PageResult<InterlocutionDO> interlocutionDOPageResult = interlocutionMapper.selectPage(pageReqVO);

        PageResult<InterlocutionRespVO> interlocutionRespVOPageResult = InterlocutionConvert.INSTANCE.convertPage(interlocutionDOPageResult);

        List<InterlocutionRespVO> list = interlocutionRespVOPageResult.getList();
        for(int i = 0 ;i < list.size();i++ ){
            InterlocutionRespVO respVO = list.get(i);

            //翻译
            SubjectDO subjectDO = subjectMapper.selectById(respVO.getSubjectId());
            respVO.setNameOfSubject(subjectDO.getName());


            KnowledgeDO knowledgeDO = knowledgeMapper.selectById(respVO.getKnowledgeId());
            respVO.setNameOfKnowledge(knowledgeDO.getName());

            TagDO tagDO = tagMapper.selectById(respVO.getTags());
            respVO.setNameOfTag(tagDO.getName());
            list.set(i,respVO);
        }

        PageResult<InterlocutionRespVO> result = new PageResult<>(list, interlocutionRespVOPageResult.getTotal());


        return result;
    }

    @Override
    public List<InterlocutionDO> getInterlocutionList(InterlocutionExportReqVO exportReqVO) {
        return interlocutionMapper.selectList(exportReqVO);
    }

}
