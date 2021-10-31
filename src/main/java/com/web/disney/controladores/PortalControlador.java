/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.disney.controladores;

import com.web.disney.errores.ErrorServicio;
import com.web.disney.servicios.GeneroServicio;
import com.web.disney.servicios.PeliculaServicio;
import com.web.disney.servicios.PersonajeServicio;
import com.web.disney.servicios.UsuarioServicio;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mateo
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    private PeliculaServicio peliculaServicio;
    
    @Autowired
    private PersonajeServicio personajeServicio;
    
    @Autowired
    private GeneroServicio generoServicio;
    
    @Autowired
    private UsuarioServicio UsuarioServicio;
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,@RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o Clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Sesion Finalizada");
        }
        return "login.html";
    }
    
    @PostMapping("/auth/register")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {

        try {
            UsuarioServicio.registrar(nombre, apellido, mail, clave1, clave2);
        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            ex.printStackTrace();

            return "/auth/register.html";
        }
        modelo.put("titulo", "Bienvenide al PET TINDER");
        modelo.put("descripcion", "USUARIO REGISTRADO CORRECTAMENTE");

        return "exito.html";
    }
    
    @GetMapping()
    public ArrayList<Object[]> getAllPeliculas(){
        return peliculaServicio.getAll();
    } 
    
    @GetMapping()
    public ArrayList<Object[]> getAllPerosnajes(){
        return personajeServicio.getAll();
    }
}
