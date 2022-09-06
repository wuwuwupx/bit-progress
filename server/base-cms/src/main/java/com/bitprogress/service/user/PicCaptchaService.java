package com.bitprogress.service.user;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.bitprogress.util.StringUtils;
import com.bitprogress.manager.redis.CaptchaRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author wuwuwupx
 */
@Service
@Slf4j
public class PicCaptchaService {

    @Autowired
    private CaptchaRedisService captchaRedisService;

    @Autowired
    private DefaultKaptcha captchaProducer;

    /**
     * 加载验证码
     *
     * @param uuid
     * @param res
     */
    public void writeCaptcha(String uuid, HttpServletResponse res) {
        log.info(" login uuid is {}", uuid);
        String capText = captchaRedisService.getCapText(uuid);
        if (StringUtils.isEmpty(capText)) {
            capText = captchaProducer.createText();
            log.info("----------capText:" + capText + "----------------");
            captchaRedisService.setCapText(uuid, capText);
        }

        res.setDateHeader("Expires", 0);
        res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        res.setHeader("Cache-Control", "post-check=0, pre-check=0");
        res.setHeader("Pragma", "no-cache");
        res.setContentType("image/jpeg");
        try (ServletOutputStream out = res.getOutputStream()) {
            BufferedImage bufferedImage = captchaProducer.createImage(capText);
            ImageIO.write(bufferedImage, "jpg", out);
            out.flush();
        } catch (IOException e) {
            log.info("----------验证码输出错误:{}--------", e.getMessage());
        }
    }

}
