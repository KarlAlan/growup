package com.rcstc.growup.repository.search;

import com.rcstc.growup.domain.Consume;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Consume entity.
 */
public interface ConsumeSearchRepository extends ElasticsearchRepository<Consume, Long> {
}
