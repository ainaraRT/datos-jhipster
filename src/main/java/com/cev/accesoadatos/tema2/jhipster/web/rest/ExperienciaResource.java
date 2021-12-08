package com.cev.accesoadatos.tema2.jhipster.web.rest;

import com.cev.accesoadatos.tema2.jhipster.domain.Experiencia;
import com.cev.accesoadatos.tema2.jhipster.service.ExperienciaService;
import com.cev.accesoadatos.tema2.jhipster.web.rest.errors.BadRequestAlertException;
import com.cev.accesoadatos.tema2.jhipster.service.dto.ExperienciaCriteria;
import com.cev.accesoadatos.tema2.jhipster.service.ExperienciaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.cev.accesoadatos.tema2.jhipster.domain.Experiencia}.
 */
@RestController
@RequestMapping("/api")
public class ExperienciaResource {

    private final Logger log = LoggerFactory.getLogger(ExperienciaResource.class);

    private static final String ENTITY_NAME = "experiencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienciaService experienciaService;

    private final ExperienciaQueryService experienciaQueryService;

    public ExperienciaResource(ExperienciaService experienciaService, ExperienciaQueryService experienciaQueryService) {
        this.experienciaService = experienciaService;
        this.experienciaQueryService = experienciaQueryService;
    }

    /**
     * {@code POST  /experiencias} : Create a new experiencia.
     *
     * @param experiencia the experiencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new experiencia, or with status {@code 400 (Bad Request)} if the experiencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //Método para dar de alta una experiencia nueva
    @PostMapping("/experiencias")
    public ResponseEntity<Experiencia> createExperiencia(@Valid @RequestBody Experiencia experiencia) throws URISyntaxException {
        log.debug("REST request to save Experiencia : {}", experiencia);
        if (experiencia.getId() != null) {
            throw new BadRequestAlertException("A new experiencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Experiencia result = experienciaService.save(experiencia);
        return ResponseEntity.created(new URI("/api/experiencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /experiencias} : Updates an existing experiencia.
     *
     * @param experiencia the experiencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experiencia,
     * or with status {@code 400 (Bad Request)} if the experiencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the experiencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //Método para modificar una experiencia
    @PutMapping("/experiencias")
    public ResponseEntity<Experiencia> updateExperiencia(@Valid @RequestBody Experiencia experiencia) throws URISyntaxException {
        log.debug("REST request to update Experiencia : {}", experiencia);
        if (experiencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Experiencia result = experienciaService.save(experiencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, experiencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /experiencias} : get all the experiencias.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of experiencias in body.
     */
    //Método para ver todas las experiencias
    @GetMapping("/experiencias")
    public ResponseEntity<List<Experiencia>> getAllExperiencias(ExperienciaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Experiencias by criteria: {}", criteria);
        Page<Experiencia> page = experienciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /experiencias/count} : count all the experiencias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/experiencias/count")
    public ResponseEntity<Long> countExperiencias(ExperienciaCriteria criteria) {
        log.debug("REST request to count Experiencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(experienciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /experiencias/:id} : get the "id" experiencia.
     *
     * @param id the id of the experiencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the experiencia, or with status {@code 404 (Not Found)}.
     */
    //Método para ver una experiencia concreta
    @GetMapping("/experiencias/{id}")
    public ResponseEntity<Experiencia> getExperiencia(@PathVariable Long id) {
        log.debug("REST request to get Experiencia : {}", id);
        Optional<Experiencia> experiencia = experienciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experiencia);
    }

    /**
     * {@code DELETE  /experiencias/:id} : delete the "id" experiencia.
     *
     * @param id the id of the experiencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    //Método para eliminar una experiencia
    @DeleteMapping("/experiencias/{id}")
    public ResponseEntity<Void> deleteExperiencia(@PathVariable Long id) {
        log.debug("REST request to delete Experiencia : {}", id);
        experienciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/experiencias?query=:query} : search for the experiencia corresponding
     * to the query.
     *
     * @param query the query of the experiencia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    //Método para buscar una experiencia
    @GetMapping("/_search/experiencias")
    public ResponseEntity<List<Experiencia>> searchExperiencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Experiencias for query {}", query);
        Page<Experiencia> page = experienciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
