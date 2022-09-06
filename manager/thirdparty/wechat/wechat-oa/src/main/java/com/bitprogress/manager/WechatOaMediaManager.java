package com.bitprogress.manager;

import com.bitprogress.model.WechatResult;
import com.bitprogress.model.media.MediaTypeEnum;
import com.bitprogress.model.media.dto.ArticleDTO;
import com.bitprogress.model.media.dto.MediaQueryDTO;
import com.bitprogress.model.media.dto.UpdateArticleDTO;
import com.bitprogress.model.media.vo.*;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.WechatRequestUtils;
import com.bitprogress.util.WechatResultUtils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitprogress.constant.WechatOaConstants.*;
import static com.bitprogress.constant.WechatOaUrl.*;
import static com.bitprogress.okhttp.constant.OkHttpConstants.*;

/**
 * @author wuwuwupx
 *  微信公众号素材管理
 */
public class WechatOaMediaManager {

    /**
     * 新增临时素材
     * 媒体文件在后台保存时间为3天，即3天后media_id失效。
     *
     * @param accessToken
     * @param mediaType
     * @param media
     * @return MediaVO
     */
    public static MediaUploadVO uploadMedia(String accessToken, MediaTypeEnum mediaType, File media) {
        Map<String, Object> mediaBody = new HashMap<>(4);
        mediaBody.put(MEDIA, media);
        Map<String, String> params = new HashMap<>(4);
        params.put(TYPE, mediaType.getName());
        String body = JsonUtils.serializeObject(mediaBody);
        String result = WechatRequestUtils.doPostWithAccessToken(UPLOAD_MEDIA_URL, MEDIA_TYPE_FILE, accessToken, body, params);
        return WechatResultUtils.wechatResultCheck(result, MediaUploadVO.class);
    }

    /**
     * 获取临时素材
     *
     * @param mediaId
     * @return MediaVO
     */
    public MediaVO getMedia(String accessToken, String mediaId, MediaTypeEnum mediaType) throws IOException {
        Map<String, String> params = new HashMap<>(4);
        params.put(MEDIA_ID, mediaId);
        if (MediaTypeEnum.IMAGE == mediaType) {
            String result = WechatRequestUtils.doGetWithAccessToken(GET_MEDIA_URL, accessToken, params);
            return WechatResultUtils.wechatResultCheck(result, MediaVO.class);
        }
        File directory = Files.createTempDirectory("media").toFile();
        File file = WechatRequestUtils.doGetWithAccessToken(GET_MEDIA_URL, accessToken, params, directory);
        MediaVO mediaVO = new MediaVO();
        mediaVO.setMedia(file);
        return mediaVO;
    }

    /**
     * 添加永久图文消息素材
     *
     * @param accessToken
     * @param articles
     * @return MediaVO
     */
    public MediaUploadVO addNewsMediaUrl(String accessToken, List<ArticleDTO> articles) {
        Map<String, String> params = new HashMap<>(4);
        params.put(ARTICLES, JsonUtils.serializeObject(articles));
        String body = JsonUtils.serializeObject(params);
        String result = WechatRequestUtils.doPostWithAccessToken(ADD_NEWS_MEDIA_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, MediaUploadVO.class);
    }

    /**
     * 添加永久图文消息里的图片
     *
     * @param accessToken
     * @param media
     * @return MediaVO
     */
    public MediaUploadVO uploadImgMedia(String accessToken, File media) {
        Map<String, Object> mediaBody = new HashMap<>(4);
        mediaBody.put(MEDIA, media);
        String body = JsonUtils.serializeObject(mediaBody);
        String result = WechatRequestUtils.doPostWithAccessToken(UPLOAD_IMG_MEDIA_URL, MEDIA_TYPE_FILE, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, MediaUploadVO.class);
    }

    /**
     * 添加其他永久素材
     *
     * @param accessToken
     * @param mediaUploadDTO
     * @return MediaVO
     */
    public MediaUploadVO uploadOtherMedia(String accessToken, MediaUploadDTO mediaUploadDTO) {
        MediaTypeEnum mediaType = mediaUploadDTO.getMediaType();
        Map<String, String> params = new HashMap<>(4);
        params.put(TYPE, mediaType.getName());
        File media = mediaUploadDTO.getMedia();
        String body = JsonUtils.serializeObject(mediaUploadDTO);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MEDIA_TYPE_FORM_DATA)
                .addFormDataPart("media", media.getName(), RequestBody.create(MEDIA_TYPE_OCTET_STREAM, media))
                .addFormDataPart("description", body)
                .build();
        String result = WechatRequestUtils.doPostWithAccessToken(UPLOAD_OTHER_MEDIA_URL, accessToken, multipartBody, params);
        return WechatResultUtils.wechatResultCheck(result, MediaUploadVO.class);
    }

    /**
     * 删除永久素材
     *
     * @param accessToken
     * @param mediaId     要获取的素材的media_id
     * @return WechatResult
     */
    public WechatResult deleteMedia(String accessToken, String mediaId) {
        HashMap<String, Object> params = new HashMap<>(4);
        params.put(MEDIA_ID, mediaId);
        String body = JsonUtils.serializeObject(params);
        String result = WechatRequestUtils.doPostWithAccessToken(DELETE_MEDIA_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 修改图文素材
     *
     * @param accessToken
     * @param updateArticleDTO 修改图文素材的DTO
     * @return WechatResult
     */
    public WechatResult updateMedia(String accessToken, UpdateArticleDTO updateArticleDTO) {
        String body = JsonUtils.serializeObject(updateArticleDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(UPDATE_MEDIA_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result);
    }

    /**
     * 修改图文素材
     *
     * @param accessToken
     * @return WechatResult
     */
    public MediaCountVO countMedia(String accessToken) {
        String result = WechatRequestUtils.doGetWithAccessToken(COUNT_MEDIA_URL, accessToken);
        return WechatResultUtils.wechatResultCheck(result, MediaCountVO.class);
    }

    /**
     * 获取素材列表
     *
     * @param accessToken
     * @param mediaQueryDTO
     * @return MediaListVO
     */
    public MediaListVO getPermanentPicMaterials(String accessToken, MediaQueryDTO mediaQueryDTO) {
        String body = JsonUtils.serializeObject(mediaQueryDTO);
        String result = WechatRequestUtils.doPostWithAccessToken(LIST_MEDIA_URL, accessToken, body);
        return WechatResultUtils.wechatResultCheck(result, MediaListVO.class);
    }

}
