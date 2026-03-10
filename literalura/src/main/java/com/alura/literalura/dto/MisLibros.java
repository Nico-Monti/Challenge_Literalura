package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MisLibros(
        @JsonAlias("results") List<DatosLibro> libros) {
    @Override
    public String toString() {
        return "---------- Libros ----------" +
                libros;
    }
}
