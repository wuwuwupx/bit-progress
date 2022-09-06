package com.bitprogress.controller.captcha;

import com.bitprogress.service.user.PicCaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wpx
 **/
@RestController
@Api(tags = "系统 -- 验证码模块")
@RequestMapping("/api/base/login/picCaptcha")
public class PicCaptchaController {

    @Autowired
    private PicCaptchaService picCaptchaService;

    @GetMapping
    @ApiOperation("验证码")
    public void writeCaptcha(@RequestParam @ApiParam("uuid") String uuid, HttpServletResponse res) {
        picCaptchaService.writeCaptcha(uuid, res);
    }

}