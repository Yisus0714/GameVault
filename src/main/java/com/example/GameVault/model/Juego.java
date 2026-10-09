package com.example.GameVault.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Juego {
    private long id;
    private String titulo;
    private String descripcion;
    private String portadaUrl;
}
