package Servidor_Videojuego.Servidor_Videojuego.services;

import Servidor_Videojuego.Servidor_Videojuego.model.Videojuego;

import java.util.List;

public interface IServicioVideojuego {
    public Videojuego getVideojuego();

    public Videojuego setVideojuego (Videojuego videojuego);

    public Videojuego updateVideojuego (Videojuego videojuego, int id);

    public boolean deleteVideojuego(int id);

    List<Videojuego> buscarVideojuegos(Integer id, String nombre, Double precio);
}
