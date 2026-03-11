package com.alura.literalura.model;

public enum Idioma {
    INGLES("en", "Inglés"),
    ESPANOL("es", "Español"),
    FRANCES("fr", "Francés"),
    PORTUGES("pt", "Portugués");
    private String idiomaOmdb;
    private String idiomaEspanol;
    Idioma(String idiomaOmdb, String idiomaEspanol){
        this.idiomaOmdb = idiomaOmdb;
        this.idiomaEspanol = idiomaEspanol;

    }
    public static Idioma fromString(String text) {
        for (Idioma idioma  : Idioma.values()) {
            if (idioma.idiomaOmdb.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna idioma encontrada: " + text);
    }
}

