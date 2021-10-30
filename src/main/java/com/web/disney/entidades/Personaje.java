/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.disney.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author mateo
 */
@Entity
public class Personaje {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
    
    @OneToOne
    private Foto foto;
    
    @JoinTable(
        name = "tabla_peliculas_personajes",
        joinColumns = @JoinColumn(name = "fk_personajes", nullable = false),
        inverseJoinColumns = @JoinColumn(name="fk_pelicula", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Pelicula> peliculas;
    
    public void addPelicula(Pelicula pelicula){
        if(this.getPeliculas() == null){
            this.setPeliculas(new ArrayList<>());
        }
        
        this.getPeliculas().add(pelicula);
    }

    public Personaje() {
    }

    public Personaje(String id, String nombre, Integer edad, Double peso, String historia, Foto foto, List<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.foto = foto;
        this.peliculas = peliculas;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * @return the peso
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    /**
     * @return the historia
     */
    public String getHistoria() {
        return historia;
    }

    /**
     * @param historia the historia to set
     */
    public void setHistoria(String historia) {
        this.historia = historia;
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
     * @return the peliculas
     */
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    /**
     * @param peliculas the peliculas to set
     */
    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @Override
    public String toString() {
        return "Personaje:" + "id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", peso=" + peso + ", historia=" + historia + ", foto=" + foto + ", peliculas=" + peliculas + '}';
    }  
}
