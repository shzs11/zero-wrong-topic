package edu.gdpu.zero.module.system.service.logger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.framework.common.util.string.StrUtils;
import edu.gdpu.zero.module.system.api.logger.dto.OperateLogCreateReqDTO;
import edu.gdpu.zero.module.system.controller.admin.logger.vo.operatelog.OperateLogExportReqVO;
import edu.gdpu.zero.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import edu.gdpu.zero.module.system.convert.logger.OperateLogConvert;
import edu.gdpu.zero.module.system.dal.dataobject.logger.OperateLogDO;
import edu.gdpu.zero.module.system.dal.dataobject.user.AdminUserDO;
import edu.gdpu.zero.module.system.dal.mysql.logger.OperateLogMapper;
import edu.gdpu.zero.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static edu.gdpu.zero.framework.common.util.collection.CollectionUtils.convertSet;
import static edu.gdpu.zero.module.system.dal.dataobject.logger.OperateLogDO.JAVA_METHOD_ARGS_MAX_LENGTH;
import static edu.gdpu.zero.module.system.dal.dataobject.logger.OperateLogDO.RESULT_MAX_LENGTH;

@Service
@Validated
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Resource
    private AdminUserService userService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        OperateLogDO logDO = OperateLogConvert.INSTANCE.convert(createReqDTO);
        logDO.setJavaMethodArgs(StrUtils.maxLength(logDO.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
        logDO.setResultData(StrUtils.maxLength(logDO.getResultData(), RESULT_MAX_LENGTH));
        operateLogMapper.insert(logDO);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO reqVO) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StrUtil.isNotEmpty(reqVO.getUserNickname())) {
            userIds = convertSet(userService.getUserListByNickname(reqVO.getUserNickname()), AdminUserDO::getId);
            if (CollUtil.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        // 查询分页
        return operateLogMapper.selectPage(reqVO, userIds);
    }

    @Override
    public List<OperateLogDO> getOperateLogList(OperateLogExportReqVO reqVO) {
        // 处理基于用户昵称的查询
        Collection<Long> userIds = null;
        if (StrUtil.isNotEmpty(reqVO.getUserNickname())) {
            userIds = convertSet(userService.getUserListByNickname(reqVO.getUserNickname()), AdminUserDO::getId);
            if (CollUtil.isEmpty(userIds)) {
                return Collections.emptyList();
            }
        }
        // 查询列表
        return operateLogMapper.selectList(reqVO, userIds);
    }

}