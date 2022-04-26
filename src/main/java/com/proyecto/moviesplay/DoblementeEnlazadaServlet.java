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

@WebServlet(name = "DoblementeEnlazadaServlet", value = "/DoblementeEnlazadaServlet")
public class DoblementeEnlazadaServlet extends HttpServlet {

    public static ListaDoblementeEnlazada lde = new ListaDoblementeEnlazada();

    public static boolean flagAgregadoP = false;

    public void agregarApiALde(){

        if (flagAgregadoP==false) {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpRequest requestPopulares = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://api.themoviedb.org/3/movie/popular?api_key=35218c6b9ac7affa768d796a7aacb4fb"))
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(requestPopulares, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                Gson gson = new Gson();
                PeliculasAPI peliculasP = gson.fromJson(json, PeliculasAPI.class);

                for (int i = 0; i < peliculasP.getResultsP().size(); i++) {
                    double reseniaDouble = Double.parseDouble(peliculasP.getResultsP().get(i).getVote_average()) / 2;
                    DecimalFormat df = new DecimalFormat("###.#");
                    String resenia = "" + df.format(reseniaDouble);
                    String imagen = "https://image.tmdb.org/t/p/w500" + peliculasP.getResultsP().get(i).getPoster_path();
                    String trailer = null;
                    String video = null;

                    Actores raizArbol = agregarApiAABB(peliculasP.getResultsP().get(i).getId());

                    lde.agregarDoblementeEnlazada(raizArbol, peliculasP.getResultsP().get(i).getId(), peliculasP.getResultsP().get(i).getTitle(), peliculasP.getResultsP().get(i).getOriginal_title(), resenia, peliculasP.getResultsP().get(i).getOverview(), imagen, trailer, video);
                }

                flagAgregadoP = true;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        if(request.getParameter("op").equals("add")){

            String id = request.getParameter("idlde");
            String nombrePelicula = request.getParameter("nombre_pelicula");
            String nombreOriginal = request.getParameter("nombre_original");
            String resenia = request.getParameter("resenia");
            String sinopsis = request.getParameter("sinopsis");
            String imagen = request.getParameter("imagen");
            String trailer = request.getParameter("video");
            String video = request.getParameter("pelicula");

            lde.agregarDoblementeEnlazada(null, id, nombrePelicula, nombreOriginal, resenia, sinopsis, imagen, trailer, video);

            response.sendRedirect("./");

        }else if(request.getParameter("op").equals("edit")){

            String id = request.getParameter("idlde");
            String nombrePeliculaN = request.getParameter("nombre_pelicula_n");
            String nombreOriginalN = request.getParameter("nombre_original_n");
            String reseniaN = request.getParameter("resenia_n");
            String sinopsisN = request.getParameter("sinopsis_n");
            String imagenN = request.getParameter("imagen_n");
            String trailerN = request.getParameter("video_n");
            String videoN = request.getParameter("pelicula_n");

            lde.setActual(lde.getPrimero());

            while (lde.getActual()!=null){

                if(id.equals(lde.getActual().getId())){
                    lde.getActual().setNombrePelicula(nombrePeliculaN);
                    lde.getActual().setNombreOriginal(nombreOriginalN);
                    lde.getActual().setResenia(reseniaN);
                    lde.getActual().setSinopsis(sinopsisN);
                    lde.getActual().setImagen(imagenN);
                    lde.getActual().setTrailer(trailerN);
                    lde.getActual().setVideo(videoN);

                    if(request.getParameter("from")!=null && request.getParameter("from").equals("desc")){
                        response.sendRedirect("descripcion.jsp?idlde="+lde.getActual().getId());
                    }else {
                        response.sendRedirect("./");
                    }
                }

                //3. Identificar su siguiente nodo
                lde.setActual(lde.getActual().getSiguiente());
            }
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("idlde");

        lde.eliminarDoblementeEnlazado(id);

    }

    public void destroy() {
    }
}
