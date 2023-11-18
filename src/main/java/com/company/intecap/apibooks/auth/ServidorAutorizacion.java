package com.company.intecap.apibooks.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration //Indica que es una clase de configuración
@EnableAuthorizationServer //Habilita el servidor de autorización
public class ServidorAutorizacion extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //BCryptPasswordEncoder: es un objeto que nos permite encriptar la contraseña

    @Autowired
    @Qualifier("authenticationManager") //Qualifier: nos permite indicar el nombre del bean que queremos inyectar
    private AuthenticationManager authenticationManager; //AuthenticationManager: es un objeto que nos permite configurar la autenticación


    //sobrescribimos 3 metodos de la clase AuthorizationServerConfigurerAdapter
    @Override //primer metodo sobrescrito de la clase AuthorizationServerConfigurerAdapter
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()") //tokenKeyAccess: es un objeto que nos permite configurar el acceso a la clave del token
                .checkTokenAccess("isAuthenticated()"); //checkTokenAccess: es un objeto que nos permite configurar el acceso al token siempre que el usuario esté autenticado
    }

    @Override //segundo metodo sobrescrito de la clase AuthorizationServerConfigurerAdapter
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { //ClientDetailsServiceConfigurer: es un objeto que nos permite configurar los clientes

        clients.inMemory().withClient("clienterest") //withClient: es un objeto que nos permite configurar el cliente POSTMAN
                .secret(passwordEncoder.encode("1234")) //secret: es un objeto que nos permite configurar la contraseña del cliente
                .scopes("read", "write") //scopes: es un objeto que nos permite configurar los alcances del cliente
                .authorizedGrantTypes("password", "refresh_token") //authorizedGrantTypes: es un objeto que nos permite configurar los tipos de concesión del cliente. refresh_token: es un objeto que nos permite configurar el tipo de concesión de actualización del cliente
                .accessTokenValiditySeconds(4000) //4000 = 4seg  //accessTokenValiditySeconds: es un objeto que nos permite configurar la validez del token 4 SEGUNDOS
                .refreshTokenValiditySeconds(4000); //refreshTokenValiditySeconds: es un objeto que nos permite configurar la validez del token de actualización 4 SEGUNDOS
    }

    @Override //tercer metodo sobrescrito de la clase AuthorizationServerConfigurerAdapter
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) //authenticationManager: es un objeto que nos permite configurar la autenticación
                .tokenStore(tokenStore())//tokenStore: es un objeto que nos permite configurar el almacenamiento de los tokens
                .accessTokenConverter(accessTokenConverter()); //accessTokenConverter: es un metodo convertidor
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter()); //JwtTokenStore: es un objeto que nos permite configurar el almacenamiento de los tokens
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() { //JwtAccessTokenConverter: es un metodo convertidor
        return new JwtAccessTokenConverter(); // retorna un objeto JwtAccessTokenConverter que nos permite configurar el convertidor
    }
}
