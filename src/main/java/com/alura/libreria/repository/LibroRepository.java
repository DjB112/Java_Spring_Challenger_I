package com.alura.libreria.repository;

import com.alura.libreria.model.Autor;
import com.alura.libreria.model.Libro;
import org.apache.logging.log4j.util.Chars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

       Optional<Libro> findByTituloContainsIgnoreCase(String tituloLibro);

       // List<Libro> findByAutorContainsIgnoreCase(String titulo);
       @Query("SELECT a FROM Autor a")
       List<Autor> mostrarAutores();

       @Query("SELECT a FROM Autor a WHERE a.fallecimiento > :autorVivo")
       List<Autor> mostrarAutoresVivos(Long autorVivo);

       @Query("SELECT l FROM Libro l WHERE l.lenguaje = :idioma")
       List<Libro> mostrarLibrosPorIdioma(String idioma);

}


