package com.company.intecap.apibooks.model.dao;

import com.company.intecap.apibooks.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoriaDao extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
