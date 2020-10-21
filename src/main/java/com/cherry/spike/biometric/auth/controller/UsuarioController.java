package com.cherry.spike.biometric.auth.controller;

import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.repository.UsuarioRepositorio;
import com.cherry.spike.biometric.auth.service.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UsuarioController {
    @Autowired
    private UsuarioServico usuarioServico;

    @PostMapping("/usuario")
    public @ResponseBody Usuario salvar(@RequestBody Usuario usuario) {
        return usuarioServico.salvar(usuario);
    }
}
