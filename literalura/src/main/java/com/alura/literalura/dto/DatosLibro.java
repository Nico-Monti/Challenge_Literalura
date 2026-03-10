package com.alura.literalura.dto;

import com.alura.literalura.model.Idioma;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autorEs,
        @JsonAlias("download_count") Long descargas,
        @JsonAlias("language") List<Idioma> idiomas
) {
}
