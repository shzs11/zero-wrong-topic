package edu.gdpu.zero.module.system.convert.logger;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.system.api.logger.dto.LoginLogCreateReqDTO;
import edu.gdpu.zero.module.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import edu.gdpu.zero.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import edu.gdpu.zero.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
