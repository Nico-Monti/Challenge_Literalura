package com.alura.literalura.model;

public enum Idioma {
    ACCION("en", "Inglés"),
    ROMANCE("es", "Español"),
    COMEDIA("fr", "Francés"),
    DRAMA("pt", "Portugués");
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
    public static Idioma fromKb(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna idioma encontrada: " + text);
    }
}

