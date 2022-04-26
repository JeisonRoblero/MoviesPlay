package com.proyecto.moviesplay;

import com.google.gson.Gson;

import javax.servlet.ServletContext;
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

@WebServlet(name = "ABBServlet", value = "/ABBServlet")
public class ABBServlet extends HttpServlet {

    public static ABB arbolNodo = new ABB();
    public static Actores root;
    public static boolean flagAgregadoA = false;

    public static Actores agregarApiAABB(String idPelicula){

            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpRequest requestActores = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.themoviedb.org/3/movie/"+ idPelicula +"/credits?api_key=35218c6b9ac7affa768d796a7aacb4fb"))
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(requestActores, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                Gson gson = new Gson();
                ActoresAPI actores = gson.fromJson(json, ActoresAPI.class);

                arbolNodo.setRaiz(null);

                for (int i=0; i<actores.getCast().size(); i++){
                    String imagen = "";
                    if (actores.getCast().get(i).getProfile_path()!=null){
                        imagen = "https://image.tmdb.org/t/p/w500" + actores.getCast().get(i).getProfile_path();
                    } else {
                        imagen = "https://images.vexels.com/media/users/3/147102/isolated/preview/082213cb0f9eabb7e6715f59ef7d322a-icono-de-perfil-de-instagram.png";
                    }

                    arbolNodo.insertar(actores.getCast().get(i).getId(), idPelicula, actores.getCast().get(i).getName(), imagen);
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            return arbolNodo.getRaiz();

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombreActor = request.getParameter("nombreActor");

        ServletContext application = getServletContext();
        Actores raizArbol = (Actores) application.getAttribute("raizArbol");
        Pelicula nodoLista = (Pelicula) application.getAttribute("nodoLista");

        Actores nuevaRaiz = arbolNodo.eliminarNodoArbol(nombreActor,raizArbol);
        nodoLista.setRaiz(nuevaRaiz);
    }

}
