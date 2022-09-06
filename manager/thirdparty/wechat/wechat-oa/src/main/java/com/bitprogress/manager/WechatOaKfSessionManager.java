package com.bitprogress.manager;

import com.bitprogress.util.JsonUtils;
import com.bitprogress.constant.WechatOaConstants;
import com.bitprogress.exception.WechatExceptionMessage;
import com.bitprogress.excetion.WechatOaException;
import com.bitprogress.excetion.WechatOaExceptionMessage;
import com.bitprogress.model.WechatResult;
import com.bitprogress.model.kfsession.*;
import com.bitprogress.model.kfsession.dto.KfSessionDTO;
import com.bitprogress.model.kfsession.dto.MessageRecordQueryDTO;
import com.bitprogress.model.kfsession.vo.KfSessionVO;
import com.bitprogress.model.kfsession.vo.KfWaitCaseSessionVO;
import com.bitprogress.model.kfsession.vo.MessageRecordVO;
import com.bitprogress.util.Assert;
import com.bitprogress.util.WechatRequestUtils;
import com.bitprogress.util.WechatResultUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.bitprogress.constant.WechatConstant.OPEN_ID;
import static com.bitprogress.constant.WechatOaConstants.COMMAND;
import static com.bitprogress.constant.WechatOaUrl.*;
import static com.bitprogress.excetion.WechatOaExceptionMessage.*;

/**
 * @author wuwuwupx
 *  公众号客服会话管理
 */
public class WechatOaKfSessionManager {

    /**
     * 创建会话
     * 此接口在客服和用户之间创建一个会话，如果该客服和用户会话已存在，则直接返回0。指定的客服帐号必须已经绑定微信号且在线。
     *
     * @param accessToken
     * @param kfSessionDTO
     * @return WechatResult
     */
    public static WechatResult createKfSession(String accessToken, KfSessionDTO kfSessionDTO) {
        String body = handlerKfSession(kfSessionDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(CREATE_KF_SESSION_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 关闭会话
     *
     * @param accessToken
     * @param kfSessionDTO
     * @return WechatResult
     */
    public static WechatResult closeKfSession(String accessToken, KfSessionDTO kfSessionDTO) {
        String body = handlerKfSession(kfSessionDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(CLOSE_KF_SESSION_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 获取客户会话状态
     * 此接口获取一个客户的会话，如果不存在，则kf_account为空。
     *
     * @param accessToken
     * @param openId      粉丝的openid
     * @return WechatResult
     */
    public KfSession getSession(String accessToken, String openId) {
        Assert.isNotEmpty(openId, KF_SESSION_OPEN_ID_EMPTY_EXCEPTION);
        Map<String, String> params = new HashMap<>(8);
        params.put(OPEN_ID, openId);
        String result = WechatRequestUtils.doGetWithAccessToken(GET_KF_SESSION_URL, accessToken, params);
        return WechatResultUtils.wechatResultCheck(result, KfSession.class);
    }

    /**
     * 获取客服的会话列表
     *
     * @param accessToken
     * @param kfAccount   完整客服账号  格式为：帐号前缀@公众号微信号
     * @return CustomSessionVO
     */
    public static KfSessionVO getSessionList(String accessToken, String kfAccount) {
        Assert.isNotEmpty(kfAccount, KF_SESSION_KF_ACCOUNT_EMPTY_EXCEPTION);
        Map<String, String> params = new HashMap<>(8);
        params.put(WechatOaConstants.KF_ACCOUNT, kfAccount);
        String result = WechatRequestUtils.doGetWithAccessToken(GET_KF_SESSION_LIST_URL, accessToken, params);
        return WechatResultUtils.wechatResultCheck(result, KfSessionVO.class);
    }

    /**
     * 获取未接入会话列表
     *
     * @param accessToken
     * @return KfWaitCaseSessionList
     */
    public KfWaitCaseSessionVO getWaitCaseList(String accessToken) {
        String result = WechatRequestUtils.doGetWithAccessToken(GET_WAIT_CASE_KF_SESSION_LIST_URL, accessToken);
        return WechatResultUtils.wechatResultCheck(result, KfWaitCaseSessionVO.class);
    }

    /**
     * 获取客服聊天记录
     *
     * @param queryDTO
     * @return CustomMsgRecord
     **/
    public static MessageRecordVO getMsgRecord(String accessToken, MessageRecordQueryDTO queryDTO) {
        String body = handlerMessageRecordQuery(queryDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(GET_MSG_LIST_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, MessageRecordVO.class);
    }

    /**
     * 客服输入状态
     *
     * @param openId     普通用户（openid）
     * @param typingEnum "Typing"：对用户下发“正在输入"状态   "CancelTyping"：取消对用户的”正在输入"状态
     * @return WechatResult
     */
    public WechatResult setKfSessionTyping(String accessToken, String openId, TypingEnum typingEnum) {
        Assert.isNotEmpty(openId, WechatExceptionMessage.OPEN_ID_EMPTY_EXCEPTION);
        Map<String, String> body = new HashMap<>(8);
        body.put(OPEN_ID, openId);
        body.put(COMMAND, typingEnum.getName());
        String result = WechatRequestUtils.doPostWithAccessToken(KF_SESSION_TYPING_URL, accessToken, JsonUtils.serializeObject(body));
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 检查客服会话信息
     *
     * @param kfSessionDTO
     * @return body
     */
    private static String handlerKfSession(KfSessionDTO kfSessionDTO) {
        if (Objects.isNull(kfSessionDTO) || Objects.isNull(kfSessionDTO.getKfAccount())) {
            throw new WechatOaException(KF_SESSION_KF_ACCOUNT_EMPTY_EXCEPTION);
        }
        Assert.isNotEmpty(kfSessionDTO.getOpenId(), WechatOaExceptionMessage.KF_SESSION_OPEN_ID_EMPTY_EXCEPTION);
        return JsonUtils.serializeObject(kfSessionDTO);
    }

    /**
     * 处理聊天记录查询参数
     *
     * @param queryDTO
     * @return body
     */
    public static String handlerMessageRecordQuery(MessageRecordQueryDTO queryDTO) {
        Long starTime = queryDTO.getStarTime();
        Long endTime = queryDTO.getEndTime();
        boolean nonNull = Objects.nonNull(starTime) && Objects.nonNull(endTime);
        Assert.isTrue(nonNull, WechatOaExceptionMessage.MESSAGE_RECORD_TIME_EMPTY_EXCEPTION);
        boolean checkTime = (endTime - starTime) > 0 && (endTime - starTime) < 86400;
        Assert.isTrue(checkTime, MESSAGE_RECORD_TIME_EXCEPTION);
        return JsonUtils.serializeObject(queryDTO);
    }

}
