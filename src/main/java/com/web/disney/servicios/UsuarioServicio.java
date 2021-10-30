/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.disney.servicios;

import com.web.disney.entidades.Usuario;
import com.web.disney.errores.ErrorServicio;
import com.web.disney.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 *
 * @author mateo
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private MailServicio MailServicio;
    
    @Transactional
    public void registrar(String nombre, String apellido, String mail, String clave, String clave2) throws ErrorServicio {

        validar(nombre, apellido, mail, clave, clave2);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        
        String encriptada = new BCryptPasswordEncoder().encode(apellido);
        usuario.setClave(encriptada);
        
        usuario.setAlta(new Date());

        usuarioRepositorio.save(usuario);
        MailServicio.enviar("Bienvenido a DISNEY, Gracias! por registrarte", "¡¡Disney!!", usuario.getMail());
    }
    
    @Transactional
    public void modificar(String id, String nombre, String apellido, String mail, String clave, String clave2) throws ErrorServicio {

        validar(nombre, apellido, mail, clave, clave2);
       

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setMail(mail);
            
            String encriptada = new BCryptPasswordEncoder().encode(apellido);
            usuario.setClave(encriptada);

            usuarioRepositorio.save(usuario);
        } else {

            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }
    
    
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepositorio.save(usuario);
        } else {

            throw new ErrorServicio("No se encontró el usuario solicitado");
        }

    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepositorio.save(usuario);
        } else {

            throw new ErrorServicio("No se encontró el usuario solicitado");
        }

    }
    
    public void validar(String nombre, String apellido, String mail, String clave, String clave2) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }

        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo");
        }

        if (clave == null || clave.isEmpty() || clave.length() <= 5) {
            throw new ErrorServicio("La clave no puede ser nula y debe tener mas de 5 caracteres");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser identicas");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }   
}
