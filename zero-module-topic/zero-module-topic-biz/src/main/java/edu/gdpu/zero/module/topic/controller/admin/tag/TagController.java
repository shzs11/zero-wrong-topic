package edu.gdpu.zero.module.topic.controller.admin.tag;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.common.pojo.CommonResult;
import static edu.gdpu.zero.framework.common.pojo.CommonResult.success;

import edu.gdpu.zero.framework.excel.core.util.ExcelUtils;

import edu.gdpu.zero.framework.operatelog.core.annotations.OperateLog;
import static edu.gdpu.zero.framework.operatelog.core.enums.OperateTypeEnum.*;

import edu.gdpu.zero.module.topic.controller.admin.tag.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;
import edu.gdpu.zero.module.topic.convert.tag.TagConvert;
import edu.gdpu.zero.module.topic.service.tag.TagService;

@Tag(name = "管理后台 - 标签")
@RestController
@RequestMapping("/topic/tag")
@Validated
public class TagController {

    @Resource
    private TagService tagService;

    @PostMapping("/create")
    @Operation(summary = "创建标签")
    @PreAuthorize("@ss.hasPermission('topic:tag:create')")
    public CommonResult<Long> createTag(@Valid @RequestBody TagCreateReqVO createReqVO) {
        return success(tagService.createTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新标签")
    @PreAuthorize("@ss.hasPermission('topic:tag:update')")
    public CommonResult<Boolean> updateTag(@Valid @RequestBody TagUpdateReqVO updateReqVO) {
        tagService.updateTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('topic:tag:delete')")
    public CommonResult<Boolean> deleteTag(@RequestParam("id") Long id) {
        tagService.deleteTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('topic:tag:query')")
    public CommonResult<TagRespVO> getTag(@RequestParam("id") Long id) {
        TagDO tag = tagService.getTag(id);
        return success(TagConvert.INSTANCE.convert(tag));
    }

    @GetMapping("/list")
    @Operation(summary = "获得标签列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('topic:tag:query')")
    public CommonResult<List<TagRespVO>> getTagList(@RequestParam("ids") Collection<Long> ids) {
        List<TagDO> list = tagService.getTagList(ids);
        return success(TagConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得标签分页")
    @PreAuthorize("@ss.hasPermission('topic:tag:query')")
    public CommonResult<PageResult<TagRespVO>> getTagPage(@Valid TagPageReqVO pageVO) {
        PageResult<TagDO> pageResult = tagService.getTagPage(pageVO);
        return success(TagConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出标签 Excel")
    @PreAuthorize("@ss.hasPermission('topic:tag:export')")
    @OperateLog(type = EXPORT)
    public void exportTagExcel(@Valid TagExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TagDO> list = tagService.getTagList(exportReqVO);
        // 导出 Excel
        List<TagExcelVO> datas = TagConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "标签.xls", "数据", TagExcelVO.class, datas);
    }

}
