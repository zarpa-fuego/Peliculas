package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class Actor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActor;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @OneToMany(mappedBy = "actor")
    private Set<ActorPelicula> actorActorPeliculas;

    public Integer getIdActor() {
        return idActor;
    }

    public void setIdActor(final Integer idActor) {
        this.idActor = idActor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public Set<ActorPelicula> getActorActorPeliculas() {
        return actorActorPeliculas;
    }

    public void setActorActorPeliculas(final Set<ActorPelicula> actorActorPeliculas) {
        this.actorActorPeliculas = actorActorPeliculas;
    }

}
