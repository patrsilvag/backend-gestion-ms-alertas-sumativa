package com.example.alertas.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import com.example.alertas.exceptions.ResourceNotFoundException;
import com.example.alertas.models.Alerta;
import com.example.alertas.repositories.AlertaRepository;
import com.example.alertas.dto.AlertaRequest;
import com.example.alertas.dto.AlertaResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
public class AlertaService {

    private final AlertaRepository alertaRepository;

    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<AlertaResponse> listarTodas() {
        return alertaRepository.findAll().stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AlertaResponse buscarPorId(@NonNull Long id) {
        return alertaRepository.findById(id).map(this::convertToResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alerta clínica no encontrada con el ID: " + id));
    }

    @Transactional
    public AlertaResponse guardar(@Valid AlertaRequest request) {
        // Validación: Puedes ajustar según tu lógica de negocio (ej: no duplicar alerta para el
        // mismo paciente)
        Alerta alerta = new Alerta();
        actualizarEntidad(alerta, request);

        Alerta guardada = alertaRepository.save(alerta);
        return convertToResponse(guardada);
    }

    @Transactional
    public AlertaResponse actualizar(@NonNull Long id, @Valid AlertaRequest detalles) {
        return alertaRepository.findById(id).map(alerta -> {
            actualizarEntidad(alerta, detalles);
            Alerta actualizada = alertaRepository.save(alerta);
            return convertToResponse(actualizada);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Alerta no encontrada con el ID: " + id));
    }

    @Transactional
    public void eliminar(@NonNull Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("ID " + id + " no existe");
        }
        alertaRepository.deleteById(id);
    }

    private AlertaResponse convertToResponse(@NonNull Alerta entity) {
        return AlertaResponse.builder().id(entity.getId())
                .nombrePaciente(entity.getNombrePaciente()).habitacion(entity.getHabitacion())
                .colorAlerta(entity.getColorAlerta()).signosVitales(entity.getSignosVitales())
                .fechaHora(entity.getFechaHora()).build();
    }

    @Transactional
    public AlertaResponse actualizarEstado(@NonNull Long id, String nuevoEstado) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada"));

        alerta.setEstado(nuevoEstado);
        return convertToResponse(alertaRepository.save(alerta));
    }

    private void actualizarEntidad(@NonNull Alerta entity, @NonNull AlertaRequest request) {
        entity.setNombrePaciente(request.getNombrePaciente());
        entity.setHabitacion(request.getHabitacion());
        entity.setColorAlerta(request.getColorAlerta());
        entity.setSignosVitales(request.getSignosVitales());
    }
}
