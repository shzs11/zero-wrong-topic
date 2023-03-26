package edu.gdpu.zero.module.topic.service.interlocution;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import edu.gdpu.zero.module.topic.dal.mysql.interlocution.InterlocutionMapper;
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
* {@link InterlocutionServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(InterlocutionServiceImpl.class)
public class InterlocutionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private InterlocutionServiceImpl interlocutionService;

    @Resource
    private InterlocutionMapper interlocutionMapper;

    @Test
    public void testCreateInterlocution_success() {
        // 准备参数
        InterlocutionCreateReqVO reqVO = randomPojo(InterlocutionCreateReqVO.class);

        // 调用
        Long interlocutionId = interlocutionService.createInterlocution(reqVO);
        // 断言
        assertNotNull(interlocutionId);
        // 校验记录的属性是否正确
        InterlocutionDO interlocution = interlocutionMapper.selectById(interlocutionId);
        assertPojoEquals(reqVO, interlocution);
    }

    @Test
    public void testUpdateInterlocution_success() {
        // mock 数据
        InterlocutionDO dbInterlocution = randomPojo(InterlocutionDO.class);
        interlocutionMapper.insert(dbInterlocution);// @Sql: 先插入出一条存在的数据
        // 准备参数
        InterlocutionUpdateReqVO reqVO = randomPojo(InterlocutionUpdateReqVO.class, o -> {
            o.setId(dbInterlocution.getId()); // 设置更新的 ID
        });

        // 调用
        interlocutionService.updateInterlocution(reqVO);
        // 校验是否更新正确
        InterlocutionDO interlocution = interlocutionMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, interlocution);
    }

    @Test
    public void testUpdateInterlocution_notExists() {
        // 准备参数
        InterlocutionUpdateReqVO reqVO = randomPojo(InterlocutionUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> interlocutionService.updateInterlocution(reqVO), INTERLOCUTION_NOT_EXISTS);
    }

    @Test
    public void testDeleteInterlocution_success() {
        // mock 数据
        InterlocutionDO dbInterlocution = randomPojo(InterlocutionDO.class);
        interlocutionMapper.insert(dbInterlocution);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbInterlocution.getId();

        // 调用
        interlocutionService.deleteInterlocution(id);
       // 校验数据不存在了
       assertNull(interlocutionMapper.selectById(id));
    }

    @Test
    public void testDeleteInterlocution_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> interlocutionService.deleteInterlocution(id), INTERLOCUTION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetInterlocutionPage() {
       // mock 数据
       InterlocutionDO dbInterlocution = randomPojo(InterlocutionDO.class, o -> { // 等会查询到
           o.setContent(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setIsWrong(null);
           o.setKnowledgeId(null);
           o.setDifficulty(null);
           o.setCreateTime(null);
       });
       interlocutionMapper.insert(dbInterlocution);
       // 测试 content 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setContent(null)));
       // 测试 tags 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setSubjectId(null)));
       // 测试 isWrong 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setIsWrong(null)));
       // 测试 knowledgeId 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setKnowledgeId(null)));
       // 测试 difficulty 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setDifficulty(null)));
       // 测试 createTime 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setCreateTime(null)));
       // 准备参数
       InterlocutionPageReqVO reqVO = new InterlocutionPageReqVO();
       reqVO.setContent(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setIsWrong(null);
       reqVO.setKnowledgeId(null);
       reqVO.setDifficulty(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<InterlocutionDO> pageResult = interlocutionService.getInterlocutionPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbInterlocution, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetInterlocutionList() {
       // mock 数据
       InterlocutionDO dbInterlocution = randomPojo(InterlocutionDO.class, o -> { // 等会查询到
           o.setContent(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setIsWrong(null);
           o.setKnowledgeId(null);
           o.setDifficulty(null);
           o.setCreateTime(null);
       });
       interlocutionMapper.insert(dbInterlocution);
       // 测试 content 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setContent(null)));
       // 测试 tags 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setSubjectId(null)));
       // 测试 isWrong 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setIsWrong(null)));
       // 测试 knowledgeId 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setKnowledgeId(null)));
       // 测试 difficulty 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setDifficulty(null)));
       // 测试 createTime 不匹配
       interlocutionMapper.insert(cloneIgnoreId(dbInterlocution, o -> o.setCreateTime(null)));
       // 准备参数
       InterlocutionExportReqVO reqVO = new InterlocutionExportReqVO();
       reqVO.setContent(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setIsWrong(null);
       reqVO.setKnowledgeId(null);
       reqVO.setDifficulty(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<InterlocutionDO> list = interlocutionService.getInterlocutionList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbInterlocution, list.get(0));
    }

}
