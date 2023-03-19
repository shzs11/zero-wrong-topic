package edu.gdpu.zero.module.topic.convert.knowledge;

import java.util.*;

import edu.gdpu.zero.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import edu.gdpu.zero.module.topic.controller.admin.knowledge.vo.*;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;

/**
 * 知识点 Convert
 *
 * @author zero
 */
@Mapper
public interface KnowledgeConvert {

    KnowledgeConvert INSTANCE = Mappers.getMapper(KnowledgeConvert.class);

    KnowledgeDO convert(KnowledgeCreateReqVO bean);

    KnowledgeDO convert(KnowledgeUpdateReqVO bean);

    KnowledgeRespVO convert(KnowledgeDO bean);

    List<KnowledgeRespVO> convertList(List<KnowledgeDO> list);

    PageResult<KnowledgeRespVO> convertPage(PageResult<KnowledgeDO> page);

    List<KnowledgeExcelVO> convertList02(List<KnowledgeDO> list);

}
