package edu.gdpu.zero.module.system.convert.tenant;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.system.controller.admin.tenant.vo.tenant.TenantCreateReqVO;
import edu.gdpu.zero.module.system.controller.admin.tenant.vo.tenant.TenantExcelVO;
import edu.gdpu.zero.module.system.controller.admin.tenant.vo.tenant.TenantRespVO;
import edu.gdpu.zero.module.system.controller.admin.tenant.vo.tenant.TenantUpdateReqVO;
import edu.gdpu.zero.module.system.controller.admin.user.vo.user.UserCreateReqVO;
import edu.gdpu.zero.module.system.dal.dataobject.tenant.TenantDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    TenantDO convert(TenantCreateReqVO bean);

    TenantDO convert(TenantUpdateReqVO bean);

    TenantRespVO convert(TenantDO bean);

    List<TenantRespVO> convertList(List<TenantDO> list);

    PageResult<TenantRespVO> convertPage(PageResult<TenantDO> page);

    List<TenantExcelVO> convertList02(List<TenantDO> list);

    default UserCreateReqVO convert02(TenantCreateReqVO bean) {
        UserCreateReqVO reqVO = new UserCreateReqVO();
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName()).setMobile(bean.getContactMobile());
        return reqVO;
    }

}
