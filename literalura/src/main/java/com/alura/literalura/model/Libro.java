package com.alura.literalura.model;

import com.alura.literalura.dto.DatosLibro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name ="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Long descargas;
    private Idioma idiomas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {

        this.titulo = datosLibro.titulo();

        // Inicializamos la lista de autores

        datosLibro.autorEs().forEach(d -> {
            Autor autorEntidad = new Autor(d); // Asume que tienes un constructor en Autor que recibe DatosAutor
            autorEntidad.setLibro(this); // Importante para mantener la relación bidireccional si existe
            this.autor =autorEntidad;
        });
        this.descargas = datosLibro.descargas();
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        return "---------------- LIBRO ----------------" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNombre() : "Desconocido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Descargas: " + descargas + "\n" +
                "---------------------------------------";
    }
}
