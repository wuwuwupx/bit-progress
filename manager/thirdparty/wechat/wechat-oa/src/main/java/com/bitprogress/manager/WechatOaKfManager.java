package com.bitprogress.manager;

import com.bitprogress.model.kf.KfAccount;
import com.bitprogress.model.kf.KfDTO;
import com.bitprogress.model.kf.KfList;
import com.bitprogress.model.WechatResult;
import com.bitprogress.model.kf.message.*;
import com.bitprogress.util.Assert;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.WechatResultUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.bitprogress.constant.WechatOaConstants.*;
import static com.bitprogress.constant.WechatOaUrl.*;
import static com.bitprogress.exception.WechatExceptionMessage.OPEN_ID_EMPTY_EXCEPTION;
import static com.bitprogress.excetion.WechatOaExceptionMessage.*;
import static com.bitprogress.okhttp.constant.OkHttpConstants.MEDIA_TYPE_IMAGE;
import static com.bitprogress.util.WechatRequestUtils.*;

/**
 * @author wuwuwupx
 *  微信公众号客服账号管理
 */
public class WechatOaKfManager {

    /**
     * 添加客服账号
     *
     * @param accessToken 微信接口调用凭证
     * @param kfDTO  客服信息
     * @return WechatResult
     */
    public static WechatResult addKf(String accessToken, KfDTO kfDTO) {
        String result = doPostWithAccessToken(ADD_KF_URL, accessToken, JsonUtils.serializeObject(kfDTO));
        return WechatResultUtils.wechatResultCheck(result, WechatResult.class);
    }

    /**
     * 设置客服信息
     *
     * @param accessToken 微信接口调用凭证
     * @param kfDTO  客服信息
     * @return WechatResult
     */
    public WechatResult updateKf(String accessToken, KfDTO kfDTO) {
        String result = doPostWithAccessToken(UPDATE_KF_URL, accessToken, JsonUtils.serializeObject(kfDTO));
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 删除客服账号
     *
     * @param accessToken 微信接口调用凭证
     * @param kfAccount   完整客服账号
     * @return WechatResult
     * @throws IOException
     */
    public WechatResult deleteCustomService(String accessToken, String kfAccount) {
        Map<String, String> params = new HashMap<>(8);
        params.put(KF_ACCOUNT, kfAccount);
        String result = doGetWithAccessToken(DELETE_KF_URL, accessToken, params);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 上传客服头像
     *
     * @param accessToken 微信接口调用凭证
     * @param kfAccount   完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，请前往微信公众平台设置
     * @param media       form-data 中媒体文件标识，有filename、filelength、content-type 等信息，文件大小为5M 以内
     * @return WechatResult
     */
    public WechatResult uploadKfHeadImgUrl(String accessToken, String kfAccount, File media) {
        Map<String, String> params = new HashMap<>(8);
        params.put(KF_ACCOUNT, kfAccount);
        Map<String, Object> body = new HashMap<>(8);
        body.put(MEDIA, media);
        String result = doPostWithAccessToken(UPLOAD_HEAD_IMG_URL, MEDIA_TYPE_IMAGE, accessToken, JsonUtils.serializeObject(body), params);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 获取客服基本信息
     *
     * @param accessToken 微信接口调用凭证
     * @return CustomAccountVO
     */
    public static KfList getKfList(String accessToken) {
        String result = doGetWithAccessToken(GET_KF_LIST_URL, accessToken);
        return WechatResultUtils.wechatResultCheck(result, KfList.class);
    }

    /**
     * 邀请绑定客服帐号
     *
     * @param accessToken 微信接口调用凭证
     * @param kfAccount   完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，请前往微信公众平台设置
     * @param inviteWx    接收绑定邀请的客服微信号
     * @return WechatResult
     */
    public WechatResult inviteWorkerKf(String accessToken, String kfAccount, String inviteWx) {
        Map<String, Object> body = new HashMap<>(8);
        body.put(KF_ACCOUNT, kfAccount);
        body.put(INVITE_WX, inviteWx);
        String result = doPostWithAccessToken(INVITE_WORKER_KF_URL, accessToken, JsonUtils.serializeObject(body));
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 客服发送消息
     *
     * @param kfMessage 消息内容
     * @return WechatResult
     */
    public static <T extends KfBaseMessage> WechatResult sendKfMessage(String accessToken, KfMessage<T> kfMessage) {
        String body = handlerKfMessage(kfMessage);
        String result = doPostWithAccessToken(SEND_KF_MESSAGE_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 对客服消息进行校验
     *
     * @param kfMessage
     */
    private static String handlerKfMessage(KfMessage kfMessage) {
        String openId = kfMessage.getToUser();
        Assert.isNotEmpty(openId, OPEN_ID_EMPTY_EXCEPTION);
        MsgTypeEnum msgType = kfMessage.getMsgType();
        Assert.notNull(msgType, KF_MESSAGE_TYPE_EMPTY_EXCEPTION);
        KfBaseMessage message = kfMessage.getMessage();
        Assert.notNull(message, KF_MESSAGE_EMPTY_EXCEPTION);
        Assert.isTrue(msgType.getMessageClass().isInstance(message), KF_MESSAGE_TYPE_MISMATCH_WITH_CONTENT_EXCEPTION);
        String typeName = msgType.getName();
        Map<String, Object> body = new HashMap<>(8);
        body.put(TO_USER, openId);
        body.put(MSG_TYPE, typeName);
        body.put(typeName, JsonUtils.serializeObject(message));
        KfAccount kfAccount = kfMessage.getKfAccount();
        if (Objects.nonNull(kfAccount)) {
            body.put(CUSTOM_SERVICE, JsonUtils.serializeObject(kfAccount));
        }
        return JsonUtils.serializeObject(body);
    }

}
