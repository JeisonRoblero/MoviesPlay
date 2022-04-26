package com.proyecto.moviesplay;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

import static com.proyecto.moviesplay.ABBServlet.agregarApiAABB;

@WebServlet(name = "CircularServlet", value = "/CircularServlet")
public class CircularServlet extends HttpServlet {

    public static int cantidad = 0;
    public static ListaCircularSimple lc =  new ListaCircularSimple();
    public static boolean flagAgregado = false;

    public void agregarApiACircular(){

        if (flagAgregado==false){
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpRequest requestCartelera = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://api.themoviedb.org/3/movie/now_playing?api_key=35218c6b9ac7affa768d796a7aacb4fb"))
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(requestCartelera, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                Gson gson = new Gson();
                PeliculasAPI peliculas = gson.fromJson(json, PeliculasAPI.class);

                for (int i=0; i<peliculas.getResultsP().size(); i++){
                    double reseniaDouble =  Double.parseDouble(peliculas.getResultsP().get(i).getVote_average())/2;
                    DecimalFormat df = new DecimalFormat("###.#");
                    String resenia = "" + df.format(reseniaDouble);
                    String imagen = "https://image.tmdb.org/t/p/w500" + peliculas.getResultsP().get(i).getPoster_path();
                    String trailer = null;
                    String video = null;

                    Actores raizArbol = agregarApiAABB(peliculas.getResultsP().get(i).getId());

                    lc.agregarCircular(raizArbol, peliculas.getResultsP().get(i).getId(), peliculas.getResultsP().get(i).getTitle(), peliculas.getResultsP().get(i).getOriginal_title(), resenia, peliculas.getResultsP().get(i).getOverview(), imagen, trailer, video);
                }

                flagAgregado = true;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        if(request.getParameter("op").equals("add")){

            String idCircular = request.getParameter("idcircular");
            String nombrePelicula = request.getParameter("nombre_pelicula");
            String nombreOriginal = request.getParameter("nombre_original");
            String resenia = request.getParameter("resenia");
            String sinopsis = request.getParameter("sinopsis");
            String imagen = request.getParameter("imagen");
            String trailer = request.getParameter("video");
            String video = request.getParameter("pelicula");

            lc.agregarCircular(null, idCircular, nombrePelicula, nombreOriginal, resenia, sinopsis, imagen, trailer, video);

            cantidad ++;

            response.sendRedirect("./");

        }else if(request.getParameter("op").equals("edit")){

            String idCircular = request.getParameter("idcircular");
            String nombrePeliculaN = request.getParameter("nombre_pelicula_n");
            String nombreOriginalN = request.getParameter("nombre_original_n");
            String reseniaN = request.getParameter("resenia_n");
            String sinopsisN = request.getParameter("sinopsis_n");
            String imagenN = request.getParameter("imagen_n");
            String trailerN = request.getParameter("video_n");
            String videoN = request.getParameter("pelicula_n");

            int cont3 = 0;

            lc.setActual(lc.getPrimero());

            while (cont3 < lc.getCantidad() && lc.getActual()!=null){

                if(idCircular.equals(lc.getActual().getId())){
                    lc.getActual().setNombrePelicula(nombrePeliculaN);
                    lc.getActual().setNombreOriginal(nombreOriginalN);
                    lc.getActual().setResenia(reseniaN);
                    lc.getActual().setSinopsis(sinopsisN);
                    lc.getActual().setImagen(imagenN);
                    lc.getActual().setTrailer(trailerN);
                    lc.getActual().setVideo(videoN);

                    if(request.getParameter("from")!=null && request.getParameter("from").equals("desc")){
                        response.sendRedirect("descripcion.jsp?idc="+lc.getActual().getId());
                    }else {
                        response.sendRedirect("./");
                    }
                }

                //3. Identificar su siguiente nodo
                lc.setActual(lc.getActual().getSiguiente());
                cont3++;
            }

        }

    }


    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idCircular = request.getParameter("idcircular");

        lc.eliminarCirular(idCircular);

    }

    public void destroy() {
    }
}