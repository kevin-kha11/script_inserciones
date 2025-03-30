package org.kevin.interfaces.catalogo.model;

public class Iphone extends Electronico {

    private String color;
    private String moddelo;

    public Iphone(String color, String moddelo, String fabricante, int precio) {
        super(precio, fabricante);
        this.color = color;
        this.moddelo = moddelo;
    }

    @Override
    public double getPrecioVenta() {
        return getPrecio()*0.5+ getPrecio();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModdelo() {
        return moddelo;
    }

    public void setModdelo(String moddelo) {
        this.moddelo = moddelo;
    }
}
