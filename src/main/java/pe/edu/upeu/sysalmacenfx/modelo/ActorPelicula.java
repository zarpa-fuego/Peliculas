package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class ActorPelicula {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActorPelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false)
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelecula_id", nullable = false)
    private Pelicula pelecula;

    public Integer getIdActorPelicula() {
        return idActorPelicula;
    }

    public void setIdActorPelicula(final Integer idActorPelicula) {
        this.idActorPelicula = idActorPelicula;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(final Actor actor) {
        this.actor = actor;
    }

    public Pelicula getPelecula() {
        return pelecula;
    }

    public void setPelecula(final Pelicula pelecula) {
        this.pelecula = pelecula;
    }

}
