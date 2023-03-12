package edu.gdpu.zero.module.infra.convert.job;

import edu.gdpu.zero.framework.common.pojo.PageResult;
import edu.gdpu.zero.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import edu.gdpu.zero.module.infra.controller.admin.job.vo.job.JobExcelVO;
import edu.gdpu.zero.module.infra.controller.admin.job.vo.job.JobRespVO;
import edu.gdpu.zero.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import edu.gdpu.zero.module.infra.dal.dataobject.job.JobDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    JobDO convert(JobCreateReqVO bean);

    JobDO convert(JobUpdateReqVO bean);

    JobRespVO convert(JobDO bean);

    List<JobRespVO> convertList(List<JobDO> list);

    PageResult<JobRespVO> convertPage(PageResult<JobDO> page);

    List<JobExcelVO> convertList02(List<JobDO> list);

}
