package com.portfolio.helpdesk.controller;

import com.portfolio.helpdesk.dto.ComentarioDTO;
import com.portfolio.helpdesk.service.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados/{chamadoId}/comentarios")
@Tag(name = "Comentários", description = "Histórico de interação dentro de um chamado")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO.Response> adicionar(@PathVariable Long chamadoId,
                                                              @Valid @RequestBody ComentarioDTO.Request dto) {
        ComentarioDTO.Response response = comentarioService.adicionar(chamadoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO.Response>> listar(@PathVariable Long chamadoId) {
        return ResponseEntity.ok(comentarioService.listarPorChamado(chamadoId));
    }
}
