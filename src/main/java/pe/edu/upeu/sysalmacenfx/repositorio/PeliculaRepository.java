package pe.edu.upeu.sysalmacenfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysalmacenfx.modelo.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
}
