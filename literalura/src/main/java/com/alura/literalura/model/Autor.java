package com.alura.literalura.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="episodios")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaDeceso;
}
