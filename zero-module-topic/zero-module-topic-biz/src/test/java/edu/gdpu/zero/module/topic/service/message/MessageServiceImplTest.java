package edu.gdpu.zero.module.topic.service.message;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.message.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.message.MessageDO;
import edu.gdpu.zero.module.topic.dal.mysql.message.MessageMapper;
import edu.gdpu.zero.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static edu.gdpu.zero.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static edu.gdpu.zero.module.topic.enums.ErrorCodeConstants.*;
import static edu.gdpu.zero.framework.test.core.util.AssertUtils.*;
import static edu.gdpu.zero.framework.test.core.util.RandomUtils.*;
import static edu.gdpu.zero.framework.common.util.object.ObjectUtils.*;
import static edu.gdpu.zero.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link MessageServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(MessageServiceImpl.class)
public class MessageServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MessageServiceImpl messageService;

    @Resource
    private MessageMapper messageMapper;

    @Test
    public void testCreateMessage_success() {
        // 准备参数
        MessageCreateReqVO reqVO = randomPojo(MessageCreateReqVO.class);

        // 调用
        Long messageId = messageService.createMessage(reqVO);
        // 断言
        assertNotNull(messageId);
        // 校验记录的属性是否正确
        MessageDO message = messageMapper.selectById(messageId);
        assertPojoEquals(reqVO, message);
    }

    @Test
    public void testUpdateMessage_success() {
        // mock 数据
        MessageDO dbMessage = randomPojo(MessageDO.class);
        messageMapper.insert(dbMessage);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MessageUpdateReqVO reqVO = randomPojo(MessageUpdateReqVO.class, o -> {
            o.setId(dbMessage.getId()); // 设置更新的 ID
        });

        // 调用
        messageService.updateMessage(reqVO);
        // 校验是否更新正确
        MessageDO message = messageMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, message);
    }

    @Test
    public void testUpdateMessage_notExists() {
        // 准备参数
        MessageUpdateReqVO reqVO = randomPojo(MessageUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> messageService.updateMessage(reqVO), MESSAGE_NOT_EXISTS);
    }

    @Test
    public void testDeleteMessage_success() {
        // mock 数据
        MessageDO dbMessage = randomPojo(MessageDO.class);
        messageMapper.insert(dbMessage);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMessage.getId();

        // 调用
        messageService.deleteMessage(id);
       // 校验数据不存在了
       assertNull(messageMapper.selectById(id));
    }

    @Test
    public void testDeleteMessage_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> messageService.deleteMessage(id), MESSAGE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMessagePage() {
       // mock 数据
       MessageDO dbMessage = randomPojo(MessageDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setUserId(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setIsPublic(null);
           o.setCreateTime(null);
       });
       messageMapper.insert(dbMessage);
       // 测试 name 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setName(null)));
       // 测试 userId 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setUserId(null)));
       // 测试 tags 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setSubjectId(null)));
       // 测试 isPublic 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setIsPublic(null)));
       // 测试 createTime 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setCreateTime(null)));
       // 准备参数
       MessagePageReqVO reqVO = new MessagePageReqVO();
       reqVO.setName(null);
       reqVO.setUserId(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setIsPublic(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<MessageDO> pageResult = messageService.getMessagePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMessage, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMessageList() {
       // mock 数据
       MessageDO dbMessage = randomPojo(MessageDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setUserId(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setIsPublic(null);
           o.setCreateTime(null);
       });
       messageMapper.insert(dbMessage);
       // 测试 name 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setName(null)));
       // 测试 userId 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setUserId(null)));
       // 测试 tags 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setSubjectId(null)));
       // 测试 isPublic 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setIsPublic(null)));
       // 测试 createTime 不匹配
       messageMapper.insert(cloneIgnoreId(dbMessage, o -> o.setCreateTime(null)));
       // 准备参数
       MessageExportReqVO reqVO = new MessageExportReqVO();
       reqVO.setName(null);
       reqVO.setUserId(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setIsPublic(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<MessageDO> list = messageService.getMessageList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbMessage, list.get(0));
    }

}
