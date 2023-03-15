package edu.gdpu.zero.module.topic.service.tag;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import edu.gdpu.zero.framework.test.core.ut.BaseDbUnitTest;

import edu.gdpu.zero.module.topic.controller.admin.tag.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import edu.gdpu.zero.module.topic.dal.mysql.tag.TagMapper;
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
* {@link TagServiceImpl} 的单元测试类
*
* @author zero
*/
@Import(TagServiceImpl.class)
public class TagServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TagServiceImpl tagService;

    @Resource
    private TagMapper tagMapper;

    @Test
    public void testCreateTag_success() {
        // 准备参数
        TagCreateReqVO reqVO = randomPojo(TagCreateReqVO.class);

        // 调用
        Long tagId = tagService.createTag(reqVO);
        // 断言
        assertNotNull(tagId);
        // 校验记录的属性是否正确
        TagDO tag = tagMapper.selectById(tagId);
        assertPojoEquals(reqVO, tag);
    }

    @Test
    public void testUpdateTag_success() {
        // mock 数据
        TagDO dbTag = randomPojo(TagDO.class);
        tagMapper.insert(dbTag);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TagUpdateReqVO reqVO = randomPojo(TagUpdateReqVO.class, o -> {
            o.setId(dbTag.getId()); // 设置更新的 ID
        });

        // 调用
        tagService.updateTag(reqVO);
        // 校验是否更新正确
        TagDO tag = tagMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, tag);
    }

    @Test
    public void testUpdateTag_notExists() {
        // 准备参数
        TagUpdateReqVO reqVO = randomPojo(TagUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> tagService.updateTag(reqVO), TAG_NOT_EXISTS);
    }

    @Test
    public void testDeleteTag_success() {
        // mock 数据
        TagDO dbTag = randomPojo(TagDO.class);
        tagMapper.insert(dbTag);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTag.getId();

        // 调用
        tagService.deleteTag(id);
       // 校验数据不存在了
       assertNull(tagMapper.selectById(id));
    }

    @Test
    public void testDeleteTag_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> tagService.deleteTag(id), TAG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTagPage() {
       // mock 数据
       TagDO dbTag = randomPojo(TagDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setCreateTime(null);
       });
       tagMapper.insert(dbTag);
       // 测试 name 不匹配
       tagMapper.insert(cloneIgnoreId(dbTag, o -> o.setName(null)));
       // 测试 createTime 不匹配
       tagMapper.insert(cloneIgnoreId(dbTag, o -> o.setCreateTime(null)));
       // 准备参数
       TagPageReqVO reqVO = new TagPageReqVO();
       reqVO.setName(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<TagDO> pageResult = tagService.getTagPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbTag, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetTagList() {
       // mock 数据
       TagDO dbTag = randomPojo(TagDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setCreateTime(null);
       });
       tagMapper.insert(dbTag);
       // 测试 name 不匹配
       tagMapper.insert(cloneIgnoreId(dbTag, o -> o.setName(null)));
       // 测试 createTime 不匹配
       tagMapper.insert(cloneIgnoreId(dbTag, o -> o.setCreateTime(null)));
       // 准备参数
       TagExportReqVO reqVO = new TagExportReqVO();
       reqVO.setName(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<TagDO> list = tagService.getTagList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbTag, list.get(0));
    }

}
