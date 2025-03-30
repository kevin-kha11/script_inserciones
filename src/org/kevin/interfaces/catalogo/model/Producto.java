package org.kevin.interfaces.catalogo.model;

abstract class Producto implements IdProducto{

    private int precio;

    public Producto(int precio) {
        this.precio = precio;
    }

    @Override
    public int getPrecio() {
        return precio;
    }

    public void addProducto(){
    }
}
