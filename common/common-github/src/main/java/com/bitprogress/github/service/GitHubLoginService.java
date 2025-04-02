package com.bitprogress.github.service;

import com.bitprogress.basemodel.constant.StringConstants;
import com.bitprogress.exception.CommonException;
import com.bitprogress.github.constant.GitHubLoginUrl;
import com.bitprogress.github.exception.GitHubLoginExceptionMessage;
import com.bitprogress.github.model.AuthorizationParams;
import com.bitprogress.github.model.GitHubUser;
import com.bitprogress.okhttp.util.OkHttpClientUtils;
import com.bitprogress.util.JsonUtils;
import okhttp3.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * GitHub登录服务
 */
public class GitHubLoginService {

    private static final Logger log = LoggerFactory.getLogger(GitHubLoginService.class);

    /**
     * 获取AccessToken
     *
     * @param params 请求参数
     */
    public String getAccessToken(AuthorizationParams params) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String body = JsonUtils.serializeObject(params);
        try {
            String result = OkHttpClientUtils.doPost(GitHubLoginUrl.GITHUB_ACCESS_TOKEN_URL, body, mediaType);
            return result.split(StringConstants.AND)[0].split(StringConstants.EQUAL_SIGN)[1];
        }catch (Exception e){
            log.error("GitHub登录AccessToken获取异常", e);
            throw new CommonException(GitHubLoginExceptionMessage.GITHUB_ACCESS_TOKEN_REQUEST_ERROR);
        }
    }

    /**
     * 获取用户信息
     *
     * @param accessToken 访问令牌
     */
    public GitHubUser getGitHubUser(String accessToken) {
        try{
            String result = OkHttpClientUtils.doGet(GitHubLoginUrl.GITHUB_USER_URL + "?access_token=" + accessToken);
            return JsonUtils.deserializeObject(result, GitHubUser.class);
        }catch (IOException e){
            throw CommonException.error(GitHubLoginExceptionMessage.GITHUB_USER_REQUEST_ERROR);
        }
    }

}
