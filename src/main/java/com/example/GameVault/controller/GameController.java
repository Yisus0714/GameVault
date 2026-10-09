package com.example.GameVault.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/fragment-demo")
    public String fragments(){
        return  "fragments-demo";
    }
    @GetMapping("/juegos")
    public String juegos(){
        return  "juegos";
    }
    @GetMapping("/juegos/nuevo")
    public String formulario(){
        return  "formulario";
    }



}
