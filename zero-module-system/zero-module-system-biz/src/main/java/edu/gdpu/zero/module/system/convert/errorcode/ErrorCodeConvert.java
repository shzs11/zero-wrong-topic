package edu.gdpu.zero.module.system.convert.errorcode;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import edu.gdpu.zero.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import edu.gdpu.zero.module.system.controller.admin.errorcode.vo.ErrorCodeCreateReqVO;
import edu.gdpu.zero.module.system.controller.admin.errorcode.vo.ErrorCodeExcelVO;
import edu.gdpu.zero.module.system.controller.admin.errorcode.vo.ErrorCodeRespVO;
import edu.gdpu.zero.module.system.controller.admin.errorcode.vo.ErrorCodeUpdateReqVO;
import edu.gdpu.zero.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 错误码 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ErrorCodeConvert {

    ErrorCodeConvert INSTANCE = Mappers.getMapper(ErrorCodeConvert.class);

    ErrorCodeDO convert(ErrorCodeCreateReqVO bean);

    ErrorCodeDO convert(ErrorCodeUpdateReqVO bean);

    ErrorCodeRespVO convert(ErrorCodeDO bean);

    List<ErrorCodeRespVO> convertList(List<ErrorCodeDO> list);

    PageResult<ErrorCodeRespVO> convertPage(PageResult<ErrorCodeDO> page);

    List<ErrorCodeExcelVO> convertList02(List<ErrorCodeDO> list);

    ErrorCodeDO convert(ErrorCodeAutoGenerateReqDTO bean);

    List<ErrorCodeRespDTO> convertList03(List<ErrorCodeDO> list);

}
