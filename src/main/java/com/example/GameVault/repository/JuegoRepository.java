package com.example.GameVault.repository;

import com.example.GameVault.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JuegoRepository extends JpaRepository<Juego,Long> {
    //SELECT * FROM juegos WHERE titulo =:titulo
    List<Juego> findByTituloContainingIgnoreCase(String titutlo);

    //List<Juego> findByGenero(String genero)

    @Query("SELECT j FROM Juego j WHERE LOWER(j.descripcion) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Juego> buscarPorDescripcion(@Param("filtro") String filtro);

}
