package com.cev.accesoadatos.tema2.jhipster.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Experiencia.
 */
@Entity
@Table(name = "experiencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "experiencia")
public class Experiencia implements Serializable {

    private static final long serialVersionUID = 1L;

    //Generar el id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    //Validaciones
    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "titulo", length = 255, nullable = false)
    private String titulo;

    @Size(min = 10, max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Size(min = 10, max = 255)
    @Column(name = "localizacion", length = 255)
    private String localizacion;

    @Column(name = "fecha")
    private Instant fecha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    //Getters y Setters de las variables
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Experiencia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Experiencia descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public Experiencia localizacion(String localizacion) {
        this.localizacion = localizacion;
        return this;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Experiencia fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Experiencia)) {
            return false;
        }
        return id != null && id.equals(((Experiencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    //Método para transformar las variables en strings
    @Override
    public String toString() {
        return "Experiencia{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", localizacion='" + getLocalizacion() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
