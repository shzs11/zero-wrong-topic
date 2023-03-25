package edu.gdpu.zero.module.topic.convert.judgment;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.judgment.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.judgment.JudgmentDO;

/**
 * 判断题 Convert
 *
 * @author zero
 */
@Mapper
public interface JudgmentConvert {

    JudgmentConvert INSTANCE = Mappers.getMapper(JudgmentConvert.class);

    JudgmentDO convert(JudgmentCreateReqVO bean);

    JudgmentDO convert(JudgmentUpdateReqVO bean);

    JudgmentRespVO convert(JudgmentDO bean);

    List<JudgmentRespVO> convertList(List<JudgmentDO> list);

    PageResult<JudgmentRespVO> convertPage(PageResult<JudgmentDO> page);

    List<JudgmentExcelVO> convertList02(List<JudgmentDO> list);

}
