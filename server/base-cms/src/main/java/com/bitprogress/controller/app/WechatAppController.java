package com.bitprogress.controller.app;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.wechatapp.pojo.cms.WechatAppCmsAddDTO;
import com.bitprogress.model.wechatapp.pojo.cms.WechatAppCmsQueryDTO;
import com.bitprogress.model.wechatapp.pojo.cms.WechatAppCmsUpdateDTO;
import com.bitprogress.model.wechatapp.pojo.cms.WechatAppCmsVO;
import com.bitprogress.service.app.WechatAppService;
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
import org.springframework.web.bind.annotation.RestController;

/**
* @author wuwuwupx
*/
@Api(tags = {"微信应用信息"})
@RestController
@RequestMapping("/api/wechatApp")
public class WechatAppController {

    @Autowired
    private WechatAppService wechatAppService;

    @GetMapping
    @ApiOperation("查询详情")
    public ResultVO<WechatAppCmsVO> findById(@RequestParam @ApiParam("wechatAppId") Long wechatAppId) {
        return ResultVO.successData(wechatAppService.findById(wechatAppId));
    }

    @PostMapping
    @ApiOperation("添加")
    public ResultVO<WechatAppCmsVO> save(@RequestBody @Valid WechatAppCmsAddDTO wechatAppAddDTO) {
        return ResultVO.successData(wechatAppService.saveWechatApp(wechatAppAddDTO));
    }

    @DeleteMapping
    @ApiOperation("删除")
    public ResultVO<BooleanVO> delete(@RequestParam @ApiParam("wechatAppId列表") Set<Long> wechatAppIds) {
        wechatAppService.deleteWechatApps(wechatAppIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @PutMapping
    @ApiOperation("修改")
    public ResultVO<WechatAppCmsVO> update(@RequestBody @Valid WechatAppCmsUpdateDTO wechatAppUpdateDTO) {
         return ResultVO.successData(wechatAppService.updateWechatApp(wechatAppUpdateDTO));
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public ResultVO<IPage<WechatAppCmsVO>> page(@ModelAttribute WechatAppCmsQueryDTO wechatAppQueryDTO, Page page) {
        return ResultVO.successData(wechatAppService.findWechatAppPage(wechatAppQueryDTO,page));
    }

}

