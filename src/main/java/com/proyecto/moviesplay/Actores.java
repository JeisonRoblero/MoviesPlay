package com.proyecto.moviesplay;

public class Actores {

    private String id;
    private String idPelicula;
    private String nombreActor;
    private String fotoActor;
    private Actores nodoIzq;
    private Actores nodoDer;

    public Actores(String id, String idPelicula, String nombreActor, String fotoActor) {
        this.id = id;
        this.idPelicula = idPelicula;
        this.nombreActor = nombreActor;
        this.fotoActor = fotoActor;
        this.nodoIzq = null;
        this.nodoDer = null;
    }

    public String getId() {
        return id;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreActor() {
        return nombreActor;
    }

    public void setNombreActor(String nombreActor) {
        this.nombreActor = nombreActor;
    }

    public String getFotoActor() {
        return fotoActor;
    }

    public void setFotoActor(String fotoActor) {
        this.fotoActor = fotoActor;
    }

    public Actores getNodoIzq() {
        return nodoIzq;
    }

    public void setNodoIzq(Actores nodoIzq) {
        this.nodoIzq = nodoIzq;
    }

    public Actores getNodoDer() {
        return nodoDer;
    }

    public void setNodoDer(Actores nodoDer) {
        this.nodoDer = nodoDer;
    }

    @Override
    public String toString() {
        return "Actores{" +
                "nombreActor='" + nombreActor + '\'' +
                '}';
    }
}