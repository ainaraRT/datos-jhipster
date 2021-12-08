package com.cev.accesoadatos.tema2.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cev.accesoadatos.tema2.jhipster.domain.Experiencia} entity. This class is used
 * in {@link com.cev.accesoadatos.tema2.jhipster.web.rest.ExperienciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /experiencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExperienciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private StringFilter descripcion;

    private StringFilter localizacion;

    private InstantFilter fecha;

    public ExperienciaCriteria(){
    }

    public ExperienciaCriteria(ExperienciaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.localizacion = other.localizacion == null ? null : other.localizacion.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
    }

    @Override
    public ExperienciaCriteria copy() {
        return new ExperienciaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(StringFilter localizacion) {
        this.localizacion = localizacion;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExperienciaCriteria that = (ExperienciaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(localizacion, that.localizacion) &&
            Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        descripcion,
        localizacion,
        fecha
        );
    }

    @Override
    public String toString() {
        return "ExperienciaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (localizacion != null ? "localizacion=" + localizacion + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
            "}";
    }

}
