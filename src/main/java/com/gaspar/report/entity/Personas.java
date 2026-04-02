package com.gaspar.report.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nombre;
    String direccion;
    String telefono;
}
