package com.company.intecap.apibooks.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer //Habilita el servidor de recursos
public class ConfiguracionRecursos extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/categorias").permitAll() //permitAll: permite a todos los usuarios acceder a la ruta
                .anyRequest().authenticated(); //authenticated: cualquier otra ruta requiere autenticación
    }

    //copiar el metodo en ConfiguracionSeguridad.java
    //porque esta configuración es para el servidor de recursos lo maneja OAuth2 y no Spring Security
}
