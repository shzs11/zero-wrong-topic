package edu.gdpu.zero.module.topic.convert.subject;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.subject.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.subject.SubjectDO;

/**
 * 科目 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface SubjectConvert {

    SubjectConvert INSTANCE = Mappers.getMapper(SubjectConvert.class);

    SubjectDO convert(SubjectCreateReqVO bean);

    SubjectDO convert(SubjectUpdateReqVO bean);

    SubjectRespVO convert(SubjectDO bean);

    List<SubjectRespVO> convertList(List<SubjectDO> list);

    PageResult<SubjectRespVO> convertPage(PageResult<SubjectDO> page);

    List<SubjectExcelVO> convertList02(List<SubjectDO> list);

}
