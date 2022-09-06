package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * 基础模块错误信息
 */
public class BaseMessageCodes {

    public static final String USER_NOT_EXIST_EXCEPTION_MESSAGE = "用户不存在";
    public static final String USER_SAVE_EXCEPTION_MESSAGE = "用户保存失败";
    public static final String USER_UPDATE_EXCEPTION_MESSAGE = "用户更新失败";
    public static final String USER_DELETE_EXCEPTION_MESSAGE = "用户删除失败";

    public static final String NOT_ROOT_EDIT_OTHER_INFO_MESSAGE = "非超管只能编辑自己的信息";
    public static final String NOT_ROOT_CHECK_OTHER_INFO_MESSAGE = "非超管只能查看自己的信息";
    public static final String ALLOW_ADD_WPX_EXCEPT_ROOT_MESSAGE = "不允许添加wpx之外的超管";
    public static final String MANAGER_NOT_EXIST_EXCEPTION_MESSAGE = "管理员记录不存在";
    public static final String MANAGER_UPDATE_EXCEPTION_MESSAGE = "更新管理员信息失败";
    public static final String MANAGER_DELETE_EXCEPTION_MESSAGE = "管理员记录删除失败";
    public static final String MANAGER_SAVE_EXCEPTION_MESSAGE = "管理员记录保存失败";
    public static final String ROOT_DELETE_EXCEPTION_MESSAGE_MESSAGE = "超管不可删除";
    public static final String NOT_ROOT_CANNOT_DELETE_MESSAGE = "非超管不可删除账号";
    public static final String ROOT_CANNOT_DISABLED_MESSAGE = "超管不可禁用";
    public static final String NOT_ROOT_CANNOT_DISABLED_MESSAGE = "非超管不可禁用账号";
    public static final String NOT_ALLOW_SUPERIOR_ROLE_MESSAGE = "不能给同级或上级设权限";

    public static final String APP_NOT_EXIST_EXCEPTION_MESSAGE = "应用信息不存在";
    public static final String APP_SAVE_EXCEPTION_MESSAGE = "应用信息保存失败";
    public static final String APP_UPDATE_EXCEPTION_MESSAGE = "应用信息更新失败";
    public static final String APP_DELETE_EXCEPTION_MESSAGE = "应用信息删除失败";

    public static final String WECHATUSER_NOT_EXIST_EXCEPTION_MESSAGE = "用户信息不存在";
    public static final String WECHATUSER_SAVE_EXCEPTION_MESSAGE = "用户信息保存失败";
    public static final String WECHATUSER_UPDATE_EXCEPTION_MESSAGE = "用户信息更新失败";
    public static final String WECHATUSER_DELETE_EXCEPTION_MESSAGE = "用户信息删除失败";
    public static final String WECHATAPPLETUSER_NOT_EXIST_EXCEPTION_MESSAGE = "微信小程序用户信息不存在";
    public static final String WECHATAPPLETUSER_SAVE_EXCEPTION_MESSAGE = "微信小程序用户信息保存失败";
    public static final String WECHATAPPLETUSER_UPDATE_EXCEPTION_MESSAGE = "微信小程序用户信息更新失败";
    public static final String WECHATAPPLETUSER_DELETE_EXCEPTION_MESSAGE = "微信小程序用户信息删除失败";
    public static final String WECHATOAUSER_NOT_EXIST_EXCEPTION_MESSAGE = "微信公众号用户信息不存在";
    public static final String WECHATOAUSER_SAVE_EXCEPTION_MESSAGE = "微信公众号用户信息保存失败";
    public static final String WECHATOAUSER_UPDATE_EXCEPTION_MESSAGE = "微信公众号用户信息更新失败";
    public static final String WECHATOAUSER_DELETE_EXCEPTION_MESSAGE = "微信公众号用户信息删除失败";
    public static final String WECHATAPP_NOT_EXIST_EXCEPTION_MESSAGE = "微信应用信息不存在";
    public static final String WECHATAPP_SAVE_EXCEPTION_MESSAGE = "微信应用信息保存失败";
    public static final String WECHATAPP_UPDATE_EXCEPTION_MESSAGE = "微信应用信息更新失败";
    public static final String WECHATAPP_DELETE_EXCEPTION_MESSAGE = "微信应用信息删除失败";
    public static final String UNION_ID_EMPTY_EXCEPTION_MESSAGE = "微信用户unionId为空";
    public static final String OPENID_ID_EMPTY_EXCEPTION_MESSAGE = "微信用户openId为空";
    public static final String NOT_WECHATUSER_EXCEPTION_MESSAGE = "非微信用户";
    public static final String REFRESH_ACCESSTOKEN_EXCEPTION_MESSAGE = "刷新微信accessToken异常";

    public static final String PHONEUSER_NOT_EXIST_EXCEPTION_MESSAGE = "手机用户信息不存在";
    public static final String PHONEUSER_SAVE_EXCEPTION_MESSAGE = "手机用户信息保存失败";
    public static final String PHONEUSER_UPDATE_EXCEPTION_MESSAGE = "手机用户信息更新失败";
    public static final String PHONEUSER_DELETE_EXCEPTION_MESSAGE = "手机用户信息删除失败";

}
