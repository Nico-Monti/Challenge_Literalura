package com.alura.literalura.principal;

import com.alura.literalura.dto.DatosLibro;
import com.alura.literalura.dto.MisLibros;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private Optional<Autor> busquedaLibro;
    private ConvierteDatos conversor = new ConvierteDatos();


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo.
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma.
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosAño();
                    break;
                case 5:
                    listarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLibrosPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();

        Optional<Libro> libroBuscado = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);
        if(libroBuscado.isPresent()){
            System.out.println("La serie buscada es: " + libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado localmente. Buscando en la API...");

            try {
                var json = consumoApi.obtenerDatos(URL_BASE + "books/?search=" + nombreLibro.replace(" ", "%20"));
                var datosBusqueda = conversor.obtenerDatos(json, MisLibros.class);

                Optional<DatosLibro> libroEncontradoAPI = datosBusqueda.libros().stream()
                        .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                        .findFirst();

                if (libroEncontradoAPI.isPresent()) {
                    Libro libroNuevo = new Libro(libroEncontradoAPI.get());
                    libroRepository.save(libroNuevo);

                    System.out.println("--- Libro encontrado en la API y guardado ---");
                    System.out.println(libroNuevo);
                } else {
                    System.out.println("El libro '" + nombreLibro + "' no fue encontrado en ninguna fuente.");
                }
            } catch (Exception e) {
                System.out.println("Error al conectar con la API: " + e.getMessage());
            }
        }
    }
    private void listarLibrosRegistrados() {
        var libros = libroRepository.findAll();
        libros.stream().
                sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }
    private void listarAutoresRegistrados(){
        var autores = autorRepository.findAll();
        autores.stream().
                sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosAño(){
        System.out.println("Año de busqueda:");
        var anio = teclado.nextLine();
        List<Autor> autoresVivos = autorRepository.autoresVivos(anio);
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos registrados en el año " + anio);
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarLibrosIdioma() {
        System.out.println("¿Qué idioma busca?");
        System.out.println("""
                en - Inglés
                es - Español
                fr - Francés
                pt - Portugués
                :""");
        var idioma = teclado.nextLine();
        Idioma idiomaBuscado = Idioma.fromString(idioma);
        List<Libro> librosIdioma = libroRepository.findByIdioma(idiomaBuscado);
        if (librosIdioma.isEmpty()) {
            System.out.println("No hay libro en el idioma: " + idiomaBuscado);
        }else{
            System.out.println("Libros en " + idiomaBuscado);
            librosIdioma.forEach(System.out::println);
        }
    }

}








