package org.kevin.interfaces.catalogo.model;

import java.util.Date;

public interface ILibro {

    Date getFechaPublicacion();
    String getAutor();
    String getTitulo();
    String getEditorial();

}
