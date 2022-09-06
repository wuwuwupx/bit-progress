package com.bitprogress.constant;

/**
 * @author wuwuwupx
 *  公众号模块接口url
 */
public class WechatOaUrl {

    /**
     * 添加客服url
     * POST
     */
    public static final String ADD_KF_URL = "https://api.weixin.qq.com/customservice/kfaccount/add";
    /**
     * 设置客服信息url
     * POST
     */
    public static final String UPDATE_KF_URL = "https://api.weixin.qq.com/customservice/kfaccount/update";
    /**
     * 删除客服信息url
     * GET
     */
    public static final String DELETE_KF_URL = "https://api.weixin.qq.com/customservice/kfaccount/del";
    /**
     * 上传客服头像url
     * POST
     */
    public static final String UPLOAD_HEAD_IMG_URL = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";
    /**
     * 获取客服列表url
     * GET
     */
    public static final String GET_KF_LIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
    /**
     * 邀请绑定客服账号url
     * POST
     */
    public static final String INVITE_WORKER_KF_URL = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker";
    /**
     * 发送客服消息url
     * POST
     */
    public static final String SEND_KF_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";

    /**
     * 创建客服会话url
     * POST
     */
    public static final String CREATE_KF_SESSION_URL = "https://api.weixin.qq.com/customservice/kfsession/create";
    /**
     * 关闭客服会话url
     * POST
     */
    public static final String CLOSE_KF_SESSION_URL = "https://api.weixin.qq.com/customservice/kfsession/close";
    /**
     * 获取客服会话url
     * GET
     */
    public static final String GET_KF_SESSION_URL = "https://api.weixin.qq.com/customservice/kfsession/getsession";
    /**
     * 获取客服会话列表url
     * GET
     */
    public static final String GET_KF_SESSION_LIST_URL = "https://api.weixin.qq.com/customservice/kfsession/getsessionlist";
    /**
     * 获取未接入会话列表url
     * GET
     */
    public static final String GET_WAIT_CASE_KF_SESSION_LIST_URL = "https://api.weixin.qq.com/customservice/kfsession/getwaitcase";
    /**
     * 获取聊天记录url
     * POST
     */
    public static final String GET_MSG_LIST_URL = "https://api.weixin.qq.com/customservice/msgrecord/getmsglist";
    /**
     * 客服输入状态url
     * POST
     */
    public static final String KF_SESSION_TYPING_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/typing";

    /**
     * 添加临时素材url
     * POST
     */
    public static final String UPLOAD_MEDIA_URL = "https https://api.weixin.qq.com/cgi-bin/media/upload";
    /**
     * 获取临时素材url
     * GET
     */
    public static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/get";
    /**
     * 添加永久素材url
     * POST
     */
    public static final String ADD_NEWS_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news";
    /**
     * 添加图文消息的图片素材url
     * POST
     */
    public static final String UPLOAD_IMG_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
    /**
     * 添加图文消息的图片素材url
     * POST
     */
    public static final String UPLOAD_OTHER_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material";
    /**
     * 删除永久素材url
     * POST
     */
    public static final String DELETE_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material";
    /**
     * 修改永久素材url
     * POST
     */
    public static final String UPDATE_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/update_news";
    /**
     * 获取永久素材总数url
     * GET
     */
    public static final String COUNT_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
    /**
     * 获取永久素材列表url
     * GET
     */
    public static final String LIST_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";

    /**
     * 创建菜单url
     * POST
     */
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create";
    /**
     * 获取菜单url
     * GET
     */
    public static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get";
    /**
     * 删除菜单url
     * GET
     */
    public static final String DELETE_MENU_URL = " https://api.weixin.qq.com/cgi-bin/menu/delete";
    /**
     * 创建个性化菜单url
     * POST
     */
    public static final String ADD_CONDITIONAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
    /**
     * 删除个性化菜单url
     * POST
     */
    public static final String DELETE_CONDITIONAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
    /**
     * 测试个性化菜单匹配结果url
     * POST
     */
    public static final String TRY_MATCH_CONDITIONAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
    /**
     * 获取自定义菜单配置url
     * GET
     */
    public static final String GET_SELF_MENU_INFO_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";

}
