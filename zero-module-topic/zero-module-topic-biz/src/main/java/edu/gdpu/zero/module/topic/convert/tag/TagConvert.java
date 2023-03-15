package edu.gdpu.zero.module.topic.convert.tag;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.tag.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.tag.TagDO;

/**
 * 标签 Convert
 *
 * @author zero
 */
@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    TagDO convert(TagCreateReqVO bean);

    TagDO convert(TagUpdateReqVO bean);

    TagRespVO convert(TagDO bean);

    List<TagRespVO> convertList(List<TagDO> list);

    PageResult<TagRespVO> convertPage(PageResult<TagDO> page);

    List<TagExcelVO> convertList02(List<TagDO> list);

}
