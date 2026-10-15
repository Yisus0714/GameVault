package com.example.GameVault.repository;

import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT j FROM Juego j WHERE LOWER(j.descripcion) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<Juego> buscarPorDescripcion(@Param("palabra") String palabra);

    List<Juego> findByUsuario(Usuario usuario);

    List<Juego> findByTituloContainingIgnoreCaseOrCategoriaContainingIgnoreCase(String titulo, String categoria);
}

