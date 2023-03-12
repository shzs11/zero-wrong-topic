package edu.gdpu.zero.module.system.convert.notice;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import edu.gdpu.zero.module.system.controller.admin.notice.vo.NoticeRespVO;
import edu.gdpu.zero.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import edu.gdpu.zero.module.system.dal.dataobject.notice.NoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
