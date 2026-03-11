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
        this.descargas = datosLibro.descargas();

        datosLibro.autorEs().forEach(d -> {
            Autor autorEntidad = new Autor(d);
            autorEntidad.setLibro(this);
            this.autor =autorEntidad;
        });

        if (datosLibro.idiomas() != null) {
            this.idioma = Idioma.fromString(datosLibro.idiomas().get(0));
        }
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
