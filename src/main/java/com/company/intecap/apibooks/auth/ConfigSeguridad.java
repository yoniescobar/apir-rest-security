package com.company.intecap.apibooks.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //indicamos que es una clase de configuración
public class ConfigSeguridad extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService usuarioService;
    @Bean //inyectamos el bean en el contenedor de Spring
    public BCryptPasswordEncoder passwordEncoder(){ // método que nos permite encriptar la contraseña
        return new BCryptPasswordEncoder(); //devuelve un objeto de tipo BCryptPasswordEncoder que es un objeto que nos permite encriptar la contraseña
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder()); //indicamos que el usuarioService es el que vamos a utilizar para autenticar
    }
    @Bean("authenticationManager") //explicito que el bean se llama authenticationManager
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //Desativamos algunas reglas que no necesitamos que utilice Spring Security
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/categorias").permitAll() //permitAll: permite a todos los usuarios acceder a la ruta
                //para todos lo demas se requiere autenticacion
                .anyRequest().authenticated() //authenticated: cualquier otra ruta requiere autenticación
                .and()
                .csrf().disable() //csrf: es un token que se envía en cada petición para validar que el usuario está autenticado
                //desactivamos el csrf origenes cruzados para que no nos genere problemas con el cliente, si fuera monolitico hay que activarlo
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //sessionCreationPolicy: es una política de creación de sesiones que nos permite indicarle a Spring Security que no cree sesiones
    }

    @Override
    public  void configure(WebSecurity web) throws  Exception{
        web.ignoring().antMatchers("/v2/api-docs","/configuration/**","/swagger-resources/**",
                    "/swagger-ui.html","/webjars/**","/api-docs/**"
                );
    }
}
