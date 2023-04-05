package edu.gdpu.zero.module.topic.service.wrong;

import edu.gdpu.zero.module.topic.convert.selection.SelectionConvert;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import edu.gdpu.zero.module.topic.dal.mysql.interlocution.InterlocutionMapper;
import edu.gdpu.zero.module.topic.dal.mysql.judgment.JudgmentMapper;
import edu.gdpu.zero.module.topic.dal.mysql.selection.SelectionMapper;
import org.springframework.expression.spel.ast.Selection;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.wrong.WrongConvert;
import edu.gdpu.zero.module.topic.dal.mysql.wrong.WrongMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 错题关联 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class WrongServiceImpl implements WrongService {

    @Resource
    private WrongMapper wrongMapper;

    @Resource
    private SelectionMapper selectionMapper;

    @Resource
    private JudgmentMapper judgmentMapper;

    @Resource
    private InterlocutionMapper interlocutionMapper;

    @Override
    public Long createWrong(WrongCreateReqVO createReqVO) {
        // 插入
        //如果是选择题，插入选择题表
        if(createReqVO.getTopicType()!=null&&createReqVO.getTopicType()==0L){
            //时间戳生成唯一id
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
            Long id = new Long(sdf.format(System.currentTimeMillis()));
            //插入选择题表
            SelectionDO selection = new SelectionDO();
            selection.setId(id);
            selection.setIsWrong(new Byte("1"));
            selection.setDifficulty(createReqVO.getDifficulty());
            selection.setName(createReqVO.getSelectionName());
            selection.setOptionsA(createReqVO.getOptionsA());
            selection.setOptionsB(createReqVO.getOptionsB());
            selection.setOptionsC(createReqVO.getOptionsC());
            selection.setOptionsD(createReqVO.getOptionsD());
            selection.setCommentId(0L);
            selection.setTags(createReqVO.getTags());
            selection.setSubjectId(createReqVO.getSubjectId());
            selection.setKnowledgeId(createReqVO.getKnowledgeId());
            selection.setAnswer(createReqVO.getSelectionAnswer());

            selectionMapper.insert(selection);

            //插入错题表
            WrongDO wrong = new WrongDO();
            //插入题号
            wrong.setTopicId(id);
            wrong.setCorrectAnswer(createReqVO.getCorrectAnswer());
            wrong.setPracticeCount(0);
            wrong.setTopicType(0L);
            wrong.setUserId(getLoginUserId());
            wrong.setSummary(createReqVO.getSummary());

            wrongMapper.insert(wrong);
        }else if(createReqVO.getTopicType()!=null&&createReqVO.getTopicType()==1L){//如果是判断题

            //时间戳生成唯一id
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
            Long id = new Long(sdf.format(System.currentTimeMillis()));

            //插入判断题表
            JudgmentDO judgment = new JudgmentDO();
            judgment.setId(id);
            judgment.setName(createReqVO.getJudgementName());
            judgment.setAnswer(createReqVO.getJudgeAnswer());
            judgment.setIsWrong(new Byte("1"));
            judgment.setDifficulty(createReqVO.getDifficulty());
            judgment.setSubjectId(createReqVO.getSubjectId());
            judgment.setTags(createReqVO.getTags());
            judgment.setKnowledgeId(createReqVO.getKnowledgeId());

            //插入判断题表
            judgmentMapper.insert(judgment);

            //插入错题表
            WrongDO wrong = new WrongDO();
            //插入题号
            wrong.setTopicId(id);
            wrong.setCorrectAnswer(createReqVO.getJudgeCorrectAnswer());//判断题错误答案
            wrong.setPracticeCount(0);
            wrong.setTopicType(1L);
            wrong.setUserId(getLoginUserId());
            wrong.setSummary(createReqVO.getSummary());
            wrongMapper.insert(wrong);
        }else if(createReqVO.getTopicType()!=null&&createReqVO.getTopicType()==2L){//如果是问答题
            //时间戳生成唯一id
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
            Long id = new Long(sdf.format(System.currentTimeMillis()));

            //插入问答题表
            InterlocutionDO interlocution = new InterlocutionDO();
            interlocution.setId(id);
            interlocution.setIsWrong(new Byte("1"));
            interlocution.setContent(createReqVO.getContent());
            interlocution.setAnswer(createReqVO.getInterAnswer());
            interlocution.setCommentId(0L);
            interlocution.setDifficulty(createReqVO.getDifficulty());
            interlocution.setSubjectId(createReqVO.getSubjectId());
            interlocution.setKnowledgeId(createReqVO.getKnowledgeId());
            interlocution.setTags(createReqVO.getTags());
            interlocution.setCommentId(0L);

            //插入数据
            interlocutionMapper.insert(interlocution);

            //插入错题表
            WrongDO wrong = new WrongDO();
            //插入题号
            wrong.setTopicId(id);
            wrong.setCorrectAnswer(createReqVO.getInterCorrectAnswer());//问答题错误答案
            wrong.setPracticeCount(0);
            //错题类型
            wrong.setTopicType(2L);
            wrong.setUserId(getLoginUserId());
            wrong.setSummary(createReqVO.getSummary());
            wrongMapper.insert(wrong);

        }
        WrongDO wrong = WrongConvert.INSTANCE.convert(createReqVO);

        // 返回
        return wrong.getId();
    }

    @Override
    public void updateWrong(WrongUpdateReqVO updateReqVO) {
        // 校验存在
        validateWrongExists(updateReqVO.getId());
        // 更新
        WrongDO updateObj = WrongConvert.INSTANCE.convert(updateReqVO);
        wrongMapper.updateById(updateObj);
    }

    @Override
    public void deleteWrong(Long id) {
        // 校验存在
        validateWrongExists(id);
        // 删除
        wrongMapper.deleteById(id);
    }

    private void validateWrongExists(Long id) {
        if (wrongMapper.selectById(id) == null) {
            throw exception(WRONG_NOT_EXISTS);
        }
    }

    @Override
    public WrongDO getWrong(Long id) {
        return wrongMapper.selectById(id);
    }

    @Override
    public List<WrongDO> getWrongList(Collection<Long> ids) {
        return wrongMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<WrongDO> getWrongPage(WrongPageReqVO pageReqVO) {
        return wrongMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WrongDO> getWrongList(WrongExportReqVO exportReqVO) {
        return wrongMapper.selectList(exportReqVO);
    }

}
