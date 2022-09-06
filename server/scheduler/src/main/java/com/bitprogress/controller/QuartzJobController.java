package com.bitprogress.controller;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobStateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.model.ResultVO;

import javax.validation.Valid;
import java.util.Set;

import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobQueryDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobUpdateDTO;
import com.bitprogress.model.quartzjob.pojo.vo.QuartzJobVO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobAddDTO;
import com.bitprogress.service.quartzjob.QuartzJobService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wpx
 * create on 2021-11-19
 */
@Api(tags = {"scheduler -- 定时任务"})
@RestController
@RequestMapping("/api/scheduler/quartzJob")
public class QuartzJobController {

    @Autowired
    private QuartzJobService quartzJobService;

    @GetMapping
    @ApiOperation("查询详情")
    public ResultVO<QuartzJobVO> findById(@RequestParam @ApiParam("quartzJobId") Long quartzJobId) {
        return ResultVO.successData(quartzJobService.findById(quartzJobId));
    }

    @PostMapping
    @ApiOperation("添加")
    public ResultVO<QuartzJobVO> save(@RequestBody @Valid QuartzJobAddDTO quartzJobAddDTO) {
        return ResultVO.successData(quartzJobService.saveQuartzJob(quartzJobAddDTO));
    }

    @DeleteMapping
    @ApiOperation("删除")
    public ResultVO<BooleanVO> delete(@RequestParam @ApiParam("quartzJobId列表") Set<Long> quartzJobIds) {
        quartzJobService.deleteQuartzJobs(quartzJobIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @PutMapping
    @ApiOperation("修改")
    public ResultVO<QuartzJobVO> update(@RequestBody @Valid QuartzJobUpdateDTO quartzJobUpdateDTO) {
        return ResultVO.successData(quartzJobService.updateQuartzJob(quartzJobUpdateDTO));
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public ResultVO<IPage<QuartzJobVO>> page(@ModelAttribute QuartzJobQueryDTO quartzJobQueryDTO, Page page) {
        return ResultVO.successData(quartzJobService.findQuartzJobPage(quartzJobQueryDTO, page));
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
/*

    @PostMapping("list")
    @ApiOperation(value = "创建多个任务")
    public ResultVO<BooleanVO> createQuartzJob(@RequestBody QuartzJobListAddDTO listAddDTO) {
        quartzJobService.createJobList(listAddDTO);
        return ResultVO.successData(BooleanVO.result(true));
    }
*/

}

