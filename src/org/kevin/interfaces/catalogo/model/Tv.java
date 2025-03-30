package org.kevin.interfaces.catalogo.model;

public class Tv extends Electronico{

    private int pulgada;

    public Tv(int precio, int pulgada ,String fabricante) {
        super(precio, fabricante);
        this.pulgada = pulgada;
    }

    public int getPulgada() {
        return pulgada;
    }

    public void setPulgada(int pulgada) {
        this.pulgada = pulgada;
    }


    @Override
    public double getPrecioVenta() {
        return getPrecio()*0.9+ getPrecio();
    }
}
