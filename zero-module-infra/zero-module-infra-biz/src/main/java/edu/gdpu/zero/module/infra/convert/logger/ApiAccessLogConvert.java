package edu.gdpu.zero.module.infra.convert.logger;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import edu.gdpu.zero.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExcelVO;
import edu.gdpu.zero.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import edu.gdpu.zero.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiAccessLogConvert {

    ApiAccessLogConvert INSTANCE = Mappers.getMapper(ApiAccessLogConvert.class);

    ApiAccessLogRespVO convert(ApiAccessLogDO bean);

    List<ApiAccessLogRespVO> convertList(List<ApiAccessLogDO> list);

    PageResult<ApiAccessLogRespVO> convertPage(PageResult<ApiAccessLogDO> page);

    List<ApiAccessLogExcelVO> convertList02(List<ApiAccessLogDO> list);

    ApiAccessLogDO convert(ApiAccessLogCreateReqDTO bean);

}
