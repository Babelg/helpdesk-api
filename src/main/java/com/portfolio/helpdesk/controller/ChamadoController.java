package com.portfolio.helpdesk.controller;

import com.portfolio.helpdesk.dto.ChamadoDTO;
import com.portfolio.helpdesk.model.Prioridade;
import com.portfolio.helpdesk.model.StatusChamado;
import com.portfolio.helpdesk.service.ChamadoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
@Tag(name = "Chamados", description = "Abertura, atribuição e acompanhamento de chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO.Response> criar(@Valid @RequestBody ChamadoDTO.CreateRequest dto) {
        ChamadoDTO.Response response = chamadoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO.Response>> listar(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) Long atendenteId,
            @RequestParam(required = false) Long solicitanteId) {
        return ResponseEntity.ok(chamadoService.listarComFiltros(status, prioridade, atendenteId, solicitanteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/atribuir")
    public ResponseEntity<ChamadoDTO.Response> atribuir(@PathVariable Long id,
                                                          @Valid @RequestBody ChamadoDTO.AtribuicaoRequest dto) {
        return ResponseEntity.ok(chamadoService.atribuir(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ChamadoDTO.Response> alterarStatus(@PathVariable Long id,
                                                               @Valid @RequestBody ChamadoDTO.StatusUpdateRequest dto) {
        return ResponseEntity.ok(chamadoService.alterarStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        chamadoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
