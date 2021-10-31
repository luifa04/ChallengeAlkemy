
package com.web.disney.repositorios;

import com.web.disney.entidades.Personaje;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonajeRepositorio extends JpaRepository<Personaje, String>{
    @Query("select p from Personaje p where p.nombre LIKE :q or p.edad LIKE :q or p.peso LIKE :q or p.historial LIKE :q")
    List<Personaje> findAllByQ(@Param("q") String q);
    
    @Query("select p from Personaje p where p.peliculas.id = :q")
    List<Personaje> findAllByPeliculas(@Param("q") String q); 
    
    @Query(value = "SELECT nombre,foto FROM Personaje",nativeQuery = true)
    public ArrayList<Object[]> getAll();
    
}
