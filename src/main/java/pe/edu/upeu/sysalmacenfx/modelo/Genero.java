package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class Genero {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGenero;

    @Column(nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "genero")
    private Set<Pelicula> generoPeliculas;

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(final Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Set<Pelicula> getGeneroPeliculas() {
        return generoPeliculas;
    }

    public void setGeneroPeliculas(final Set<Pelicula> generoPeliculas) {
        this.generoPeliculas = generoPeliculas;
    }

}
