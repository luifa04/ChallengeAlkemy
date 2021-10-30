
package com.web.disney.repositorios;

import com.web.disney.entidades.Pelicula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepositorio extends JpaRepository<Pelicula, String>{    
    @Query("select p from Pelicula p where p.titulo LIKE :q or p.calificacion LIKE :q or p.genero LIKE :q or p.fechaCreacion LIKE :q")
    List<Pelicula> findAllByQ(@Param("q") String q);
   
    @Query("select p from Pelicula p where p.personajes.nombre = :q")
    List<Pelicula> findAllByPersonaje(@Param("q") String q); 
    
    @Query("select p from Pelicula p where p.genero.id = :q")
    List<Pelicula> findAllByGenero(@Param("q") String q);
}
