package com.bitprogress.repository.user;

import com.bitprogress.model.user.WechatOaUserESO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author wuwuwupx
 */
public interface WechatOaUserRepository extends ElasticsearchRepository<WechatOaUserESO, Long> {
}
