package com.cev.accesoadatos.tema2.jhipster.repository.search;
import com.cev.accesoadatos.tema2.jhipster.domain.Experiencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Experiencia} entity.
 */
public interface ExperienciaSearchRepository extends ElasticsearchRepository<Experiencia, Long> {
}
