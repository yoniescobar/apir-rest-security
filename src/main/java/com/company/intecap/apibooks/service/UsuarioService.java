package com.company.intecap.apibooks.service;

import com.company.intecap.apibooks.model.Usuario;
import com.company.intecap.apibooks.model.dao.IUsuarioDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {
    //UserDetailsService es una interfaz de Spring Security que se utiliza para recuperar los detalles del usuario conectado actualmente, dada su identificación de usuario. Implementa un método llamado loadUserByUsername () que se utiliza para recuperar los detalles del usuario conectado actualmente.

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UsuarioService.class); // findByNombreUsuario es un método personalizado de IUsuarioDao que busca un usuario por nombre de usuario.

    @Autowired // @Autowired se utiliza para inyectar dependencias en el bean de Spring.
    private IUsuarioDao usuarioDao; // IUsuarioDao es una interfaz que extiende de CrudRepository, que es una interfaz de Spring Data JPA que proporciona métodos CRUD heredados de CrudRepository. IUsuarioDao proporciona un método personalizado para buscar un usuario por nombre de usuario.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioDao.findByNombreUsuario(username); // findByNombreUsuario es un método personalizado de IUsuarioDao que busca un usuario por nombre de usuario.

        if (usuario == null) {
            log.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '" + username + "' en el sistema!");
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                // getRoles() es un método de la clase Usuario que devuelve una lista de roles.
                //GrantedAuthority es una interfaz que representa una autoridad concedida a un Authenticationobjeto. Las autoridades se utilizan para representar tanto los roles como los permisos.
                .stream()// stream() es un método de la interfaz Collection que devuelve un flujo secuencial de elementos de esta colección.
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> log.info("Role: " + authority.getAuthority())) // peek() es un método de la interfaz Stream que devuelve un flujo que consiste en los elementos de este flujo, además de realizar la acción especificada en el parámetro.
                .collect(Collectors.toList()); // devolver un collectors.toList() collect() es un método de la interfaz Stream que devuelve un Collector que describe cómo se debe realizar la reducción de secuencias, por ejemplo, la colección en la que se deben almacenar los datos de entrada.
        return new User(usuario.getNombreUsuario(), usuario.getPassword(), usuario.getHabilitado(), true, true, true, authorities);
    }
}


