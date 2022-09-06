package com.bitprogress.rest.dubbo;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobAddDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobStateDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobUpdateDTO;
import com.bitprogress.model.quartzjob.pojo.vo.QuartzJobVO;
import com.bitprogress.quartzjob.QuartzJobDubboService;
import com.bitprogress.service.quartzjob.QuartzJobService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author wuwuwupx
 *  QuartzJob dubbo
 */
@Service(version = "1.0.0")
public class QuartzJobDubboServiceImpl implements QuartzJobDubboService {

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 创建定时任务
     *
     * @param addDTO 需要添加的定时任务信息
     */
    @Override
    public ResultVO<QuartzJobVO> createQuartzJob(QuartzJobAddDTO addDTO) {
        return ResultVO.successData(quartzJobService.saveQuartzJob(addDTO));
    }

    /**
     * 更新定时任务信息
     *
     * @param quartzJobUpdateDTO
     * @return 更新后的定时任务信息
     */
    @Override
    public ResultVO<QuartzJobVO> update(QuartzJobUpdateDTO quartzJobUpdateDTO) {
        return ResultVO.successData(quartzJobService.updateQuartzJob(quartzJobUpdateDTO));
    }

    /**
     * 移除定时任务
     *
     * @param quartzJobIds 移除的任务ID
     */
    @Override
    public ResultVO<BooleanVO> deleteQuartzJob(Set<Long> quartzJobIds) {
        quartzJobService.deleteQuartzJobs(quartzJobIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    /**
     * 暂停定时任务
     *
     * @param stateDTO 暂停的任务ID
     * @return 是否暂停成功
     */
    @Override
    public ResultVO<BooleanVO> pauseQuartzJob(QuartzJobStateDTO stateDTO) {
        quartzJobService.pauseQuartzJob(stateDTO);
        return ResultVO.successData(BooleanVO.result(true));
    }

    /**
     * 恢复任务
     *
     * @param stateDTO 恢复的任务ID
     * @return 是否恢复成功
     */
    @Override
    public ResultVO<BooleanVO> rescheduleQuartzJob(QuartzJobStateDTO stateDTO) {
        quartzJobService.rescheduleQuartzJob(stateDTO);
        return ResultVO.successData(BooleanVO.result(true));
    }

}
