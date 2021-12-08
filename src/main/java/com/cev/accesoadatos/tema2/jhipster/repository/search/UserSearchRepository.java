package com.cev.accesoadatos.tema2.jhipster.repository.search;

import com.cev.accesoadatos.tema2.jhipster.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
