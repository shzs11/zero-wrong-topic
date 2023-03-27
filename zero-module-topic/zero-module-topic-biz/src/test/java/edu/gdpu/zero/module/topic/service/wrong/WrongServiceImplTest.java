package edu.gdpu.zero.module.topic.service.wrong;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;
import edu.gdpu.zero.module.topic.dal.mysql.wrong.WrongMapper;
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
* {@link WrongServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(WrongServiceImpl.class)
public class WrongServiceImplTest extends BaseDbUnitTest {

    @Resource
    private WrongServiceImpl wrongService;

    @Resource
    private WrongMapper wrongMapper;

    @Test
    public void testCreateWrong_success() {
        // 准备参数
        WrongCreateReqVO reqVO = randomPojo(WrongCreateReqVO.class);

        // 调用
        Long wrongId = wrongService.createWrong(reqVO);
        // 断言
        assertNotNull(wrongId);
        // 校验记录的属性是否正确
        WrongDO wrong = wrongMapper.selectById(wrongId);
        assertPojoEquals(reqVO, wrong);
    }

    @Test
    public void testUpdateWrong_success() {
        // mock 数据
        WrongDO dbWrong = randomPojo(WrongDO.class);
        wrongMapper.insert(dbWrong);// @Sql: 先插入出一条存在的数据
        // 准备参数
        WrongUpdateReqVO reqVO = randomPojo(WrongUpdateReqVO.class, o -> {
            o.setId(dbWrong.getId()); // 设置更新的 ID
        });

        // 调用
        wrongService.updateWrong(reqVO);
        // 校验是否更新正确
        WrongDO wrong = wrongMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, wrong);
    }

    @Test
    public void testUpdateWrong_notExists() {
        // 准备参数
        WrongUpdateReqVO reqVO = randomPojo(WrongUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> wrongService.updateWrong(reqVO), WRONG_NOT_EXISTS);
    }

    @Test
    public void testDeleteWrong_success() {
        // mock 数据
        WrongDO dbWrong = randomPojo(WrongDO.class);
        wrongMapper.insert(dbWrong);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbWrong.getId();

        // 调用
        wrongService.deleteWrong(id);
       // 校验数据不存在了
       assertNull(wrongMapper.selectById(id));
    }

    @Test
    public void testDeleteWrong_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> wrongService.deleteWrong(id), WRONG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetWrongPage() {
       // mock 数据
       WrongDO dbWrong = randomPojo(WrongDO.class, o -> { // 等会查询到
           o.setTopicId(null);
           o.setCorrectAnswer(null);
           o.setUserId(null);
           o.setTopicType(null);
           o.setSummary(null);
           o.setPracticeCount(null);
           o.setCreateTime(null);
       });
       wrongMapper.insert(dbWrong);
       // 测试 topicId 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setTopicId(null)));
       // 测试 correctAnswer 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setCorrectAnswer(null)));
       // 测试 userId 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setUserId(null)));
       // 测试 topicType 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setTopicType(null)));
       // 测试 summary 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setSummary(null)));
       // 测试 practiceCount 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setPracticeCount(null)));
       // 测试 createTime 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setCreateTime(null)));
       // 准备参数
       WrongPageReqVO reqVO = new WrongPageReqVO();
       reqVO.setTopicId(null);
       reqVO.setCorrectAnswer(null);
       reqVO.setUserId(null);
       reqVO.setTopicType(null);
       reqVO.setSummary(null);
       reqVO.setPracticeCount(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<WrongDO> pageResult = wrongService.getWrongPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbWrong, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetWrongList() {
       // mock 数据
       WrongDO dbWrong = randomPojo(WrongDO.class, o -> { // 等会查询到
           o.setTopicId(null);
           o.setCorrectAnswer(null);
           o.setUserId(null);
           o.setTopicType(null);
           o.setSummary(null);
           o.setPracticeCount(null);
           o.setCreateTime(null);
       });
       wrongMapper.insert(dbWrong);
       // 测试 topicId 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setTopicId(null)));
       // 测试 correctAnswer 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setCorrectAnswer(null)));
       // 测试 userId 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setUserId(null)));
       // 测试 topicType 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setTopicType(null)));
       // 测试 summary 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setSummary(null)));
       // 测试 practiceCount 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setPracticeCount(null)));
       // 测试 createTime 不匹配
       wrongMapper.insert(cloneIgnoreId(dbWrong, o -> o.setCreateTime(null)));
       // 准备参数
       WrongExportReqVO reqVO = new WrongExportReqVO();
       reqVO.setTopicId(null);
       reqVO.setCorrectAnswer(null);
       reqVO.setUserId(null);
       reqVO.setTopicType(null);
       reqVO.setSummary(null);
       reqVO.setPracticeCount(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<WrongDO> list = wrongService.getWrongList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbWrong, list.get(0));
    }

}
