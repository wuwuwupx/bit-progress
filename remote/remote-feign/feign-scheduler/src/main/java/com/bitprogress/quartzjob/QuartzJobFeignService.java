package com.bitprogress.quartzjob;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobAddDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobStateDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobUpdateDTO;
import com.bitprogress.model.quartzjob.pojo.vo.QuartzJobVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author wuwuwupx
 * quartzJob feign 服务调用类
 */
@FeignClient(name = "scheduler", path = "rest/scheduler/quartzJob", fallbackFactory = QuartzJobFeignServiceFallback.class)
public interface QuartzJobFeignService {

    /**
     * 创建定时任务
     *
     * @param addDTO 需要添加的定时任务信息
     * @return 创建后的定时任务信息
     */
    @PostMapping
    @ApiOperation(value = "创建任务")
    ResultVO<QuartzJobVO> createQuartzJob(@RequestBody QuartzJobAddDTO addDTO);

    /**
     * 更新定时任务信息
     *
     * @param quartzJobUpdateDTO
     * @return 更新后的定时任务信息
     */
    @PutMapping
    @ApiOperation("修改")
    ResultVO<QuartzJobVO> update(@RequestBody @Valid QuartzJobUpdateDTO quartzJobUpdateDTO);

    /**
     * 移除定时任务
     *
     * @param quartzJobIds 移除的任务ID
     * @return 是否移除成功
     */
    @DeleteMapping
    ResultVO<BooleanVO> deleteQuartzJob(@RequestParam @ApiParam("quartzJobId列表") Set<Long> quartzJobIds);

    /**
     * 暂停定时任务
     *
     * @param stateDTO 暂停的任务ID
     * @return 是否暂停成功
     */
    @PutMapping("pause")
    @ApiOperation(value = "暂停任务")
    ResultVO<BooleanVO> pauseQuartzJob(@RequestBody @Valid QuartzJobStateDTO stateDTO);

    /**
     * 恢复任务
     *
     * @param stateDTO 恢复的任务ID
     * @return 是否恢复成功
     */
    @PutMapping("reschedule")
    @ApiOperation(value = "恢复任务")
    ResultVO<BooleanVO> rescheduleQuartzJob(@RequestBody @Valid QuartzJobStateDTO stateDTO);

}
