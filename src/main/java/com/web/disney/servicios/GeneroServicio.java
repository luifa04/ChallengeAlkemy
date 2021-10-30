
package com.web.disney.servicios;

import com.web.disney.entidades.Foto;
import com.web.disney.entidades.Genero;
import com.web.disney.errores.ErrorServicio;
import com.web.disney.repositorios.GeneroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//listo

@Service
public class GeneroServicio {
    @Autowired
    private GeneroRepositorio generoRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Autowired
    private PeliculaServicio peliculaServicio;
    
    @Transactional
    public Genero AgregaGenero(MultipartFile archivo,String nombre) throws ErrorServicio {
        validar(archivo, nombre);
        
        Genero genero = new Genero();
        genero.setNombre(nombre);
        Foto foto = fotoServicio.guardar(archivo);
        genero.setFoto(foto);
        return generoRepositorio.save(genero);
    }
    
    @Transactional
    public void modificaGenero(MultipartFile archivo,String nombre, String id) throws ErrorServicio {
        validar(archivo,nombre);

        Optional<Genero> respuesta = generoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Genero genero = respuesta.get();
            genero.setNombre(nombre);
                
            String idFoto = null;
            if (genero.getFoto() != null) {
                idFoto = genero.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            genero.setFoto(foto);
            generoRepositorio.save(genero);
        } else {
            throw new ErrorServicio(("No existe un genero con el ID solicitado"));
        }
    }

   public void validar(MultipartFile archivo,String nombre) throws ErrorServicio{
      if (nombre == null) {
       throw new ErrorServicio("El nombre del genero no puede ser nulo");
      }
      if (archivo == null) {
       throw new ErrorServicio("El genero debe contener una imagen");
      }
    }
   
   
    public List<Genero> listAll() {
      return generoRepositorio.findAll();
    }

    public List<Genero> listAll(String q) {
      return generoRepositorio.findAll("%" + q + "%");
    }
    
    public Genero findById(Genero genero) {
    Optional<Genero> optional = generoRepositorio.findById(genero.getId());
    if (optional.isPresent()) {
     genero = optional.get();
    }
    return genero;
  }

    public Optional<Genero> findById(String id) {
      return generoRepositorio.findById(id);
    }

    @Transactional
    public void delete(Genero genero) {
      generoRepositorio.delete(genero);
    }

    @Transactional
    public void deleteById(String id) {
      Optional<Genero> optional = generoRepositorio.findById(id);
      if (optional.isPresent()) {
        Genero genero = optional.get();
        
        peliculaServicio.deleteFieldGenero(genero);
        generoRepositorio.delete(genero);
    }
  }

   
}
