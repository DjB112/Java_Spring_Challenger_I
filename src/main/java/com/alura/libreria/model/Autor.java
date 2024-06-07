package com.alura.libreria.model;



import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Long nacimiento;
    private Long fallecimiento;
    @ManyToOne
    private Libro libro;

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Autor(){}

    public Autor(AutorLibro d){
        this.nombre= d.nombre();
        this.nacimiento = d.nacimiento();
        this.fallecimiento= d.fallecimiento();
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' +
                ", nacimiento=" + nacimiento +
                ", fallecimiento= " + fallecimiento;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Long nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Long getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Long fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

}
