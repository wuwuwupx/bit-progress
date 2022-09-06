package com.bitprogress.model.kf.message;

/**
 * @author wuwuwupx
 *  消息类型枚举
 */
public enum MsgTypeEnum {

    /**
     * 文本消息
     */
    TEXT("text", Text.class),

    /**
     * 图片消息
     */
    IMAGE("image", Media.class),

    /**
     * 语音消息
     */
    VOICE("voice", Media.class),

    /**
     * 视频消息
     */
    VIDEO("video", Video.class),

    /**
     * 音乐消息
     */
    MUSIC("music", Music.class),

    /**
     * 图文消息（点击跳转到外链）
     */
    NEWS("news", News.class),

    /**
     * 图文消息（点击跳转到图文消息页面）
     */
    MP_NEWS("mpnews", Media.class),

    /**
     * 菜单消息
     */
    MSG_MENU("msgmenu", MsgMenu.class),

    /**
     * 发送卡券
     */
    WX_CARD("wxcard", WxCard.class),

    /**
     * 发送卡券
     */
    MINI_PROGRAM_PAGE("miniprogrampage", MiniProgramPage.class),

    ;

    private String name;

    private Class<? extends KfBaseMessage> messageClass;

    public String getName() {
        return name;
    }

    public Class<? extends KfBaseMessage> getMessageClass() {
        return messageClass;
    }

    MsgTypeEnum(String name, Class<? extends KfBaseMessage> messageClass) {
        this.name = name;
        this.messageClass = messageClass;
    }

}
