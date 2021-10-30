/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.disney.repositorios;

import com.web.disney.entidades.Genero;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mateo
 */
@Repository
public interface GeneroRepositorio extends JpaRepository<Genero, String>{
  
  @Query("select p from Genero p where p.nombre LIKE :q")
  List<Genero> findAll(@Param("q") String q);
  
}
