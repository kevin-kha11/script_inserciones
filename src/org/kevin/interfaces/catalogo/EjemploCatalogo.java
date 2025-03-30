package org.kevin.interfaces.catalogo;

import org.kevin.interfaces.catalogo.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EjemploCatalogo {
    public static void main(String[] args) {

        IdProducto [] productos = new IdProducto[4];
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaP = format.parse("1998-02-21");
            format.format(fechaP);
            productos [2] = new Comic("SpiderMan", fechaP, "Marvel", "The amazing", "San quintin", 200);

            productos [3] =  new Libro(fechaP, "Alan poe", "Narraciones extraordinarias", "la chida",  200);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        productos [0] = new Tv(5000, 40, "Hisense");
        productos [1] = new Iphone("Blanco", "16 pro max", "Apple", 16000);

        StringBuilder sb = new StringBuilder();

        for (IdProducto p : productos) {
            System.out.println("Producto: " + p.getClass().getSimpleName());
            System.out.println("Precio: $" +p.getPrecio() + "      " +"Precio + IVA: $" + p.getPrecioVenta());

            if (p instanceof  ILibro){
                System.out.println("Titulo: " + ((ILibro) p).getTitulo());
                System.out.println("Autor: " + ((ILibro) p).getAutor());
                System.out.println("Fecha de publicacion: " + ((ILibro) p).getFechaPublicacion());
                System.out.println("Editorial: " + ((ILibro) p).getEditorial());
                if (p instanceof Comic){

                    System.out.println("Personaje: " + ((Comic) p).getPersonaje());

                }
            }
            if (p instanceof IElectronico) {
                System.out.println("Fabricante: " + ((IElectronico) p).getFabricante());
                if (p instanceof  Iphone){
                    System.out.println("Modelo: " + ((Iphone) p).getModdelo());
                    System.out.println("Color: " + ((Iphone) p).getColor());
                }
                if (p instanceof  Tv){
                    System.out.println("Pulgadas: " +   ((Tv) p).getPulgada());
                }
            }
            System.out.println("-----------------------------------");
        }
    }
}
