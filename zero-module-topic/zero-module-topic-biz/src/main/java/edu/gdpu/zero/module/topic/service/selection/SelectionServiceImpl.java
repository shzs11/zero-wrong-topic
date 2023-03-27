package edu.gdpu.zero.module.topic.service.selection;

import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import edu.gdpu.zero.module.topic.dal.mysql.knowledge.KnowledgeMapper;
import edu.gdpu.zero.module.topic.dal.mysql.subject.SubjectMapper;
import edu.gdpu.zero.module.topic.dal.mysql.tag.TagMapper;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.selection.SelectionConvert;
import edu.gdpu.zero.module.topic.dal.mysql.selection.SelectionMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 选择题 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class SelectionServiceImpl implements SelectionService {

    @Resource
    private SelectionMapper selectionMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long createSelection(SelectionCreateReqVO createReqVO) {
        // 插入
        SelectionDO selection = SelectionConvert.INSTANCE.convert(createReqVO);
        //时间戳生成唯一id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        selection.setId(new Long(sdf.format(System.currentTimeMillis())));
        selection.setCommentId(0L);
        selection.setIsWrong(new Byte("0"));
        selectionMapper.insert(selection);
        // 返回
        return selection.getId();
    }

    @Override
    public void updateSelection(SelectionUpdateReqVO updateReqVO) {
        // 校验存在
        validateSelectionExists(updateReqVO.getId());
        // 更新
        SelectionDO updateObj = SelectionConvert.INSTANCE.convert(updateReqVO);
        selectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteSelection(Long id) {
        // 校验存在
        validateSelectionExists(id);
        // 删除
        selectionMapper.deleteById(id);
    }

    private void validateSelectionExists(Long id) {
        if (selectionMapper.selectById(id) == null) {
            throw exception(SELECTION_NOT_EXISTS);
        }
    }

    @Override
    public SelectionDO getSelection(Long id) {
        return selectionMapper.selectById(id);
    }

    @Override
    public List<SelectionDO> getSelectionList(Collection<Long> ids) {
        return selectionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SelectionDO> getSelectionPage(SelectionPageReqVO pageReqVO) {


        return selectionMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<SelectionRespVO> getSelectionPage2(SelectionPageReqVO pageReqVO) {
        PageResult<SelectionDO> selectionDOPageResult = selectionMapper.selectPage(pageReqVO);
        PageResult<SelectionRespVO> selectionRespVOPageResult = SelectionConvert.INSTANCE.convertPage(selectionDOPageResult);
        List<SelectionRespVO> list = selectionRespVOPageResult.getList();
        for(int i = 0; i< list.size();i++){
            SelectionRespVO respVO = list.get(i);
            //翻译
            SubjectDO subjectDO = subjectMapper.selectById(respVO.getSubjectId());
            respVO.setNameOfSubject(subjectDO.getName());


            KnowledgeDO knowledgeDO = knowledgeMapper.selectById(respVO.getKnowledgeId());
            respVO.setNameOfKnowledge(knowledgeDO.getName());

            TagDO tagDO = tagMapper.selectById(respVO.getTags());
            respVO.setNameOfTag(tagDO.getName());
            list.set(i,respVO);

        }

        PageResult<SelectionRespVO> selectionRespVOPageResult1 = new PageResult<>(list, selectionDOPageResult.getTotal());


        return selectionRespVOPageResult1;
    }

    @Override
    public List<SelectionDO> getSelectionList(SelectionExportReqVO exportReqVO) {
        return selectionMapper.selectList(exportReqVO);
    }

}
