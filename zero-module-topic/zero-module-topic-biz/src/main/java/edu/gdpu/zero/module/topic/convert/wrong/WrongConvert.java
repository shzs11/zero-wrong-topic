package edu.gdpu.zero.module.topic.convert.wrong;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.wrong.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.wrong.WrongDO;

/**
 * 错题关联 Convert
 *
 * @author zero
 */
@Mapper
public interface WrongConvert {

    WrongConvert INSTANCE = Mappers.getMapper(WrongConvert.class);

    WrongDO convert(WrongCreateReqVO bean);

    WrongDO convert(WrongUpdateReqVO bean);

    WrongRespVO convert(WrongDO bean);

    List<WrongRespVO> convertList(List<WrongDO> list);

    PageResult<WrongRespVO> convertPage(PageResult<WrongDO> page);

    List<WrongExcelVO> convertList02(List<WrongDO> list);

}
