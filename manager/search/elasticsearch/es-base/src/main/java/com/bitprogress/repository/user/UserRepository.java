package com.bitprogress.repository.user;

import com.bitprogress.model.user.UserESO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author wuwuwupx
 */
public interface UserRepository extends ElasticsearchRepository<UserESO, Long> {
}
