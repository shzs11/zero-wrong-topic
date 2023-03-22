package edu.gdpu.zero.module.topic.controller.admin.selection;

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

import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;
import edu.gdpu.zero.module.topic.convert.selection.SelectionConvert;
import edu.gdpu.zero.module.topic.service.selection.SelectionService;

@Tag(name = "管理后台 - 选择题")
@RestController
@RequestMapping("/topic/selection")
@Validated
public class SelectionController {

    @Resource
    private SelectionService selectionService;

    @PostMapping("/create")
    @Operation(summary = "创建选择题")
    @PreAuthorize("@ss.hasPermission('topic:selection:create')")
    public CommonResult<Long> createSelection(@Valid @RequestBody SelectionCreateReqVO createReqVO) {
        return success(selectionService.createSelection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新选择题")
    @PreAuthorize("@ss.hasPermission('topic:selection:update')")
    public CommonResult<Boolean> updateSelection(@Valid @RequestBody SelectionUpdateReqVO updateReqVO) {
        selectionService.updateSelection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除选择题")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('topic:selection:delete')")
    public CommonResult<Boolean> deleteSelection(@RequestParam("id") Long id) {
        selectionService.deleteSelection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得选择题")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('topic:selection:query')")
    public CommonResult<SelectionRespVO> getSelection(@RequestParam("id") Long id) {
        SelectionDO selection = selectionService.getSelection(id);
        SelectionRespVO convert = SelectionConvert.INSTANCE.convert(selection);
        return success(SelectionConvert.INSTANCE.convert(selection));
    }

    @GetMapping("/list")
    @Operation(summary = "获得选择题列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('topic:selection:query')")
    public CommonResult<List<SelectionRespVO>> getSelectionList(@RequestParam("ids") Collection<Long> ids) {
        List<SelectionDO> list = selectionService.getSelectionList(ids);
        return success(SelectionConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得选择题分页")
    @PreAuthorize("@ss.hasPermission('topic:selection:query')")
    public CommonResult<PageResult<SelectionRespVO>> getSelectionPage(@Valid SelectionPageReqVO pageVO) {
        PageResult<SelectionRespVO> selectionPage2 = selectionService.getSelectionPage2(pageVO);
        return success(selectionPage2);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出选择题 Excel")
    @PreAuthorize("@ss.hasPermission('topic:selection:export')")
    @OperateLog(type = EXPORT)
    public void exportSelectionExcel(@Valid SelectionExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<SelectionDO> list = selectionService.getSelectionList(exportReqVO);
        // 导出 Excel
        List<SelectionExcelVO> datas = SelectionConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "选择题.xls", "数据", SelectionExcelVO.class, datas);
    }

}
