
package com.web.disney.seguridad;

import com.web.disney.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UsuarioServicio usuarioServicio;
    
    // @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                    .antMatchers("/css/", "/js/", "/img/", "/*")
                    .permitAll()
                .and().formLogin()
                    .loginPage("/login") // Que formulario esta mi login
                            .loginProcessingUrl("/logincheck")
                            .usernameParameter("username") 
                            .passwordParameter("password")
                            .defaultSuccessUrl("/inicio") // A que URL viaja
                            .permitAll()
                .and().logout() // Aca configuro la salida
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll().and().csrf().disable();
    }
    
}
