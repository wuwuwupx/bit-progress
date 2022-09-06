package com.bitprogress.service;

import com.bitprogress.constant.StringConstants;
import com.bitprogress.exception.CommonException;
import com.bitprogress.constant.GitHubLoginUrl;
import com.bitprogress.exception.GitHubLoginExceptionMessage;
import com.bitprogress.model.AuthorizationParams;
import com.bitprogress.model.GitHubUser;
import com.bitprogress.okhttp.util.OkHttpClientUtils;
import com.bitprogress.util.JsonUtils;
import okhttp3.*;

import java.io.IOException;

/**
 * @author wuwuwupx
 * GitHub登录服务
 */
public class GitHubLoginService {

    /**
     * 获取AccessToken
     *
     * @param params
     */
    public String getAccessToken(AuthorizationParams params) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String body = JsonUtils.serializeObject(params);
        try {
            String result = OkHttpClientUtils.doPost(GitHubLoginUrl.GITHUB_ACCESS_TOKEN_URL, body, mediaType);
            return result.split(StringConstants.AND)[0].split(StringConstants.EQUAL_SIGN)[1];
        }catch (Exception e){
            e.printStackTrace();
            throw new CommonException(GitHubLoginExceptionMessage.GITHUB_ACCESS_TOKEN_REQUEST_ERROR);
        }
    }

    /**
     * 获取用户信息
     *
     * @param accessToken
     */
    public GitHubUser getGitHubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GitHubLoginUrl.GITHUB_USER_URL + "?access_token=" + accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JsonUtils.deserializeObject(string, GitHubUser.class);
        }catch (IOException e){
            throw CommonException.error(GitHubLoginExceptionMessage.GITHUB_USER_REQUEST_ERROR);
        }
    }

}
