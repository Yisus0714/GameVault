package com.example.GameVault.controller;

import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import com.example.GameVault.services.JuegoService;
import com.example.GameVault.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private UsuarioService usuarioService;

    private boolean esDuenio(Juego juego, Usuario usuario) {
        return juego != null
                && usuario != null
                && juego.getUsuario() != null
                && juego.getUsuario().getId().equals(usuario.getId());
    }

    @GetMapping("/fragments-demo")
    public String fragments() {
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(@RequestParam(value = "buscar", required = false) String buscar, Model model) {
        model.addAttribute("juegos", juegoService.buscar(buscar));
        model.addAttribute("busquedaActual", (buscar != null && !buscar.isBlank()) ? buscar : null);
        return "juegos";
    }

    @GetMapping("/mis-juegos")
    public String misJuegos(Model model) {
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();
        model.addAttribute("usuario", logueado);
        model.addAttribute("juegos", juegoService.listarPorUsuario(logueado));
        return "mis-juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String formulario(Model model) {
        model.addAttribute("juego", new Juego());
        return "formulario";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam(value = "precio", required = false) Double precio,
                               @RequestParam("categoria") String categoria,
                               @RequestParam(value = "portada", required = false) MultipartFile portada) {
        Juego juego = new Juego();
        juego.setTitulo(titulo);
        juego.setDescripcion(descripcion);
        juego.setPrecio(precio);
        juego.setCategoria(categoria);
        juego.setUsuario(usuarioService.obtenerUsuarioLogueado());

        juegoService.guardarJuego(juego, portada);
        return "redirect:/mis-juegos";
    }

    @GetMapping("/juegos/editar/{id}")
    public String editarJuego(@PathVariable Long id, Model model) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();

        if (esDuenio(juego, logueado)) {
            model.addAttribute("juego", juego);
            return "formulario";
        }
        return "redirect:/mis-juegos";
    }

    @PostMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id,
                                  @RequestParam("titulo") String titulo,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam(value = "precio", required = false) Double precio,
                                  @RequestParam("categoria") String categoria,
                                  @RequestParam(value = "portada", required = false) MultipartFile portada) {
        Juego juegoExistente = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();

        if (esDuenio(juegoExistente, logueado)) {
            juegoExistente.setTitulo(titulo);
            juegoExistente.setDescripcion(descripcion);
            juegoExistente.setPrecio(precio);
            juegoExistente.setCategoria(categoria);
            juegoService.guardarJuego(juegoExistente, portada);
        }
        return "redirect:/mis-juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();

        if (esDuenio(juego, logueado)) {
            juegoService.eliminarJuego(id);
        }
        return "redirect:/mis-juegos";
    }
}