package com.example.GameVault.controller;

import com.example.GameVault.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam("nombre") String nombre,
                            @RequestParam("email") String email,
                            @RequestParam("contrasena") String contrasena,
                            Model model) {
        if (usuarioService.existePorEmail(email)) {
            model.addAttribute("error", "Ya existe una cuenta con ese correo.");
            return "registro";
        }
        usuarioService.registrar(nombre, email, contrasena);
        return "redirect:/login?registrado";
    }
}