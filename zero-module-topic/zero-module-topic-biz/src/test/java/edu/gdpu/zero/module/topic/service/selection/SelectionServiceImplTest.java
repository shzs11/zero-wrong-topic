package edu.gdpu.zero.module.topic.service.selection;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import edu.gdpu.zero.module.topic.dal.mysql.selection.SelectionMapper;
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
* {@link SelectionServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(SelectionServiceImpl.class)
public class SelectionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SelectionServiceImpl selectionService;

    @Resource
    private SelectionMapper selectionMapper;

    @Test
    public void testCreateSelection_success() {
        // 准备参数
        SelectionCreateReqVO reqVO = randomPojo(SelectionCreateReqVO.class);

        // 调用
        Long selectionId = selectionService.createSelection(reqVO);
        // 断言
        assertNotNull(selectionId);
        // 校验记录的属性是否正确
        SelectionDO selection = selectionMapper.selectById(selectionId);
        assertPojoEquals(reqVO, selection);
    }

    @Test
    public void testUpdateSelection_success() {
        // mock 数据
        SelectionDO dbSelection = randomPojo(SelectionDO.class);
        selectionMapper.insert(dbSelection);// @Sql: 先插入出一条存在的数据
        // 准备参数
        SelectionUpdateReqVO reqVO = randomPojo(SelectionUpdateReqVO.class, o -> {
            o.setId(dbSelection.getId()); // 设置更新的 ID
        });

        // 调用
        selectionService.updateSelection(reqVO);
        // 校验是否更新正确
        SelectionDO selection = selectionMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, selection);
    }

    @Test
    public void testUpdateSelection_notExists() {
        // 准备参数
        SelectionUpdateReqVO reqVO = randomPojo(SelectionUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> selectionService.updateSelection(reqVO), SELECTION_NOT_EXISTS);
    }

    @Test
    public void testDeleteSelection_success() {
        // mock 数据
        SelectionDO dbSelection = randomPojo(SelectionDO.class);
        selectionMapper.insert(dbSelection);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbSelection.getId();

        // 调用
        selectionService.deleteSelection(id);
       // 校验数据不存在了
       assertNull(selectionMapper.selectById(id));
    }

    @Test
    public void testDeleteSelection_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> selectionService.deleteSelection(id), SELECTION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSelectionPage() {
       // mock 数据
       SelectionDO dbSelection = randomPojo(SelectionDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setKnowledgeId(null);
           o.setDifficulty(null);
           o.setCreateTime(null);
       });
       selectionMapper.insert(dbSelection);
       // 测试 name 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setName(null)));
       // 测试 tags 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setSubjectId(null)));
       // 测试 knowledgeId 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setKnowledgeId(null)));
       // 测试 difficulty 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setDifficulty(null)));
       // 测试 createTime 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setCreateTime(null)));
       // 准备参数
       SelectionPageReqVO reqVO = new SelectionPageReqVO();
       reqVO.setName(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setKnowledgeId(null);
       reqVO.setDifficulty(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<SelectionDO> pageResult = selectionService.getSelectionPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbSelection, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetSelectionList() {
       // mock 数据
       SelectionDO dbSelection = randomPojo(SelectionDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setTags(null);
           o.setSubjectId(null);
           o.setKnowledgeId(null);
           o.setDifficulty(null);
           o.setCreateTime(null);
       });
       selectionMapper.insert(dbSelection);
       // 测试 name 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setName(null)));
       // 测试 tags 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setTags(null)));
       // 测试 subjectId 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setSubjectId(null)));
       // 测试 knowledgeId 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setKnowledgeId(null)));
       // 测试 difficulty 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setDifficulty(null)));
       // 测试 createTime 不匹配
       selectionMapper.insert(cloneIgnoreId(dbSelection, o -> o.setCreateTime(null)));
       // 准备参数
       SelectionExportReqVO reqVO = new SelectionExportReqVO();
       reqVO.setName(null);
       reqVO.setTags(null);
       reqVO.setSubjectId(null);
       reqVO.setKnowledgeId(null);
       reqVO.setDifficulty(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<SelectionDO> list = selectionService.getSelectionList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbSelection, list.get(0));
    }

}
