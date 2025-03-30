package org.kevin.interfaces.catalogo.model;

public interface IdProducto {

    default int getPrecio(){
        return 0;
    };

    double getPrecioVenta();

}
