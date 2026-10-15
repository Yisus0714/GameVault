package com.example.GameVault.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class JuegoService {


    @Autowired
    private JuegoRepository juegoRepository;

    @Autowired(required = false)
    private Cloudinary cloudinary;



    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";


    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }







    public List<Juego> buscarPorTitulo(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return listarTodos();
        }
        return juegoRepository.findByTituloContainingIgnoreCase(palabra);
    }



    private String guardarImagenLocal(MultipartFile portada) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            String nombreArchivo = UUID.randomUUID().toString() + "_" + portada.getOriginalFilename();
            Path filePath = uploadPath.resolve(nombreArchivo);
            Files.copy(portada.getInputStream(), filePath);


            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return "default.png";
        }
    }

    private String guardarImagenCloudinary(MultipartFile portada) {
        try {
            Map uploadResult = cloudinary.uploader().upload(portada.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            System.err.println("Error al subir a Cloudinary. Revisa tus credenciales.");
            e.printStackTrace();
            return "default.png";
        }
    }



    public void guardarJuego(Juego juego, MultipartFile portada) {
        if (portada != null && !portada.isEmpty()) {
            juego.setPortadaUrl(guardarImagenCloudinary(portada));
        } else if (juego.getPortadaUrl() == null) {
            juego.setPortadaUrl("default.png");
        }
        juegoRepository.save(juego);
    }

    public Juego obtenerJuegoPorId(Long id) {
        return juegoRepository.findById(id).orElse(null);
    }

    public void eliminarJuego(Long id) {
        juegoRepository.deleteById(id);
    }

    public List<Juego> listarPorUsuario(Usuario usuario) {
        return juegoRepository.findByUsuario(usuario);
    }

    public List<Juego> buscar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTodos();
        }
        return juegoRepository
                .findByTituloContainingIgnoreCaseOrCategoriaContainingIgnoreCase(texto.trim(), texto.trim());
    }



}







