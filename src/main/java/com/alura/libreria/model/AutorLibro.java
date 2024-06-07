package com.alura.libreria.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.StringBufferInputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorLibro(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Long nacimiento,
        @JsonAlias("death_year") Long fallecimiento
        ) {
}
