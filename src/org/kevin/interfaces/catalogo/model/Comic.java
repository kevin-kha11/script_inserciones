package org.kevin.interfaces.catalogo.model;

import java.util.Date;

public class Comic extends Libro{

    private String personaje;


    public Comic(String personaje ,Date fechaPublicacion, String autor, String titulo, String editorial, int precio) {
        super(fechaPublicacion, autor, titulo, editorial, precio);
        this.personaje = personaje;
    }


    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    @Override
    public double getPrecioVenta() {
        return getPrecio()*0.7 + getPrecio();
    }
}