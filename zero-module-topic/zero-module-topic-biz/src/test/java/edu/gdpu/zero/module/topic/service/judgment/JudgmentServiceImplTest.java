package edu.gdpu.zero.module.topic.service.judgment;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.judgment.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;
import edu.gdpu.zero.module.topic.dal.mysql.judgment.JudgmentMapper;
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
* {@link JudgmentServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(JudgmentServiceImpl.class)
public class JudgmentServiceImplTest extends BaseDbUnitTest {

    @Resource
    private JudgmentServiceImpl judgmentService;

    @Resource
    private JudgmentMapper judgmentMapper;

    @Test
    public void testCreateJudgment_success() {
        // 准备参数
        JudgmentCreateReqVO reqVO = randomPojo(JudgmentCreateReqVO.class);

        // 调用
        Long judgmentId = judgmentService.createJudgment(reqVO);
        // 断言
        assertNotNull(judgmentId);
        // 校验记录的属性是否正确
        JudgmentDO judgment = judgmentMapper.selectById(judgmentId);
        assertPojoEquals(reqVO, judgment);
    }

    @Test
    public void testUpdateJudgment_success() {
        // mock 数据
        JudgmentDO dbJudgment = randomPojo(JudgmentDO.class);
        judgmentMapper.insert(dbJudgment);// @Sql: 先插入出一条存在的数据
        // 准备参数
        JudgmentUpdateReqVO reqVO = randomPojo(JudgmentUpdateReqVO.class, o -> {
            o.setId(dbJudgment.getId()); // 设置更新的 ID
        });

        // 调用
        judgmentService.updateJudgment(reqVO);
        // 校验是否更新正确
        JudgmentDO judgment = judgmentMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, judgment);
    }

    @Test
    public void testUpdateJudgment_notExists() {
        // 准备参数
        JudgmentUpdateReqVO reqVO = randomPojo(JudgmentUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> judgmentService.updateJudgment(reqVO), JUDGMENT_NOT_EXISTS);
    }

    @Test
    public void testDeleteJudgment_success() {
        // mock 数据
        JudgmentDO dbJudgment = randomPojo(JudgmentDO.class);
        judgmentMapper.insert(dbJudgment);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbJudgment.getId();

        // 调用
        judgmentService.deleteJudgment(id);
       // 校验数据不存在了
       assertNull(judgmentMapper.selectById(id));
    }

    @Test
    public void testDeleteJudgment_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> judgmentService.deleteJudgment(id), JUDGMENT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetJudgmentPage() {
       // mock 数据
       JudgmentDO dbJudgment = randomPojo(JudgmentDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setAnswer(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setKnowledgeId(null);
           o.setDifficulty(null);
           o.setIsWrong(null);
           o.setCreateTime(null);
       });
       judgmentMapper.insert(dbJudgment);
       // 测试 name 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setName(null)));
       // 测试 answer 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setAnswer(null)));
       // 测试 tags 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setSubjectId(null)));
       // 测试 knowledgeId 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setKnowledgeId(null)));
       // 测试 difficulty 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setDifficulty(null)));
       // 测试 isWrong 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setIsWrong(null)));
       // 测试 createTime 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setCreateTime(null)));
       // 准备参数
       JudgmentPageReqVO reqVO = new JudgmentPageReqVO();
       reqVO.setName(null);
       reqVO.setAnswer(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setKnowledgeId(null);
       reqVO.setDifficulty(null);
       reqVO.setIsWrong(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<JudgmentDO> pageResult = judgmentService.getJudgmentPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbJudgment, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetJudgmentList() {
       // mock 数据
       JudgmentDO dbJudgment = randomPojo(JudgmentDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setAnswer(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setKnowledgeId(null);
           o.setDifficulty(null);
           o.setIsWrong(null);
           o.setCreateTime(null);
       });
       judgmentMapper.insert(dbJudgment);
       // 测试 name 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setName(null)));
       // 测试 answer 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setAnswer(null)));
       // 测试 tags 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setSubjectId(null)));
       // 测试 knowledgeId 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setKnowledgeId(null)));
       // 测试 difficulty 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setDifficulty(null)));
       // 测试 isWrong 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setIsWrong(null)));
       // 测试 createTime 不匹配
       judgmentMapper.insert(cloneIgnoreId(dbJudgment, o -> o.setCreateTime(null)));
       // 准备参数
       JudgmentExportReqVO reqVO = new JudgmentExportReqVO();
       reqVO.setName(null);
       reqVO.setAnswer(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setKnowledgeId(null);
       reqVO.setDifficulty(null);
       reqVO.setIsWrong(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<JudgmentDO> list = judgmentService.getJudgmentList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbJudgment, list.get(0));
    }

}
