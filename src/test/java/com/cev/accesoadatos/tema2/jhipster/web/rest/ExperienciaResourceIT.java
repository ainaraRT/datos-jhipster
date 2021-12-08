package com.cev.accesoadatos.tema2.jhipster.web.rest;

import com.cev.accesoadatos.tema2.jhipster.JhipsterApp;
import com.cev.accesoadatos.tema2.jhipster.domain.Experiencia;
import com.cev.accesoadatos.tema2.jhipster.repository.ExperienciaRepository;
import com.cev.accesoadatos.tema2.jhipster.repository.search.ExperienciaSearchRepository;
import com.cev.accesoadatos.tema2.jhipster.service.ExperienciaService;
import com.cev.accesoadatos.tema2.jhipster.web.rest.errors.ExceptionTranslator;
import com.cev.accesoadatos.tema2.jhipster.service.dto.ExperienciaCriteria;
import com.cev.accesoadatos.tema2.jhipster.service.ExperienciaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.cev.accesoadatos.tema2.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExperienciaResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class ExperienciaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZACION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA = Instant.ofEpochMilli(-1L);

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @Autowired
    private ExperienciaService experienciaService;

    /**
     * This repository is mocked in the com.cev.accesoadatos.tema2.jhipster.repository.search test package.
     *
     * @see com.cev.accesoadatos.tema2.jhipster.repository.search.ExperienciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExperienciaSearchRepository mockExperienciaSearchRepository;

    @Autowired
    private ExperienciaQueryService experienciaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restExperienciaMockMvc;

    private Experiencia experiencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienciaResource experienciaResource = new ExperienciaResource(experienciaService, experienciaQueryService);
        this.restExperienciaMockMvc = MockMvcBuilders.standaloneSetup(experienciaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experiencia createEntity(EntityManager em) {
        Experiencia experiencia = new Experiencia()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .localizacion(DEFAULT_LOCALIZACION)
            .fecha(DEFAULT_FECHA);
        return experiencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experiencia createUpdatedEntity(EntityManager em) {
        Experiencia experiencia = new Experiencia()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .localizacion(UPDATED_LOCALIZACION)
            .fecha(UPDATED_FECHA);
        return experiencia;
    }

    @BeforeEach
    public void initTest() {
        experiencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperiencia() throws Exception {
        int databaseSizeBeforeCreate = experienciaRepository.findAll().size();

        // Create the Experiencia
        restExperienciaMockMvc.perform(post("/api/experiencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiencia)))
            .andExpect(status().isCreated());

        // Validate the Experiencia in the database
        List<Experiencia> experienciaList = experienciaRepository.findAll();
        assertThat(experienciaList).hasSize(databaseSizeBeforeCreate + 1);
        Experiencia testExperiencia = experienciaList.get(experienciaList.size() - 1);
        assertThat(testExperiencia.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testExperiencia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testExperiencia.getLocalizacion()).isEqualTo(DEFAULT_LOCALIZACION);
        assertThat(testExperiencia.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the Experiencia in Elasticsearch
        verify(mockExperienciaSearchRepository, times(1)).save(testExperiencia);
    }

    @Test
    @Transactional
    public void createExperienciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienciaRepository.findAll().size();

        // Create the Experiencia with an existing ID
        experiencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienciaMockMvc.perform(post("/api/experiencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiencia)))
            .andExpect(status().isBadRequest());

        // Validate the Experiencia in the database
        List<Experiencia> experienciaList = experienciaRepository.findAll();
        assertThat(experienciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Experiencia in Elasticsearch
        verify(mockExperienciaSearchRepository, times(0)).save(experiencia);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienciaRepository.findAll().size();
        // set the field null
        experiencia.setTitulo(null);

        // Create the Experiencia, which fails.

        restExperienciaMockMvc.perform(post("/api/experiencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiencia)))
            .andExpect(status().isBadRequest());

        List<Experiencia> experienciaList = experienciaRepository.findAll();
        assertThat(experienciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperiencias() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList
        restExperienciaMockMvc.perform(get("/api/experiencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experiencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getExperiencia() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get the experiencia
        restExperienciaMockMvc.perform(get("/api/experiencias/{id}", experiencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experiencia.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.localizacion").value(DEFAULT_LOCALIZACION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getAllExperienciasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where titulo equals to DEFAULT_TITULO
        defaultExperienciaShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the experienciaList where titulo equals to UPDATED_TITULO
        defaultExperienciaShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllExperienciasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultExperienciaShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the experienciaList where titulo equals to UPDATED_TITULO
        defaultExperienciaShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllExperienciasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where titulo is not null
        defaultExperienciaShouldBeFound("titulo.specified=true");

        // Get all the experienciaList where titulo is null
        defaultExperienciaShouldNotBeFound("titulo.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienciasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultExperienciaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the experienciaList where descripcion equals to UPDATED_DESCRIPCION
        defaultExperienciaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllExperienciasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultExperienciaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the experienciaList where descripcion equals to UPDATED_DESCRIPCION
        defaultExperienciaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllExperienciasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where descripcion is not null
        defaultExperienciaShouldBeFound("descripcion.specified=true");

        // Get all the experienciaList where descripcion is null
        defaultExperienciaShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienciasByLocalizacionIsEqualToSomething() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where localizacion equals to DEFAULT_LOCALIZACION
        defaultExperienciaShouldBeFound("localizacion.equals=" + DEFAULT_LOCALIZACION);

        // Get all the experienciaList where localizacion equals to UPDATED_LOCALIZACION
        defaultExperienciaShouldNotBeFound("localizacion.equals=" + UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void getAllExperienciasByLocalizacionIsInShouldWork() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where localizacion in DEFAULT_LOCALIZACION or UPDATED_LOCALIZACION
        defaultExperienciaShouldBeFound("localizacion.in=" + DEFAULT_LOCALIZACION + "," + UPDATED_LOCALIZACION);

        // Get all the experienciaList where localizacion equals to UPDATED_LOCALIZACION
        defaultExperienciaShouldNotBeFound("localizacion.in=" + UPDATED_LOCALIZACION);
    }

    @Test
    @Transactional
    public void getAllExperienciasByLocalizacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where localizacion is not null
        defaultExperienciaShouldBeFound("localizacion.specified=true");

        // Get all the experienciaList where localizacion is null
        defaultExperienciaShouldNotBeFound("localizacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienciasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where fecha equals to DEFAULT_FECHA
        defaultExperienciaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the experienciaList where fecha equals to UPDATED_FECHA
        defaultExperienciaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllExperienciasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultExperienciaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the experienciaList where fecha equals to UPDATED_FECHA
        defaultExperienciaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllExperienciasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienciaRepository.saveAndFlush(experiencia);

        // Get all the experienciaList where fecha is not null
        defaultExperienciaShouldBeFound("fecha.specified=true");

        // Get all the experienciaList where fecha is null
        defaultExperienciaShouldNotBeFound("fecha.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExperienciaShouldBeFound(String filter) throws Exception {
        restExperienciaMockMvc.perform(get("/api/experiencias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experiencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restExperienciaMockMvc.perform(get("/api/experiencias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExperienciaShouldNotBeFound(String filter) throws Exception {
        restExperienciaMockMvc.perform(get("/api/experiencias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExperienciaMockMvc.perform(get("/api/experiencias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingExperiencia() throws Exception {
        // Get the experiencia
        restExperienciaMockMvc.perform(get("/api/experiencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperiencia() throws Exception {
        // Initialize the database
        experienciaService.save(experiencia);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockExperienciaSearchRepository);

        int databaseSizeBeforeUpdate = experienciaRepository.findAll().size();

        // Update the experiencia
        Experiencia updatedExperiencia = experienciaRepository.findById(experiencia.getId()).get();
        // Disconnect from session so that the updates on updatedExperiencia are not directly saved in db
        em.detach(updatedExperiencia);
        updatedExperiencia
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .localizacion(UPDATED_LOCALIZACION)
            .fecha(UPDATED_FECHA);

        restExperienciaMockMvc.perform(put("/api/experiencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperiencia)))
            .andExpect(status().isOk());

        // Validate the Experiencia in the database
        List<Experiencia> experienciaList = experienciaRepository.findAll();
        assertThat(experienciaList).hasSize(databaseSizeBeforeUpdate);
        Experiencia testExperiencia = experienciaList.get(experienciaList.size() - 1);
        assertThat(testExperiencia.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testExperiencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testExperiencia.getLocalizacion()).isEqualTo(UPDATED_LOCALIZACION);
        assertThat(testExperiencia.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the Experiencia in Elasticsearch
        verify(mockExperienciaSearchRepository, times(1)).save(testExperiencia);
    }

    @Test
    @Transactional
    public void updateNonExistingExperiencia() throws Exception {
        int databaseSizeBeforeUpdate = experienciaRepository.findAll().size();

        // Create the Experiencia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienciaMockMvc.perform(put("/api/experiencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiencia)))
            .andExpect(status().isBadRequest());

        // Validate the Experiencia in the database
        List<Experiencia> experienciaList = experienciaRepository.findAll();
        assertThat(experienciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Experiencia in Elasticsearch
        verify(mockExperienciaSearchRepository, times(0)).save(experiencia);
    }

    @Test
    @Transactional
    public void deleteExperiencia() throws Exception {
        // Initialize the database
        experienciaService.save(experiencia);

        int databaseSizeBeforeDelete = experienciaRepository.findAll().size();

        // Delete the experiencia
        restExperienciaMockMvc.perform(delete("/api/experiencias/{id}", experiencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Experiencia> experienciaList = experienciaRepository.findAll();
        assertThat(experienciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Experiencia in Elasticsearch
        verify(mockExperienciaSearchRepository, times(1)).deleteById(experiencia.getId());
    }

    @Test
    @Transactional
    public void searchExperiencia() throws Exception {
        // Initialize the database
        experienciaService.save(experiencia);
        when(mockExperienciaSearchRepository.search(queryStringQuery("id:" + experiencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(experiencia), PageRequest.of(0, 1), 1));
        // Search the experiencia
        restExperienciaMockMvc.perform(get("/api/_search/experiencias?query=id:" + experiencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experiencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experiencia.class);
        Experiencia experiencia1 = new Experiencia();
        experiencia1.setId(1L);
        Experiencia experiencia2 = new Experiencia();
        experiencia2.setId(experiencia1.getId());
        assertThat(experiencia1).isEqualTo(experiencia2);
        experiencia2.setId(2L);
        assertThat(experiencia1).isNotEqualTo(experiencia2);
        experiencia1.setId(null);
        assertThat(experiencia1).isNotEqualTo(experiencia2);
    }
}
