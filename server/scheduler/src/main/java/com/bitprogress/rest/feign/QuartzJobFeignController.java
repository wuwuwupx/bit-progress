package com.bitprogress.rest.feign;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobAddDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobStateDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobUpdateDTO;
import com.bitprogress.model.quartzjob.pojo.vo.QuartzJobVO;
import com.bitprogress.service.quartzjob.QuartzJobService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author wpx
 * Created on 2021/2/6 14:17
 * 
 */
@RestController
@RequestMapping("rest/scheduler/quartzJob")
public class QuartzJobFeignController {

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 创建定时任务
     *
     * @param addDTO 需要添加的定时任务信息
     */
    @PostMapping
    @ApiOperation(value = "创建任务")
    public ResultVO<QuartzJobVO> createQuartzJob(@RequestBody QuartzJobAddDTO addDTO) {
        return ResultVO.successData(quartzJobService.saveQuartzJob(addDTO));
    }

    @PutMapping
    @ApiOperation("修改")
    public ResultVO<QuartzJobVO> update(@RequestBody @Valid QuartzJobUpdateDTO quartzJobUpdateDTO) {
        return ResultVO.successData(quartzJobService.updateQuartzJob(quartzJobUpdateDTO));
    }

    /**
     * 移除定时任务
     *
     * @param quartzJobIds 移除的任务ID
     */
    @DeleteMapping
    public ResultVO<BooleanVO> deleteQuartzJob(@RequestParam @ApiParam("quartzJobId列表") Set<Long> quartzJobIds) {
        quartzJobService.deleteQuartzJobs(quartzJobIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @PutMapping("pause")
    @ApiOperation(value = "暂停任务")
    public ResultVO<BooleanVO> pauseQuartzJob(@RequestBody @Valid QuartzJobStateDTO stateDTO) {
        quartzJobService.pauseQuartzJob(stateDTO);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @PutMapping("reschedule")
    @ApiOperation(value = "恢复任务")
    public ResultVO<BooleanVO> rescheduleQuartzJob(@RequestBody @Valid QuartzJobStateDTO stateDTO) {
        quartzJobService.rescheduleQuartzJob(stateDTO);
        return ResultVO.successData(BooleanVO.result(true));
    }

}
