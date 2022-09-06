package com.bitprogress.repository.app;

import com.bitprogress.model.app.AppESO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author wuwuwupx
 */
public interface AppRepository extends ElasticsearchRepository<AppESO, Long> {
}
