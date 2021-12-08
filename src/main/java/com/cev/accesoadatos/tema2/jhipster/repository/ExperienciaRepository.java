package com.cev.accesoadatos.tema2.jhipster.repository;
import com.cev.accesoadatos.tema2.jhipster.domain.Experiencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Experiencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Long>, JpaSpecificationExecutor<Experiencia> {

}
