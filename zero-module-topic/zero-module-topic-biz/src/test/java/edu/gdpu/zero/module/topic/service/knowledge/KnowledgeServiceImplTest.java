package edu.gdpu.zero.module.topic.service.knowledge;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.knowledge.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.module.topic.dal.mysql.knowledge.KnowledgeMapper;
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
* {@link KnowledgeServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(KnowledgeServiceImpl.class)
public class KnowledgeServiceImplTest extends BaseDbUnitTest {

    @Resource
    private KnowledgeServiceImpl knowledgeService;

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Test
    public void testCreateKnowledge_success() {
        // 准备参数
        KnowledgeCreateReqVO reqVO = randomPojo(KnowledgeCreateReqVO.class);

        // 调用
        Long knowledgeId = knowledgeService.createKnowledge(reqVO);
        // 断言
        assertNotNull(knowledgeId);
        // 校验记录的属性是否正确
        KnowledgeDO knowledge = knowledgeMapper.selectById(knowledgeId);
        assertPojoEquals(reqVO, knowledge);
    }

    @Test
    public void testUpdateKnowledge_success() {
        // mock 数据
        KnowledgeDO dbKnowledge = randomPojo(KnowledgeDO.class);
        knowledgeMapper.insert(dbKnowledge);// @Sql: 先插入出一条存在的数据
        // 准备参数
        KnowledgeUpdateReqVO reqVO = randomPojo(KnowledgeUpdateReqVO.class, o -> {
            o.setId(dbKnowledge.getId()); // 设置更新的 ID
        });

        // 调用
        knowledgeService.updateKnowledge(reqVO);
        // 校验是否更新正确
        KnowledgeDO knowledge = knowledgeMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, knowledge);
    }

    @Test
    public void testUpdateKnowledge_notExists() {
        // 准备参数
        KnowledgeUpdateReqVO reqVO = randomPojo(KnowledgeUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> knowledgeService.updateKnowledge(reqVO), KNOWLEDGE_NOT_EXISTS);
    }

    @Test
    public void testDeleteKnowledge_success() {
        // mock 数据
        KnowledgeDO dbKnowledge = randomPojo(KnowledgeDO.class);
        knowledgeMapper.insert(dbKnowledge);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbKnowledge.getId();

        // 调用
        knowledgeService.deleteKnowledge(id);
       // 校验数据不存在了
       assertNull(knowledgeMapper.selectById(id));
    }

    @Test
    public void testDeleteKnowledge_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> knowledgeService.deleteKnowledge(id), KNOWLEDGE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetKnowledgePage() {
       // mock 数据
       KnowledgeDO dbKnowledge = randomPojo(KnowledgeDO.class, o -> { // 等会查询到
           o.setSubjectId(null);
           o.setName(null);
           o.setCreateTime(null);
       });
       knowledgeMapper.insert(dbKnowledge);
       // 测试 subjectId 不匹配
       knowledgeMapper.insert(cloneIgnoreId(dbKnowledge, o -> o.setSubjectId(null)));
       // 测试 name 不匹配
       knowledgeMapper.insert(cloneIgnoreId(dbKnowledge, o -> o.setName(null)));
       // 测试 createTime 不匹配
       knowledgeMapper.insert(cloneIgnoreId(dbKnowledge, o -> o.setCreateTime(null)));
       // 准备参数
       KnowledgePageReqVO reqVO = new KnowledgePageReqVO();
       reqVO.setSubjectId(null);
       reqVO.setName(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<KnowledgeDO> pageResult = knowledgeService.getKnowledgePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbKnowledge, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetKnowledgeList() {
       // mock 数据
       KnowledgeDO dbKnowledge = randomPojo(KnowledgeDO.class, o -> { // 等会查询到
           o.setSubjectId(null);
           o.setName(null);
           o.setCreateTime(null);
       });
       knowledgeMapper.insert(dbKnowledge);
       // 测试 subjectId 不匹配
       knowledgeMapper.insert(cloneIgnoreId(dbKnowledge, o -> o.setSubjectId(null)));
       // 测试 name 不匹配
       knowledgeMapper.insert(cloneIgnoreId(dbKnowledge, o -> o.setName(null)));
       // 测试 createTime 不匹配
       knowledgeMapper.insert(cloneIgnoreId(dbKnowledge, o -> o.setCreateTime(null)));
       // 准备参数
       KnowledgeExportReqVO reqVO = new KnowledgeExportReqVO();
       reqVO.setSubjectId(null);
       reqVO.setName(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<KnowledgeDO> list = knowledgeService.getKnowledgeList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbKnowledge, list.get(0));
    }

}
