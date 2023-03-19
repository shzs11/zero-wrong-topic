package edu.gdpu.zero.module.topic.controller.admin.knowledge;

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

import edu.gdpu.zero.module.topic.controller.admin.knowledge.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import edu.gdpu.zero.module.topic.convert.knowledge.KnowledgeConvert;
import edu.gdpu.zero.module.topic.service.knowledge.KnowledgeService;

@Tag(name = "管理后台 - 知识点")
@RestController
@RequestMapping("/topic/knowledge")
@Validated
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;

    @PostMapping("/create")
    @Operation(summary = "创建知识点")
    @PreAuthorize("@ss.hasPermission('topic:knowledge:create')")
    public CommonResult<Long> createKnowledge(@Valid @RequestBody KnowledgeCreateReqVO createReqVO) {
        return success(knowledgeService.createKnowledge(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新知识点")
    @PreAuthorize("@ss.hasPermission('topic:knowledge:update')")
    public CommonResult<Boolean> updateKnowledge(@Valid @RequestBody KnowledgeUpdateReqVO updateReqVO) {
        knowledgeService.updateKnowledge(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除知识点")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('topic:knowledge:delete')")
    public CommonResult<Boolean> deleteKnowledge(@RequestParam("id") Long id) {
        knowledgeService.deleteKnowledge(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得知识点")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('topic:knowledge:query')")
    public CommonResult<KnowledgeRespVO> getKnowledge(@RequestParam("id") Long id) {
        KnowledgeDO knowledge = knowledgeService.getKnowledge(id);
        return success(KnowledgeConvert.INSTANCE.convert(knowledge));
    }

    @GetMapping("/list")
    @Operation(summary = "获得知识点列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('topic:knowledge:query')")
    public CommonResult<List<KnowledgeRespVO>> getKnowledgeList(@RequestParam("ids") Collection<Long> ids) {
        List<KnowledgeDO> list = knowledgeService.getKnowledgeList(ids);
        return success(KnowledgeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得知识点分页")
    @PreAuthorize("@ss.hasPermission('topic:knowledge:query')")
    public CommonResult<PageResult<KnowledgeRespVO>> getKnowledgePage(@Valid KnowledgePageReqVO pageVO) {
        PageResult<KnowledgeDO> pageResult = knowledgeService.getKnowledgePage(pageVO);
        return success(KnowledgeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出知识点 Excel")
    @PreAuthorize("@ss.hasPermission('topic:knowledge:export')")
    @OperateLog(type = EXPORT)
    public void exportKnowledgeExcel(@Valid KnowledgeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<KnowledgeDO> list = knowledgeService.getKnowledgeList(exportReqVO);
        // 导出 Excel
        List<KnowledgeExcelVO> datas = KnowledgeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "知识点.xls", "数据", KnowledgeExcelVO.class, datas);
    }

}
