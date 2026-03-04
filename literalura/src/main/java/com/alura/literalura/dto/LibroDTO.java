package com.alura.literalura.dto;

import java.util.List;

public record LibroDTO(
        String titulo,
        List<AutorDTO> autorEs,
        int descargas
) {
}
