package com.example.alertas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.alertas.models.Alerta;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    // Cambiado de existsByNombre a NombrePaciente para coincidir con la entidad Alerta
    boolean existsByNombrePaciente(String nombrePaciente);
}
