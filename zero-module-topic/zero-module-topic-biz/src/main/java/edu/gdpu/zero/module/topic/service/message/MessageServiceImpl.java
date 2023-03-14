package edu.gdpu.zero.module.topic.service.message;

import edu.gdpu.zero.module.system.api.user.AdminUserApi;
import edu.gdpu.zero.module.system.api.user.dto.AdminUserRespDTO;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;
import edu.gdpu.zero.module.topic.dal.mysql.subject.SubjectMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.security.auth.Subject;

import org.springframework.validation.annotation.Validated;

import java.util.*;
import edu.gdpu.zero.module.topic.controller.admin.message.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.message.MessageDO;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import edu.gdpu.zero.module.topic.convert.message.MessageConvert;
import edu.gdpu.zero.module.topic.dal.mysql.message.MessageMapper;

import static edu.gdpu.zero.framework.common.exception.util.ServiceExceptionUtil.exception;
import static edu.gdpu.zero.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;

/**
 * 错题 Service 实现类
 *
 * @author zero
 */
@Service
@Validated
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private AdminUserApi userApi;

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Long createMessage(MessageCreateReqVO createReqVO) {
        // 插入
        MessageDO message = MessageConvert.INSTANCE.convert(createReqVO);
        message.setUserId(getLoginUserId());
        messageMapper.insert(message);
        // 返回
        return message.getId();
    }

    @Override
    public void updateMessage(MessageUpdateReqVO updateReqVO) {
        // 校验存在
        validateMessageExists(updateReqVO.getId());
        // 更新
        MessageDO updateObj = MessageConvert.INSTANCE.convert(updateReqVO);
        messageMapper.updateById(updateObj);
    }

    @Override
    public void deleteMessage(Long id) {
        // 校验存在
        validateMessageExists(id);
        // 删除
        messageMapper.deleteById(id);
    }

    private void validateMessageExists(Long id) {
        if (messageMapper.selectById(id) == null) {
            throw exception(MESSAGE_NOT_EXISTS);
        }
    }

    @Override
    public MessageDO getMessage(Long id) {
        return messageMapper.selectById(id);
    }

    @Override
    public List<MessageDO> getMessageList(Collection<Long> ids) {
        return messageMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MessageRespVO> getMessagePage(MessagePageReqVO pageReqVO) {

        PageResult<MessageDO> messageDOPageResult = messageMapper.selectPage(pageReqVO);

        PageResult<MessageRespVO> messageRespVOPageResult = MessageConvert.INSTANCE.convertPage(messageDOPageResult);
        List<MessageRespVO> list = messageRespVOPageResult.getList();
        for(int i =0 ;i < list.size();i++){
            MessageRespVO messageRespVO = list.get(i);
            //翻译userId
            AdminUserRespDTO user = userApi.getUser(messageRespVO.getUserId());
            messageRespVO.setNameOfUser(user.getNickname());
            //翻译subjectId
            SubjectDO subjectDO = subjectMapper.selectById(messageRespVO.getSubjectId());
            messageRespVO.setNameOfSubject(subjectDO.getName());
            list.set(i,messageRespVO);
        }

        PageResult<MessageRespVO> result = new PageResult<>(list, messageDOPageResult.getTotal());

        return result;
    }

    @Override
    public List<MessageDO> getMessageList(MessageExportReqVO exportReqVO) {
        return messageMapper.selectList(exportReqVO);
    }

}
