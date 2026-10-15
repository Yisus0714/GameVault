package com.example.GameVault.controller;

import com.example.GameVault.model.Usuario;
import com.example.GameVault.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual() {
        return usuarioService.obtenerUsuarioLogueado();
    }
}