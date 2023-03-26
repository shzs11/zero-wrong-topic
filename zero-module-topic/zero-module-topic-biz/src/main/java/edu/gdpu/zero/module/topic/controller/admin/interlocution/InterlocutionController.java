package edu.gdpu.zero.module.topic.controller.admin.interlocution;

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

import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;
import edu.gdpu.zero.module.topic.convert.interlocution.InterlocutionConvert;
import edu.gdpu.zero.module.topic.service.interlocution.InterlocutionService;

@Tag(name = "管理后台 - 问答题")
@RestController
@RequestMapping("/topic/interlocution")
@Validated
public class InterlocutionController {

    @Resource
    private InterlocutionService interlocutionService;

    @PostMapping("/create")
    @Operation(summary = "创建问答题")
    @PreAuthorize("@ss.hasPermission('topic:interlocution:create')")
    public CommonResult<Long> createInterlocution(@Valid @RequestBody InterlocutionCreateReqVO createReqVO) {
        return success(interlocutionService.createInterlocution(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新问答题")
    @PreAuthorize("@ss.hasPermission('topic:interlocution:update')")
    public CommonResult<Boolean> updateInterlocution(@Valid @RequestBody InterlocutionUpdateReqVO updateReqVO) {
        interlocutionService.updateInterlocution(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除问答题")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('topic:interlocution:delete')")
    public CommonResult<Boolean> deleteInterlocution(@RequestParam("id") Long id) {
        interlocutionService.deleteInterlocution(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得问答题")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('topic:interlocution:query')")
    public CommonResult<InterlocutionRespVO> getInterlocution(@RequestParam("id") Long id) {
        InterlocutionDO interlocution = interlocutionService.getInterlocution(id);
        return success(InterlocutionConvert.INSTANCE.convert(interlocution));
    }

    @GetMapping("/list")
    @Operation(summary = "获得问答题列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('topic:interlocution:query')")
    public CommonResult<List<InterlocutionRespVO>> getInterlocutionList(@RequestParam("ids") Collection<Long> ids) {
        List<InterlocutionDO> list = interlocutionService.getInterlocutionList(ids);
        return success(InterlocutionConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得问答题分页")
    @PreAuthorize("@ss.hasPermission('topic:interlocution:query')")
    public CommonResult<PageResult<InterlocutionRespVO>> getInterlocutionPage(@Valid InterlocutionPageReqVO pageVO) {
        PageResult<InterlocutionDO> pageResult = interlocutionService.getInterlocutionPage(pageVO);
        return success(InterlocutionConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出问答题 Excel")
    @PreAuthorize("@ss.hasPermission('topic:interlocution:export')")
    @OperateLog(type = EXPORT)
    public void exportInterlocutionExcel(@Valid InterlocutionExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<InterlocutionDO> list = interlocutionService.getInterlocutionList(exportReqVO);
        // 导出 Excel
        List<InterlocutionExcelVO> datas = InterlocutionConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "问答题.xls", "数据", InterlocutionExcelVO.class, datas);
    }

}
