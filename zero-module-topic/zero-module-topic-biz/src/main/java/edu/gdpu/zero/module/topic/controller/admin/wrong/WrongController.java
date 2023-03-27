package edu.gdpu.zero.module.topic.controller.admin.wrong;

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

import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;
import edu.gdpu.zero.module.topic.convert.wrong.WrongConvert;
import edu.gdpu.zero.module.topic.service.wrong.WrongService;

@Tag(name = "管理后台 - 错题关联")
@RestController
@RequestMapping("/topic/wrong")
@Validated
public class WrongController {

    @Resource
    private WrongService wrongService;

    @PostMapping("/create")
    @Operation(summary = "创建错题关联")
    @PreAuthorize("@ss.hasPermission('topic:wrong:create')")
    public CommonResult<Long> createWrong(@Valid @RequestBody WrongCreateReqVO createReqVO) {
        return success(wrongService.createWrong(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新错题关联")
    @PreAuthorize("@ss.hasPermission('topic:wrong:update')")
    public CommonResult<Boolean> updateWrong(@Valid @RequestBody WrongUpdateReqVO updateReqVO) {
        wrongService.updateWrong(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除错题关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('topic:wrong:delete')")
    public CommonResult<Boolean> deleteWrong(@RequestParam("id") Long id) {
        wrongService.deleteWrong(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得错题关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('topic:wrong:query')")
    public CommonResult<WrongRespVO> getWrong(@RequestParam("id") Long id) {
        WrongDO wrong = wrongService.getWrong(id);
        return success(WrongConvert.INSTANCE.convert(wrong));
    }

    @GetMapping("/list")
    @Operation(summary = "获得错题关联列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('topic:wrong:query')")
    public CommonResult<List<WrongRespVO>> getWrongList(@RequestParam("ids") Collection<Long> ids) {
        List<WrongDO> list = wrongService.getWrongList(ids);
        return success(WrongConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得错题关联分页")
    @PreAuthorize("@ss.hasPermission('topic:wrong:query')")
    public CommonResult<PageResult<WrongRespVO>> getWrongPage(@Valid WrongPageReqVO pageVO) {
        PageResult<WrongDO> pageResult = wrongService.getWrongPage(pageVO);
        return success(WrongConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出错题关联 Excel")
    @PreAuthorize("@ss.hasPermission('topic:wrong:export')")
    @OperateLog(type = EXPORT)
    public void exportWrongExcel(@Valid WrongExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<WrongDO> list = wrongService.getWrongList(exportReqVO);
        // 导出 Excel
        List<WrongExcelVO> datas = WrongConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "错题关联.xls", "数据", WrongExcelVO.class, datas);
    }

}
