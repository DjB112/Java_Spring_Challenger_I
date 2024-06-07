package com.alura.libreria.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;
    private Long descargas;
    private String lenguaje;

    public Libro(){}

    public Libro(DatosLibro datoslibro) {
        this.titulo = datoslibro.resultado().get(0).titulo();
        List<AutorLibro> valor = datoslibro.resultado().get(0).autor();
        this.autor = valor.stream()
                .map(t -> new Autor(new AutorLibro(t.nombre(), t.nacimiento(), t.fallecimiento())))
                .collect(Collectors.toList());
        setAutor(this.autor);
        this.lenguaje = datoslibro.resultado().get(0).lenguaje().toString();
        this.descargas = datoslibro.resultado().get(0).descargas();
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  " titulo= '" + titulo + '\'' +
                ", autor= " + autor +
                ", lenguaje= " + lenguaje +
                ", descargas= " + descargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        autor.forEach(e -> e.setLibro(this));
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }
}
