package com.bitprogress.controller.user;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsQueryDTO;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsVO;
import com.bitprogress.service.user.WechatOaUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.model.ResultVO;

import java.util.Set;
import org.springframework.web.bind.annotation.RestController;

/**
* @author wuwuwupx
*/
@Api(tags = {"微信公众号用户信息"})
@RestController
@RequestMapping("/api/base/user/wechatOaUser")
public class WechatOaUserController {

    @Autowired
    private WechatOaUserService wechatOaUserService;

    @GetMapping
    @ApiOperation("查询详情")
    public ResultVO<WechatOaUserCmsVO> findById(@RequestParam @ApiParam("wechatOaUserId") Long wechatOaUserId) {
        return ResultVO.successData(wechatOaUserService.findById(wechatOaUserId));
    }

    @DeleteMapping
    @ApiOperation("删除")
    public ResultVO<BooleanVO> delete(@RequestParam @ApiParam("wechatOaUserId列表") Set<Long> wechatOaUserIds) {
        wechatOaUserService.deleteWechatOaUsers(wechatOaUserIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public ResultVO<IPage<WechatOaUserCmsVO>> page(@ModelAttribute WechatOaUserCmsQueryDTO wechatOaUserQueryDTO, Page page) {
        return ResultVO.successData(wechatOaUserService.findWechatOaUserPage(wechatOaUserQueryDTO,page));
    }

}

