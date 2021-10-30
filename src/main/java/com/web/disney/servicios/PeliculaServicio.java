/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.disney.servicios;

import com.web.disney.entidades.Foto;
import com.web.disney.entidades.Genero;
import com.web.disney.entidades.Pelicula;
import com.web.disney.entidades.Personaje;
import com.web.disney.errores.ErrorServicio;
import com.web.disney.repositorios.PeliculaRepositorio;
import com.web.disney.repositorios.PersonajeRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mateo
 */
@Service
public class PeliculaServicio {
    
   @Autowired
   private PeliculaRepositorio peliculaRepositorio;
   
   @Autowired
   private GeneroServicio generoServicio;
   
   @Autowired 
   private FotoServicio fotoServicio;
   
   @Autowired
   private PersonajeRepositorio personajeRepositorio;
   
    @Transactional
    public Pelicula Save(MultipartFile archivo,String titulo,Integer calificacion,Genero genero) throws ErrorServicio {
        
        Pelicula pelicula= new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setFechaCreacion(new Date());
        pelicula.setCalificacion(calificacion);
        
        Foto foto = fotoServicio.guardar(archivo);
        pelicula.setFoto(foto);
        
        return peliculaRepositorio.save(pelicula);
    }
           
    @Transactional
    public Pelicula Save(Pelicula pelicula) throws ErrorServicio {
    if (pelicula.getTitulo().isEmpty() || pelicula.getTitulo()== null) {
      throw new ErrorServicio(" El titulo de la pelicula no puede ser nulo y no puede estar vacio");
    }
    if (pelicula.getCalificacion() < 0 || pelicula.getCalificacion() > 5 ) {
      throw new ErrorServicio(" la calificacion no puede ser mayor a 5 o menor a 0");
    }
    if (pelicula.getGenero() == null) {
      throw new ErrorServicio(" el genero no puede estar vacio");
    }else{
      pelicula.setGenero(generoServicio.findById(pelicula.getGenero()));
    }
    if (pelicula.getFoto() == null) {
       throw new ErrorServicio("la pelicula debe contener una imagen");
    }
    return peliculaRepositorio.save(pelicula);
   }
    
    @Transactional
    public void AddPersonajes(String idPersonaje, String idPelicula){
        Personaje personaje= personajeRepositorio.findById(idPersonaje).get();
        
        Pelicula pelicula= peliculaRepositorio.findById(idPelicula).get();
        
        pelicula.getPersonajes().add(personaje);
    }
    
    public List<Pelicula> listAll() {
      return peliculaRepositorio.findAll();
    }

    public List<Pelicula> listAllByQ(String q) {
      return peliculaRepositorio.findAllByQ("%" + q + "%");
    }
    
    public List<Pelicula> listAllbyPersonaje(String nombre) {
      return peliculaRepositorio.findAllByPersonaje(nombre);
    }
    
    public List<Pelicula> listAllbyGenero(String nombre) {
      return peliculaRepositorio.findAllByGenero(nombre);
    }

    public Optional<Pelicula> findById(String id) {
      return peliculaRepositorio.findById(id);
    }
    
    @Transactional
    public void delete(Pelicula pelicula) {
      peliculaRepositorio.delete(pelicula);
    }
    
    @Transactional
    public void deleteFieldGenero(Genero genero) {
      List<Pelicula> peliculas = listAllbyGenero(genero.getNombre());
      for (Pelicula pelicula : peliculas) {
        pelicula.setGenero(null);
       }
      peliculaRepositorio.saveAll(peliculas);
  }
    
    //en el caso que se elimine personajes, se debe eliminar estos de las peliculas.
    @Transactional
    public void deleteFieldPersonajes(Personaje personaje) {
      List<Pelicula> peliculas = listAllbyPersonaje(personaje.getId());
      for (Pelicula  pelicula : peliculas) {
          for (Personaje personajeSolo : pelicula.getPersonajes()) {
              if (personajeSolo.getId().equals(personaje.getId())) {
                  pelicula.getPersonajes().remove(personajeSolo);
              }     
          }
       }
      peliculaRepositorio.saveAll(peliculas);
  }

   @Transactional
    public void deleteById(String id) {
      Optional<Pelicula> optional = peliculaRepositorio.findById(id);
      if (optional.isPresent()) {
         peliculaRepositorio.delete(optional.get());
      }
  }
    
}
