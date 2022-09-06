package com.bitprogress.controller;

import com.bitprogress.model.BooleanVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.model.ResultVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import com.bitprogress.model.group.pojo.dto.GroupQueryDTO;
import com.bitprogress.model.group.pojo.dto.GroupUpdateDTO;
import com.bitprogress.model.group.pojo.vo.GroupVO;
import com.bitprogress.model.group.pojo.dto.GroupAddDTO;
import com.bitprogress.service.group.GroupService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wpx
 * create on 2021-11-19
 */
@Api(tags = {"scheduler -- 定时任务分组"})
@RestController
@RequestMapping("/api/scheduler/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    @ApiOperation("查询详情")
    public ResultVO<GroupVO> findById(@RequestParam @ApiParam("groupId") Long groupId) {
        return ResultVO.successData(groupService.findById(groupId));
    }

    @PostMapping
    @ApiOperation("添加")
    public ResultVO<GroupVO> save(@RequestBody @Valid GroupAddDTO groupAddDTO) {
        return ResultVO.successData(groupService.saveGroup(groupAddDTO));
    }

    @DeleteMapping
    @ApiOperation("删除")
    public ResultVO<BooleanVO> delete(@RequestParam @ApiParam("groupId列表") Set<Long> groupIds) {
        groupService.deleteGroups(groupIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @PutMapping
    @ApiOperation("修改")
    public ResultVO<GroupVO> update(@RequestBody @Valid GroupUpdateDTO groupUpdateDTO) {
        return ResultVO.successData(groupService.updateGroup(groupUpdateDTO));
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public ResultVO<IPage<GroupVO>> page(@ModelAttribute GroupQueryDTO groupQueryDTO, Page page) {
        return ResultVO.successData(groupService.findGroupPage(groupQueryDTO, page));
    }

    @GetMapping("list")
    @ApiOperation("获取任务分组列表")
    public ResultVO<List<GroupVO>> list() {
        return ResultVO.successData(groupService.listGroup());
    }

}

