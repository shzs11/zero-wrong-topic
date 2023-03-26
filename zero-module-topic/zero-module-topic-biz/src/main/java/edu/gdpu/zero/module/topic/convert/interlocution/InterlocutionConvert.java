package edu.gdpu.zero.module.topic.convert.interlocution;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.interlocution.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.interlocution.InterlocutionDO;

/**
 * 问答题 Convert
 *
 * @author zero
 */
@Mapper
public interface InterlocutionConvert {

    InterlocutionConvert INSTANCE = Mappers.getMapper(InterlocutionConvert.class);

    InterlocutionDO convert(InterlocutionCreateReqVO bean);

    InterlocutionDO convert(InterlocutionUpdateReqVO bean);

    InterlocutionRespVO convert(InterlocutionDO bean);

    List<InterlocutionRespVO> convertList(List<InterlocutionDO> list);

    PageResult<InterlocutionRespVO> convertPage(PageResult<InterlocutionDO> page);

    List<InterlocutionExcelVO> convertList02(List<InterlocutionDO> list);

}
