package com.proyecto.moviesplay;

import java.util.List;

public class ABB {

    //public static Actores actores[500];
    public static Actores[] actores = new Actores[500];
    public static List<Actores> actores2;
    int cont = 0;
    int contador = 0;

    private Actores raiz;
    private Actores nodoActual;
    private Actores subRaiz;

    public ABB() {
        this.raiz = null;
        this.nodoActual = null;
        this.subRaiz = null;
    }

    //Metodo para insertar un nodo al arbol
    public void insertar(String id, String idPelicula, String nombreActor, String fotoActor){

        Actores actores = new Actores(id, idPelicula, nombreActor, fotoActor);

        if(raiz == null){
            raiz = actores;
        }else{

            nodoActual=raiz;

            while (true){
                subRaiz=nodoActual;

                if (actores.getNombreActor().compareTo(nodoActual.getNombreActor()) < 0){
                    nodoActual = nodoActual.getNodoIzq();
                    if(nodoActual==null){
                        subRaiz.setNodoIzq(actores);
                        return;
                    }
                }else {
                    nodoActual = nodoActual.getNodoDer();
                    if(nodoActual==null){
                        subRaiz.setNodoDer(actores);
                        return;
                    }
                }
            }
        }

    }

    //Metodo para eliminar un nodo del arbol
    public Actores eliminarNodoArbol(String nombreActor, Actores raizArbol){

        raiz = raizArbol;
        nodoActual = raiz;
        subRaiz =raiz;
        boolean esHijoIzq = true;

        while (!nodoActual.getNombreActor().equals(nombreActor)){

            subRaiz=nodoActual;

            if(nombreActor.compareTo(nodoActual.getNombreActor())<0){
                esHijoIzq=true;
                nodoActual=nodoActual.getNodoIzq();
            }else {
                esHijoIzq=false;
                nodoActual=nodoActual.getNodoDer();
            }

            if (nodoActual==null){
                //return;
            }

        }

        if(nodoActual.getNodoIzq()==null && nodoActual.getNodoDer()==null){
            if(nodoActual==raiz){
                raiz=null;
            }else if(esHijoIzq){
                subRaiz.setNodoIzq(null);
            }else{
                subRaiz.setNodoDer(null);
            }
        }else if(nodoActual.getNodoDer()==null){
            if(nodoActual==raiz){
                raiz=nodoActual.getNodoIzq();
            }else if(esHijoIzq){
                subRaiz.setNodoIzq(nodoActual.getNodoIzq());
            }else{
                subRaiz.setNodoDer(nodoActual.getNodoIzq());
            }


        }else if(nodoActual.getNodoIzq()==null){
            if(nodoActual==raiz){
                raiz=nodoActual.getNodoDer();
            }else if(esHijoIzq){
                subRaiz.setNodoIzq(nodoActual.getNodoDer());
            }else{
                subRaiz.setNodoDer(nodoActual.getNodoDer());
            }

        }else{
            Actores reemplazo = obtenerNodoReemplazo(nodoActual);
            if(nodoActual==raiz){
                raiz=reemplazo;
            }else if(esHijoIzq){
                subRaiz.setNodoIzq(reemplazo);
            }else{
                subRaiz.setNodoDer(reemplazo);
            }
            reemplazo.setNodoDer(nodoActual.getNodoDer());

        }

        return raiz;

    }

    //Metodo que devolvera el nodo que reemplazara al nodo a eliminar
    public Actores obtenerNodoReemplazo(Actores nodoReemp){
        Actores reemplazarPadre = nodoReemp;
        Actores reemplazo = nodoReemp;
        Actores nodoActual = nodoReemp.getNodoIzq();

        while (nodoActual!=null){
            reemplazarPadre = reemplazo; //Oscar
            reemplazo = nodoActual; //Ovaldo
            nodoActual = nodoActual.getNodoDer();
        }
        if(reemplazo!=nodoReemp.getNodoIzq()){
            reemplazarPadre.setNodoDer(reemplazo.getNodoIzq());
            reemplazo.setNodoIzq(nodoReemp.getNodoIzq());
            reemplazo.setNodoDer(nodoReemp.getNodoDer());
        }
        return reemplazo;

    }

    //Metodo para saber si el arbol esta vacio
    public boolean arbolVacio(){
        return raiz==null;
    }

    public Actores getRaiz() {
        return raiz;
    }

    public void setRaiz(Actores raiz) {
        this.raiz = raiz;
    }

    @Override
    public String toString() {
        return "ABB{" +
                ", raiz=" + raiz +
                ", nodoActual=" + nodoActual +
                ", subRaiz=" + subRaiz +
                '}';
    }
}
