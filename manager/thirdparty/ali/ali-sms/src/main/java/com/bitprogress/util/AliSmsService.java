package com.bitprogress.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.bitprogress.property.AliSmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * AliSms 短信验证码工具类
 *
 * @author wuwuwupx
 */
@Service
public class AliSmsService {

    @Autowired
    private AliSmsProperties aliSmsProperties;

    /* 产品域名, 以下常量基本不变 */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String VERSION = "2017-05-25";
    private static final String SMS_ACTION = "SendSms";
    private static final String REGION_ID = "cn-hangzhou";

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    public String sendSms(String phone) throws ClientException {
        return sendSms(phone, aliSmsProperties.getSignName(), aliSmsProperties.getTemplateCode(),
                aliSmsProperties.getAccessKeyId(), aliSmsProperties.getAccessKeySecret());
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param signName
     * @param templateCode
     * @param accessKeyId
     * @param accessKeySecret
     */
    public String sendSms(String phone, String signName, String templateCode, String accessKeyId,
                          String accessKeySecret) throws ClientException {
        String code = NumberUtils.randomNumbers(6);
        Map<String, String> paramMap = new HashMap<>(4);
        paramMap.put("code", code);
        String templateParam = JsonUtils.serializeObject(paramMap);

        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(DOMAIN);
        request.setVersion(VERSION);
        request.setAction(SMS_ACTION);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateParam", templateParam);
        // 发送短信验证码
        client.getCommonResponse(request);
        return code;
    }

}
