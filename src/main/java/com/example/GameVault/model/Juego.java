package com.example.GameVault.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Generar gets y sets
@NoArgsConstructor  //Constructor sin argumentos
@AllArgsConstructor  //Constructor con todito

//Entidad
@Entity
@Table(name = "juegos")
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "portada_url")
    private String portadaUrl;

    @Column(nullable = false)
    private String categoria;

    @Column
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}