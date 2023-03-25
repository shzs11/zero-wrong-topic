package edu.gdpu.zero.module.topic.controller.admin.judgment;

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

import edu.gdpu.zero.module.topic.controller.admin.judgment.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;
import edu.gdpu.zero.module.topic.convert.judgment.JudgmentConvert;
import edu.gdpu.zero.module.topic.service.judgment.JudgmentService;

@Tag(name = "管理后台 - 判断题")
@RestController
@RequestMapping("/topic/judgment")
@Validated
public class JudgmentController {

    @Resource
    private JudgmentService judgmentService;

    @PostMapping("/create")
    @Operation(summary = "创建判断题")
    @PreAuthorize("@ss.hasPermission('topic:judgment:create')")
    public CommonResult<Long> createJudgment(@Valid @RequestBody JudgmentCreateReqVO createReqVO) {
        return success(judgmentService.createJudgment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新判断题")
    @PreAuthorize("@ss.hasPermission('topic:judgment:update')")
    public CommonResult<Boolean> updateJudgment(@Valid @RequestBody JudgmentUpdateReqVO updateReqVO) {
        judgmentService.updateJudgment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除判断题")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('topic:judgment:delete')")
    public CommonResult<Boolean> deleteJudgment(@RequestParam("id") Long id) {
        judgmentService.deleteJudgment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得判断题")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('topic:judgment:query')")
    public CommonResult<JudgmentRespVO> getJudgment(@RequestParam("id") Long id) {
        JudgmentDO judgment = judgmentService.getJudgment(id);
        return success(JudgmentConvert.INSTANCE.convert(judgment));
    }

    @GetMapping("/list")
    @Operation(summary = "获得判断题列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('topic:judgment:query')")
    public CommonResult<List<JudgmentRespVO>> getJudgmentList(@RequestParam("ids") Collection<Long> ids) {
        List<JudgmentDO> list = judgmentService.getJudgmentList(ids);
        return success(JudgmentConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得判断题分页")
    @PreAuthorize("@ss.hasPermission('topic:judgment:query')")
    public CommonResult<PageResult<JudgmentRespVO>> getJudgmentPage(@Valid JudgmentPageReqVO pageVO) {
        PageResult<JudgmentRespVO> judgmentPage2 = judgmentService.getJudgmentPage2(pageVO);
        return success(judgmentPage2);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出判断题 Excel")
    @PreAuthorize("@ss.hasPermission('topic:judgment:export')")
    @OperateLog(type = EXPORT)
    public void exportJudgmentExcel(@Valid JudgmentExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<JudgmentDO> list = judgmentService.getJudgmentList(exportReqVO);
        // 导出 Excel
        List<JudgmentExcelVO> datas = JudgmentConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "判断题.xls", "数据", JudgmentExcelVO.class, datas);
    }

}
