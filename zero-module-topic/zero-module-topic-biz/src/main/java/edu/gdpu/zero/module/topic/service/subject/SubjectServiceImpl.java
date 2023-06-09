package edu.gdpu.zero.module.topic.service.subject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.gdpu.zero.framework.mybatis.core.query.QueryWrapperX;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectAndKnowledge;
import edu.gdpu.zero.module.topic.dal.mysql.knowledge.KnowledgeMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.subject.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.subject.SubjectConvert;
import edu.gdpu.zero.module.topic.dal.mysql.subject.SubjectMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 科目 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Override
    public Long createSubject(SubjectCreateReqVO createReqVO) {
        // 插入
        SubjectDO subject = SubjectConvert.INSTANCE.convert(createReqVO);
        subjectMapper.insert(subject);
        // 返回
        return subject.getId();
    }

    @Override
    public void updateSubject(SubjectUpdateReqVO updateReqVO) {
        // 校验存在
        validateSubjectExists(updateReqVO.getId());
        // 更新
        SubjectDO updateObj = SubjectConvert.INSTANCE.convert(updateReqVO);
        subjectMapper.updateById(updateObj);
    }

    @Override
    public void deleteSubject(Long id) {
        // 校验存在
        validateSubjectExists(id);
        // 删除
        subjectMapper.deleteById(id);
    }

    private void validateSubjectExists(Long id) {
        if (subjectMapper.selectById(id) == null) {
            throw exception(SUBJECT_NOT_EXISTS);
        }
    }

    @Override
    public SubjectDO getSubject(Long id) {
        return subjectMapper.selectById(id);
    }

    @Override
    public List<SubjectDO> getSubjectList(Collection<Long> ids) {
        return subjectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SubjectDO> getSubjectPage(SubjectPageReqVO pageReqVO) {
        return subjectMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SubjectAndKnowledge> getSubjectAndKnowledge() {

        //获取所有科目信息
        List<SubjectDO> subjectDOS = subjectMapper.selectList();

        List<SubjectAndKnowledge> result = new ArrayList<>();

        for(int i = 0 ;i < subjectDOS.size();i++){
            SubjectAndKnowledge subjectAndKnowledge = new SubjectAndKnowledge();
            subjectAndKnowledge.setId(subjectDOS.get(i).getId());
            subjectAndKnowledge.setName(subjectDOS.get(i).getName());

            //获取关联知识点信息
            List<KnowledgeDO> knowledgeDOList = knowledgeMapper.selectList(
                    new QueryWrapper<KnowledgeDO>().eq("subject_id", subjectDOS.get(i).getId().toString()));
            subjectAndKnowledge.setKnowledgeDOList(knowledgeDOList);

            result.add(i,subjectAndKnowledge);

        }

        return result;
    }

    @Override
    public List<SubjectDO> getSubjectList(SubjectExportReqVO exportReqVO) {
        return subjectMapper.selectList(exportReqVO);
    }

}
