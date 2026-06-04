package com.example.alertas.controllers;

import com.example.alertas.dto.AlertaRequest;
import com.example.alertas.dto.AlertaResponse;
import com.example.alertas.services.AlertaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = {"http://mi-app-docker", "http://localhost:4200", "http://localhost"})
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<AlertaResponse>> listar() {
        System.out.println("ENTRO AL CONTROLADOR DE ALERTAS");
        return ResponseEntity.ok(alertaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlertaResponse> crear(@Valid @RequestBody AlertaRequest request) {
        return new ResponseEntity<>(alertaService.guardar(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody AlertaRequest detalles) {
        return ResponseEntity.ok(alertaService.actualizar(id, detalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<AlertaResponse> cambiarEstado(@PathVariable Long id,
            @RequestBody String nuevoEstado) {
        return ResponseEntity.ok(alertaService.actualizarEstado(id, nuevoEstado));
    }
}
