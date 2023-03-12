package edu.gdpu.zero.module.infra.service.logger;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import edu.gdpu.zero.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExportReqVO;
import edu.gdpu.zero.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import edu.gdpu.zero.module.infra.convert.logger.ApiAccessLogConvert;
import edu.gdpu.zero.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import edu.gdpu.zero.module.infra.dal.mysql.logger.ApiAccessLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * API 访问日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLogDO apiAccessLog = ApiAccessLogConvert.INSTANCE.convert(createDTO);
        apiAccessLogMapper.insert(apiAccessLog);
    }

    @Override
    public PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ApiAccessLogDO> getApiAccessLogList(ApiAccessLogExportReqVO exportReqVO) {
        return apiAccessLogMapper.selectList(exportReqVO);
    }

}
