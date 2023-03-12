package edu.gdpu.zero.module.infra.convert.logger;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import edu.gdpu.zero.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExcelVO;
import edu.gdpu.zero.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import edu.gdpu.zero.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 错误日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiErrorLogConvert {

    ApiErrorLogConvert INSTANCE = Mappers.getMapper(ApiErrorLogConvert.class);

    ApiErrorLogRespVO convert(ApiErrorLogDO bean);

    PageResult<ApiErrorLogRespVO> convertPage(PageResult<ApiErrorLogDO> page);

    List<ApiErrorLogExcelVO> convertList02(List<ApiErrorLogDO> list);

    ApiErrorLogDO convert(ApiErrorLogCreateReqDTO bean);

}
