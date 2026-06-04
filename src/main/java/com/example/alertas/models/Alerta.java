package com.example.alertas.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTAS_VITALES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long id;

    @Column(name = "NOMBRE_PACIENTE", nullable = false)
    private String nombrePaciente;

    @Column(name = "HABITACION", nullable = false)
    private String habitacion;

    @Column(name = "COLOR_ALERTA", nullable = false)
    private String colorAlerta; // Guardaremos 'ROJO', 'AMARILLO', 'VERDE'

    @Column(name = "SIGNOS_VITALES", length = 1000)
    private String signosVitales;

    @Column(name = "FECHA_HORA_REGISTRO", nullable = false)
    private LocalDateTime fechaHora;

    @PrePersist
    protected void onCreate() {
        this.fechaHora = LocalDateTime.now();
    }
}
