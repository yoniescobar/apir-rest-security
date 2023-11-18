package com.company.intecap.apibooks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    private static final long serialVersionUID = 1L; // serializable id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 200) //unique: para que no se repita el nombre de usuario
    private String nombreUsuario;

    @Column(length = 200)
    private String password;

    private Boolean habilitado;//para saber si el usuario esta habilitado o no

    //Relacion de muchos a muchos
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})})
    //uniqueConstraints: se utiliza para que no se repitan los roles en un usuario
    private List<Role> roles;
}
