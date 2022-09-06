package com.bitprogress.controller.user;

import com.bitprogress.service.app.WechatService;
import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.WechatPhoneDTO;
import com.bitprogress.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wuwuwupx
 */
@Api(tags = "系统 -- 微信用户模块")
@RestController
@RequestMapping("api/base/user/wechat")
public class WechatUserController {

    @Autowired
    private WechatService wechatService;

    @PostMapping("phone")
    @ApiOperation("微信端获取用户手机号码")
    public ResultVO<BooleanVO> updatePhone(@RequestBody @Valid WechatPhoneDTO wechatPhoneDTO) {
        wechatService.updatePhone(wechatPhoneDTO, UserUtils.getUserId());
        return ResultVO.successData(BooleanVO.result(true));
    }

}
