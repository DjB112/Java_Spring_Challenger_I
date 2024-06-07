package com.alura.libreria.principal;

import com.alura.libreria.model.Autor;
import com.alura.libreria.model.DatosLibro;
import com.alura.libreria.model.Libro;
import com.alura.libreria.repository.LibroRepository;
import com.alura.libreria.service.ConsumoAPI;
import com.alura.libreria.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com//books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libro> libros;
    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ---------------------------------------
                    1 - Buscar Libro
                    2 - Mostrar Libros Registrados
                    3 - Mostrar Autores Registrados
                    4 - Mostrar Autores Vivos En Un Determinado Año
                    5 - Mostrar Libros Registrados Por Idioma                                  
                    0 - Salir
                    ---------------------------------------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                    mostrarPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del Libro que buscas");
        //Busca los datos generales de las series
        var nombreLibro = teclado.nextLine();

        Optional<Libro> estaLibro = repositorio.findByTituloContainsIgnoreCase(nombreLibro);
        if (estaLibro.isPresent()) {
            System.out.println("Libro ya registrado");
            return null;
        }else {
            var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
            if (json.length() == 52) {
                System.out.println("Su Libro no fue encontrado.");
                return null;
            }
            DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);
            return datos;
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos!=null){
            Libro libro = new Libro(datos);
            repositorio.save(libro);
            System.out.println("------------ LIBRO ----------------");
            System.out.println(" Titulo: " + libro.getTitulo());
            System.out.println(" Autor: " + libro.getAutor().get(0).getNombre());
            System.out.println(" Idioma: " + libro.getLenguaje());
            System.out.println(" Número de descargas: " + libro.getDescargas());
            System.out.println("-----------------------------------");
        }
    }

    private void mostrarLibrosRegistrados() {
        libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(s-> System.out.println("-------------- Libro ----------------" +"\n"+
                        "Titulo: " + s.getTitulo() +"\n"+
                        "Nombre de Autor: " + s.getAutor().get(0).getNombre() +"\n"+
                        "Lenguaje: " + s.getLenguaje() +"\n"+
                        "Descargas: " + s.getDescargas() +"\n"+
                        "-------------------------------------"+"\n"
                ));

    }

    private void mostrarAutoresRegistrados() {

        List<Autor> autor = repositorio.mostrarAutores();

        autor.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(s-> System.out.println("-------------- Autor ----------------" +"\n"+
                        "Autor: " + s.getNombre() + "\n"+
                        "Fecha de Nacimiento: " + s.getNacimiento() +"\n"+
                        "Fecha de Fallecimiento: " + s.getFallecimiento() +"\n"+
                        "Libros: " + s.getLibro() +"\n"+
                        "-------------------------------------" + "\n"
                ));
    }

    private void mostrarAutoresVivos() {
        System.out.println("Escriba el año para buscar autores vivos:");
        var autorVivo = teclado.nextLong();

        List<Autor> autor = repositorio.mostrarAutoresVivos(autorVivo);
        if(autor.size()!=0){
            autor.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(s-> System.out.println("----------- Autor / Vivo ------------" +"\n"+
                            "Autor: " + s.getNombre() + "\n"+
                            "Fecha de Nacimiento: " + s.getNacimiento() +"\n"+
                            "Fecha de Fallecimiento: " + s.getFallecimiento() +"\n"+
                            "Libros: " + s.getLibro() +"\n"+
                            "-------------------------------------"+"\n"
                    ));
        }else{
            System.out.println("Ningun autor encontrado vivo hasta el año "+ autorVivo);
        }
    }

    private void mostrarPorIdioma() {
        var menuIdioma = """
                -------------------------------------------
                 Ingrese el idioma para buscar los libros:
                  es - Español
                  en - Ingles
                  fr - Frances
                  pt - Portugues
                  0  - volver atras
                -------------------------------------------
                """;
        System.out.println(menuIdioma);
        var idIdioma = teclado.nextLine();
        switch (idIdioma){
            case ("es"):
                buscarLibroIdioma("[es]");
                break;
            case ("en"):
                buscarLibroIdioma("[en]");
                break;
            case ("fr"):
                buscarLibroIdioma("[fr]");
                break;
            case ("0"):
                System.out.println("Volviendo Atras ......");
                break;
            default:
                System.out.println("Error al seleccionar idioma.");
                break;
        }
    }

    private void buscarLibroIdioma(String idioma){
        libros = repositorio.mostrarLibrosPorIdioma(idioma);
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(s-> System.out.println("----------- Libro / Idioma -----------" +"\n"+
                        "Titulo: " + s.getTitulo() +"\n"+
                        "Nombre de Autor: " + s.getAutor().get(0).getNombre() +"\n"+
                        "Lenguaje: " + s.getLenguaje() +"\n"+
                        "Descargas: " + s.getDescargas() +"\n"+
                        "-------------------------------------"+"\n"
                ));
    }

}