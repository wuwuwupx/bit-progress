package com.bitprogress.model.kf.message;

import com.bitprogress.model.kf.KfAccount;

/**
 * @author wuwuwupx
 *  客服消息
 */
public class KfMessage<T extends KfBaseMessage> {

    /**
     * 用户openid
     */
    private String toUser;

    /**
     * 消息类型
     */
    private MsgTypeEnum msgType;

    /**
     * 消息内容
     */
    private T message;

    /**
     * 如果需要以某个客服帐号来发消息（在微信6.0.2及以上版本中显示自定义头像），则需要此参数
     */
    private KfAccount kfAccount;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public MsgTypeEnum getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgTypeEnum msgType) {
        this.msgType = msgType;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public KfAccount getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(KfAccount kfAccount) {
        this.kfAccount = kfAccount;
    }

}
