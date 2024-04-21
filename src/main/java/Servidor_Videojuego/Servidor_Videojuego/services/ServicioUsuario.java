package Servidor_Videojuego.Servidor_Videojuego.services;

import Servidor_Videojuego.Servidor_Videojuego.model.Usuario;
import Servidor_Videojuego.Servidor_Videojuego.model.Videojuego;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class
ServicioUsuario implements IServicioUsuario {

    private List<Usuario> usuarios = new ArrayList<>();
    private IServicioVideojuego servicioVideojuego;


    public ServicioUsuario(IServicioVideojuego servicioVideojuego) {
        this.servicioVideojuego = servicioVideojuego;
    }


    @Override
    public Usuario addUsuario(Usuario usuario) {
        if (existeUsuarioConId(usuario.getId())) {
            throw new IllegalArgumentException("Un usuario con el mismo ID ya existe");
        }
        usuarios.add(usuario);
        return usuario;
    }


    @Override
    public Usuario updateUsuario(Usuario usuario, int id) {
        Optional<Usuario> existingUsuarioOpt = usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        if (existingUsuarioOpt.isPresent()) {
            Usuario existingUsuario = existingUsuarioOpt.get();

            // Verificar si el ID del usuario proporcionado es el mismo que el ID en la ruta
            if (usuario.getId() != id) {
                throw new IllegalArgumentException("El ID del usuario no coincide con el ID en la ruta");
            }

            // Conservar los videojuegos existentes si el nuevo usuario no los tiene definidos
            if (usuario.getVideojuegos() == null || usuario.getVideojuegos().isEmpty()) {
                usuario.setVideojuegos(existingUsuario.getVideojuegos());
            }

            // Actualizar el usuario en la lista
            int index = usuarios.indexOf(existingUsuario);
            usuarios.set(index, usuario); // Actualizar el objeto existente en la lista

            return usuario;
        }

        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }



    @Override
    public boolean deleteUsuario(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }

    @Override
    public Optional<Usuario> buscarUsuario(Integer id, String nombre) {
        return usuarios.stream()
                .filter(usuario -> (id == null || usuario.getId() == id) &&
                        (nombre == null || usuario.getNombre().equalsIgnoreCase(nombre)))
                .findFirst();
    }

    @Override
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> getUsuarios(Double estatura, Boolean esPremium) {
        List<Usuario> listaFiltrada = new ArrayList<>(usuarios);

        if (estatura != null) {
            listaFiltrada = listaFiltrada.stream()
                    .filter(us -> us.getEstatura() == estatura)
                    .collect(Collectors.toList());
        }
        if (esPremium != null) {
            listaFiltrada = listaFiltrada.stream()
                    .filter(us -> us.isEsPremium() == esPremium)
                    .collect(Collectors.toList());
        }

        return listaFiltrada;
    }

    @Override
    public boolean existeUsuarioConId(int id) {
        return usuarios.stream().anyMatch(usuario -> usuario.getId() == id);
    }
}


