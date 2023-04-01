package edu.gdpu.zero.module.topic.service.wrong;

import edu.gdpu.zero.module.topic.convert.selection.SelectionConvert;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
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
            selection.setDifficulty(0L);
            selection.setName("1");
            selection.setOptionsA(createReqVO.getOptionsA());
            selection.setOptionsB(createReqVO.getOptionsB());
            selection.setOptionsC(createReqVO.getOptionsC());
            selection.setOptionsD(createReqVO.getOptionsD());
            selection.setCommentId(0L);
            selection.setTags(createReqVO.getTags());
            selection.setSubjectId(createReqVO.getSubjectId());
            selection.setKnowledgeId(createReqVO.getKnowledgeId());
            selection.setAnswer(createReqVO.getAnswer());

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
