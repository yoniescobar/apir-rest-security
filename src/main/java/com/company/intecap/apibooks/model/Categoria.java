package com.company.intecap.apibooks.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Categoria implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotBlank(message = "El nombre es requerido")
    //@Column(unique = true, length = 200) //Validaciones
    @NotNull(message = "El nombre es requerido")
    @NotBlank(message = "El nombre es requerido")
    @Column(unique = true, length = 200)
    private String nombre;
    @NotNull(message = "La descripcion es requerida")
    @NotBlank(message = "La descripcion es requerida")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                orphanRemoval = true,
                mappedBy = "categoria"
    )
    @JsonIgnore
    private Set<Libro> libros;
}
