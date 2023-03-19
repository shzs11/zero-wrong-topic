package edu.gdpu.zero.module.topic.controller.admin.selection.selection;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.selection.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.selection.SelectionDO;

/**
 * 选择题 Convert
 *
 * @author zero
 */
@Mapper
public interface SelectionConvert {

    SelectionConvert INSTANCE = Mappers.getMapper(SelectionConvert.class);

    SelectionDO convert(SelectionCreateReqVO bean);

    SelectionDO convert(SelectionUpdateReqVO bean);

    SelectionRespVO convert(SelectionDO bean);

    List<SelectionRespVO> convertList(List<SelectionDO> list);

    PageResult<SelectionRespVO> convertPage(PageResult<SelectionDO> page);

    List<SelectionExcelVO> convertList02(List<SelectionDO> list);

}
