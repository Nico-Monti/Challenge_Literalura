package com.alura.literalura.principal;

import com.alura.literalura.dto.DatosLibro;
import com.alura.literalura.dto.MisLibros;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.awt.print.Book;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/";
    private LibroRepository libroRepository;
    private List<Book> books;
    private Optional<Book> searchBook;
    private ConvierteDatos conversor = new ConvierteDatos();


    public Principal(LibroRepository libroRepository){
        this.libroRepository = libroRepository;
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
//                    listarLibrosRegistrados();
                    break;
                case 3:
//                    listarAutoresRegistrados();
                    break;
                case 4:
//                    listarAutoresVivosAño();
                    break;
                case 5:
//                    listarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    //1)busca libros en el repositorio y los intenta agregar si no existen
    private void buscarLibrosPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();

        Optional<Libro> libroBuscado = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);
        if(libroBuscado.isPresent()){
            System.out.println("La serie buscada es: " + libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado localmente. Buscando en la API...");

            try {
                // Reemplaza los espacios por '+' para la URL
                var json = consumoApi.obtenerDatos(URL_BASE + "books/?search=" + nombreLibro.replace(" ", "%20"));
                var datosBusqueda = conversor.obtenerDatos(json, MisLibros.class);

                // Buscamos la primera coincidencia en el resultado de la API
                Optional<DatosLibro> libroEncontradoAPI = datosBusqueda.libros().stream()
                        .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                        .findFirst();

                if (libroEncontradoAPI.isPresent()) {
                    // 3. Si existe en la API, lo convierto a Entidad y lo guardo
                    Libro libroNuevo = new Libro(libroEncontradoAPI.get());
                    libroRepository.save(libroNuevo);

                    System.out.println("--- Libro encontrado en la API y guardado ---");
                    System.out.println(libroNuevo);
                } else {
                    // 4. Si no está ni en BD ni en API, recién mostramos el error
                    System.out.println("El libro '" + nombreLibro + "' no fue encontrado en ninguna fuente.");
                }
            } catch (Exception e) {
                System.out.println("Error al conectar con la API: " + e.getMessage());
            }
        }
    }
    }

////    2)
//    private void listarLibrosRegistrados() {
//        //        series = datosSeries.stream()
//        //                .map(d -> new Serie(d))
//        //                .collect(Collectors.toList());
//
//        series = libroRepository.findAll();
//        series.stream().
//                sorted(Comparator.comparing(Serie::getGenero))
//                .forEach(System.out::println);
//
//    }
//
//
////    3)
//    private void listarAutoresRegistrados() {
//
//    }
//
//
//
////    4)
//    private void listarAutoresVivosAño(){
//
//    }
//
////    5)
//    private void listarLibrosIdioma() {
//        System.out.println("Deseja buscar séries de que categoria/gênero? ");
//        var idioma = teclado.nextLine();
//        Idioma categoria = Idioma.fromKb(idioma);
//        List<Libro> seriesPorCategoria = libroRepository.findByIdioma(categoria);
//        System.out.println("Séries da categoria " + nombreGenero);
//        seriesPorCategoria.forEach(System.out::println);
//    }







