package com.bitprogress.manager;

import com.bitprogress.constant.WechatOaUrl;
import com.bitprogress.model.WechatResult;
import com.bitprogress.model.menu.MenuResult;
import com.bitprogress.model.menu.dto.MenuDTO;
import com.bitprogress.model.menu.dto.MenuDeleteDTO;
import com.bitprogress.model.menu.dto.MenuQueryDTO;
import com.bitprogress.model.menu.vo.ButtonTryMatchVO;
import com.bitprogress.model.menu.vo.MenuVO;
import com.bitprogress.model.menu.vo.selfmenuinfo.SelfMenuInfoVO;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.WechatRequestUtils;
import com.bitprogress.util.WechatResultUtils;

/**
 * @author wuwuwupx
 *  微信公众号菜单管理
 */
public class WechatOaMenuManager {

    /**
     * 创建菜单
     *
     * @param accessToken
     * @param menuDTO
     * @return WechatResult
     */
    public MenuResult createMenu(String accessToken, MenuDTO menuDTO) {
        String body = JsonUtils.serializeObject(menuDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(WechatOaUrl.CREATE_MENU_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, MenuResult.class);
    }

    /**
     * 获取菜单
     * 使用接口创建自定义菜单后，开发者还可使用接口查询自定义菜单的结构。另外请注意，在设置了个性化菜单后
     * 使用本自定义菜单查询接口可以获取默认菜单和全部个性化菜单信息
     *
     * @param accessToken
     * @return MenuVO
     */
    public MenuVO getMenu(String accessToken) {
        String result = WechatRequestUtils.doGetWithAccessToken(WechatOaUrl.GET_MENU_URL, accessToken);
        return WechatResultUtils.wechatResultCheck(result, MenuVO.class);
    }

    /**
     * 删除菜单
     *
     * @param accessToken
     * @return MenuVO
     */
    public WechatResult deleteMenu(String accessToken) {
        String result = WechatRequestUtils.doGetWithAccessToken(WechatOaUrl.DELETE_MENU_URL, accessToken);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 创建个性化菜单
     *
     * @param accessToken
     * @param menuDTO
     * @return WechatResult
     */
    public MenuResult createConditionalMenu(String accessToken, MenuDTO menuDTO) {
        String body = JsonUtils.serializeObject(menuDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(WechatOaUrl.ADD_CONDITIONAL_MENU_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, MenuResult.class);
    }

    /**
     * 删除个性化菜单
     *
     * @param accessToken
     * @param deleteDTO
     * @return WechatResult
     */
    public WechatResult deleteConditionalMenu(String accessToken, MenuDeleteDTO deleteDTO) {
        String body = JsonUtils.serializeObject(deleteDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(WechatOaUrl.DELETE_CONDITIONAL_MENU_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 测试个性化菜单匹配结果
     *
     * @param accessToken
     * @param queryDTO
     * @return WechatResult
     */
    public ButtonTryMatchVO tryMatchConditionalMenu(String accessToken, MenuQueryDTO queryDTO) {
        String body = JsonUtils.serializeObject(queryDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(WechatOaUrl.TRY_MATCH_CONDITIONAL_MENU_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, ButtonTryMatchVO.class);
    }

    /**
     * 获取自定义菜单配置
     * 本接口将会提供公众号当前使用的自定义菜单的配置，
     * 如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
     * 而如果公众号是在公众平台官网通过网站功能发布菜单，
     * 则本接口返回运营者设置的菜单配置。
     *
     * @param accessToken
     * @return WechatResult
     */
    public SelfMenuInfoVO getSelfMenuInfo(String accessToken) {
        String result = WechatRequestUtils.doGetWithAccessToken(WechatOaUrl.TRY_MATCH_CONDITIONAL_MENU_URL, accessToken);
        return WechatResultUtils.wechatResultCheck(result, SelfMenuInfoVO.class);
    }

}
