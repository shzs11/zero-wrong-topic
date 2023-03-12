package edu.gdpu.zero.module.infra.convert.file;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.infra.controller.admin.file.vo.file.FileRespVO;
import edu.gdpu.zero.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
