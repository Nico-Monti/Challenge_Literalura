package com.alura.literalura.principal;

import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;

import java.awt.print.Book;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/";
    private LibroRepository libroRepository;
    private List<Book> books;
    private Optional<Book> searchBook;


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

    //1)
    private void buscarLibrosPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreSerie = teclado.nextLine();
        serieBuscada = serieRepository.findByTituloContainsIgnoreCase(nombreSerie);
        if(serieBuscada.isPresent()){
            System.out.println("La serie buscada es: " + serieBuscada.get());
        } else {
            //consultar y agregarlo
            System.out.println("Serie no encontrada");
        }
    }

//    2)
    private void listarLibrosRegistrados() {
        //        series = datosSeries.stream()
        //                .map(d -> new Serie(d))
        //                .collect(Collectors.toList());

        series = serieRepository.findAll();
        series.stream().
                sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }


//    3)
    private void listarAutoresRegistrados() {

    }



//    4)
    private void listarAutoresVivosAño(){

    }

//    5)
    private void listarLibrosIdioma() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nombreGenero = teclado.nextLine();
        Categoria categoria = Categoria.fromEspanol(nombreGenero);
        List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoria);
        System.out.println("Séries da categoria " + nombreGenero);
        seriesPorCategoria.forEach(System.out::println);
    }






}
