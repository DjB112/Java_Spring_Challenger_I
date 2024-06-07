package com.alura.libreria.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorLibro> autor,
        @JsonAlias("languages") List<String> lenguaje,
        @JsonAlias("download_count") Long descargas
) {
}
