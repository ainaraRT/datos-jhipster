package com.cev.accesoadatos.tema2.jhipster.service;

import com.cev.accesoadatos.tema2.jhipster.domain.Experiencia;
import com.cev.accesoadatos.tema2.jhipster.repository.ExperienciaRepository;
import com.cev.accesoadatos.tema2.jhipster.repository.search.ExperienciaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Experiencia}.
 */
@Service
@Transactional
public class ExperienciaService {

    private final Logger log = LoggerFactory.getLogger(ExperienciaService.class);

    private final ExperienciaRepository experienciaRepository;

    private final ExperienciaSearchRepository experienciaSearchRepository;

    public ExperienciaService(ExperienciaRepository experienciaRepository, ExperienciaSearchRepository experienciaSearchRepository) {
        this.experienciaRepository = experienciaRepository;
        this.experienciaSearchRepository = experienciaSearchRepository;
    }

    /**
     * Save a experiencia.
     *
     * @param experiencia the entity to save.
     * @return the persisted entity.
     */
    public Experiencia save(Experiencia experiencia) {
        log.debug("Request to save Experiencia : {}", experiencia);
        Experiencia result = experienciaRepository.save(experiencia);
        experienciaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the experiencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Experiencia> findAll(Pageable pageable) {
        log.debug("Request to get all Experiencias");
        return experienciaRepository.findAll(pageable);
    }


    /**
     * Get one experiencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Experiencia> findOne(Long id) {
        log.debug("Request to get Experiencia : {}", id);
        return experienciaRepository.findById(id);
    }

    /**
     * Delete the experiencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Experiencia : {}", id);
        experienciaRepository.deleteById(id);
        experienciaSearchRepository.deleteById(id);
    }

    /**
     * Search for the experiencia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Experiencia> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Experiencias for query {}", query);
        return experienciaSearchRepository.search(queryStringQuery(query), pageable);    }
}
