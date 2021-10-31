
package com.web.disney.servicios;

import com.web.disney.entidades.Foto;
import com.web.disney.entidades.Pelicula;
import com.web.disney.entidades.Personaje;
import com.web.disney.errores.ErrorServicio;
import com.web.disney.repositorios.PeliculaRepositorio;
import com.web.disney.repositorios.PersonajeRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonajeServicio {
    
   @Autowired
   private PeliculaRepositorio peliculaRepositorio;
   
   @Autowired 
   private FotoServicio fotoServicio;
   
   @Autowired
   private PersonajeRepositorio personajeRepositorio;
    
    
    
    @Transactional
    public Personaje Save(MultipartFile archivo,String nombre,Integer edad,double peso, String historia) throws ErrorServicio {
        
        Personaje personaje= new Personaje();
        personaje.setNombre(nombre);
        personaje.setEdad(edad);
        personaje.setPeso(peso);
        personaje.setHistoria(historia);
        
        Foto foto = fotoServicio.guardar(archivo);
        personaje.setFoto(foto);
        
        return personajeRepositorio.save(personaje);
    }
           
    @Transactional
    public Personaje Save(Personaje personaje) throws ErrorServicio {
    if (personaje.getPeso() < 0 ) {
      throw new ErrorServicio("El peso del personaje no puede ser menor a 0");
    } 
    if (personaje.getNombre().isEmpty() || personaje.getNombre()== null) {
      throw new ErrorServicio(" El nombre del personaje no puede ser nulo y no puede estar vacio");
    }
    if (personaje.getEdad() < 0 ) {
      throw new ErrorServicio(" la edad no puede ser menor a 0");
    }
    if (personaje.getHistoria() == null || personaje.getHistoria().length() < 200) {
      throw new ErrorServicio("El personaje debe contener una historio y esta debe contener menos de 200 caracteres");
    }
    if (personaje.getFoto() == null ) {
       throw new ErrorServicio("el personaje debe contener una imagen");
    }
    return personajeRepositorio.save(personaje);
   }
    
    @Transactional
    public void AddPeliculas(String idPersonaje, String idPelicula){
        Personaje personaje= personajeRepositorio.findById(idPersonaje).get();
        
        Pelicula pelicula= peliculaRepositorio.findById(idPelicula).get();
        
        personaje.getPeliculas().add(pelicula);
    }
    
    public ArrayList<Object[]> getAll(){
        return personajeRepositorio.getAll();
    }
    
    public List<Personaje> listAll() {
      return personajeRepositorio.findAll();
    }

    public List<Personaje> listAllByQ(String q) {
      return personajeRepositorio.findAllByQ("%" + q + "%");
    }
    
    public List<Personaje> listAllbyPelicula(String nombre) {
      return personajeRepositorio.findAllByPeliculas(nombre);
    }

    public Optional<Personaje> findById(String id) {
      return personajeRepositorio.findById(id);
    }
    
    @Transactional
    public void delete(Personaje personaje) {
      personajeRepositorio.delete(personaje);
    }
    
    //en el caso que se elimine una pelicula, es necesario eliminar la pelicula de los personajes
    @Transactional
    public void deleteFieldPeliculas(Pelicula pelicula) {
      List<Personaje> personajes = listAllbyPelicula(pelicula.getId());
      for (Personaje personaje : personajes) {
          for (Pelicula peliculas : personaje.getPeliculas()) {
              if (peliculas.getId().equals(pelicula.getId())) {
                  personaje.getPeliculas().remove(peliculas);
              }     
          }
       }
      personajeRepositorio.saveAll(personajes);
  }

   @Transactional
    public void deleteById(String id) {
      Optional<Personaje > optional = personajeRepositorio.findById(id);
      if (optional.isPresent()) {
         personajeRepositorio.delete(optional.get());
      }
  }
    
}
