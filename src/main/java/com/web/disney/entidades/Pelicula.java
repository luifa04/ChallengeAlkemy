/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.disney.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mateo
 */
@Entity
public class Pelicula {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String titulo;
    private Integer calificacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @OneToOne
    private Foto foto;
    
    @ManyToOne
    private Genero genero;
    
    @ManyToMany(mappedBy = "peliculas")
    private List<Personaje> personajes;

    public Pelicula() {
    }

    public Pelicula(String titulo, Integer calificacion, Date fechaCreacion, Foto foto, Genero genero, List<Personaje> personajes) {
        this.titulo = titulo;
        this.calificacion = calificacion;
        this.fechaCreacion = fechaCreacion;
        this.foto = foto;
        this.genero = genero;
        this.personajes = personajes;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the calificacion
     */
    public Integer getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the foto
     */
    public Foto getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    /**
     * @return the genero
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * @return the personajes
     */
    public List<Personaje> getPersonajes() {
        return personajes;
    }

    /**
     * @param personajes the personajes to set
     */
    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    @Override
    public String toString() {
        return "Pelicula{" + "id=" + id + ", titulo=" + titulo + ", calificacion=" + calificacion + ", fechaCreacion=" + fechaCreacion + ", foto=" + foto + ", genero=" + genero + ", personajes=" + personajes + '}';
    }  
}
