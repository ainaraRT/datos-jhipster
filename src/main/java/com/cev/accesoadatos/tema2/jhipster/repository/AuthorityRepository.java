package com.cev.accesoadatos.tema2.jhipster.repository;

import com.cev.accesoadatos.tema2.jhipster.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
