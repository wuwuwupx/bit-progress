package com.bitprogress.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuwuwupx
 */
@Setter
@Getter
public class GitHubUser {

    /**
     * GitHub用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 头像地址
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     * 用户说明
     */
    private String bio;

}
