package com.bitprogress.service.user;

import com.bitprogress.exception.MessageCodes;
import com.bitprogress.exception.ValidationException;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.LoginCmsDTO;
import com.bitprogress.model.login.LoginVO;
import com.bitprogress.util.BcryptUtils;
import com.bitprogress.util.StringUtils;
import com.bitprogress.auth.AuthFeignService;
import com.bitprogress.model.login.LogoutDTO;
import com.bitprogress.entity.Manager;
import com.bitprogress.model.manager.pojo.cms.LoginSuccessVO;
import com.bitprogress.model.manager.pojo.cms.ManagerLoginDTO;
import com.bitprogress.manager.redis.CaptchaRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

/**
 * <p>
 *
 * @author wpx
 **/
@Slf4j
@Service
public class LoginService {

    @Value("${application.config.salt}")
    private String salt;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private CaptchaRedisService captchaRedisService;

    @Autowired
    private AuthFeignService authFeignService;

    /**
     * 管理员登录
     *
     * @param loginDTO
     * @return LoginSuccessVO
     */
    public LoginSuccessVO login(ManagerLoginDTO loginDTO) {
        //从redis中获得该验证码
        String uuid = loginDTO.getUuid();
        String rightCaptcha = captchaRedisService.getCapText(uuid);
        captchaRedisService.deleteCaptcha(uuid);
        if (StringUtils.isEmpty(rightCaptcha)) {
            //验证码已失效
            throw new ValidationException(MessageCodes.AUTH_PICCAPTCHA_LOST_MESSAGE);
        } else {
            //判断验证码是否正确
            if (!rightCaptcha.equals(loginDTO.getCaptcha())) {
                //验证码错误
                throw new ValidationException(MessageCodes.AUTH_PICCAPTCHA_WRONG_MESSAGE);
            } else {
                //判断账号密码是否正确
                String md5Password;
                String decrypt;
                try {
                    log.info("loginVO.getPassword:[{}]", loginDTO.getPassword());
                    decrypt = BcryptUtils.decrypt(loginDTO.getPassword());
                    log.info("decrypt:[{}]===salt:[{}]", decrypt, salt);
                    md5Password = DigestUtils.md5DigestAsHex((decrypt + salt).getBytes());
                    log.info("md5Password:[{}]", md5Password);
                } catch (Exception e) {
                    log.error("RSAUtil.decrypt exception", e);
                    throw new IllegalArgumentException(MessageCodes.RSAUTIL_DECRYPT_ERROR_MESSAGE);
                }
                Manager manager = managerService.getByAccountWithPassword(loginDTO.getAccount(), md5Password);
                log.info("manager:[{}]", manager);
                Assert.notNull(manager, MessageCodes.AUTH_ACCOUNT_PASSWORD_WRONG_MESSAGE);
                Assert.isTrue(!manager.getDisabled(), MessageCodes.ACCOUNT_HAS_DISABLED_MESSAGE);
                Long managerId = manager.getManagerId();
                int role = manager.getRole().ordinal();
                // 登录获取token
                String token = generateToken(String.valueOf(managerId));
                LoginSuccessVO successVO = new LoginSuccessVO();
                successVO.setToken(token);
                successVO.setRole(manager.getRole());
                successVO.setName(manager.getUsername());
                return successVO;
            }
        }
    }

    /**
     * 生成token
     *
     * @param userId
     */
    public String generateToken(String userId) {
        LoginCmsDTO loginDTO = new LoginCmsDTO();
        loginDTO.setUserId(userId);
        ResultVO<LoginVO> login = authFeignService.login(loginDTO);
        return login.getData().getToken();
    }

    /**
     * 移除token
     *
     * @param userId
     */
    public void removeToken(String userId) {
        LogoutDTO logoutDTO = new LogoutDTO(userId);
        authFeignService.logout(logoutDTO);
    }

}
