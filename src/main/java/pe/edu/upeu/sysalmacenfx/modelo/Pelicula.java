package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.Set;


@Entity
public class Pelicula {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPelicula;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal preUnitario;

    @Column(nullable = false)
    private Double porceUtil;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

    @OneToMany(mappedBy = "pelecula")
    private Set<VentaDetallada> peleculaVentaDetalladas;

    @OneToMany(mappedBy = "pelecula")
    private Set<ActorPelicula> peleculaActorPeliculas;

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(final Integer idPelecula) {
        this.idPelicula = idPelecula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPreUnitario() {
        return preUnitario;
    }

    public void setPreUnitario(final BigDecimal preUnitario) {
        this.preUnitario = preUnitario;
    }

    public Double getPorceUtil() {
        return porceUtil;
    }

    public void setPorceUtil(final Double porceUtil) {
        this.porceUtil = porceUtil;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(final Integer stock) {
        this.stock = stock;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(final Genero genero) {
        this.genero = genero;
    }

    public Set<VentaDetallada> getPeleculaVentaDetalladas() {
        return peleculaVentaDetalladas;
    }

    public void setPeleculaVentaDetalladas(final Set<VentaDetallada> peleculaVentaDetalladas) {
        this.peleculaVentaDetalladas = peleculaVentaDetalladas;
    }

    public Set<ActorPelicula> getPeleculaActorPeliculas() {
        return peleculaActorPeliculas;
    }

    public void setPeleculaActorPeliculas(final Set<ActorPelicula> peleculaActorPeliculas) {
        this.peleculaActorPeliculas = peleculaActorPeliculas;
    }

    public String getGeneroNombre() {
        return genero != null ? genero.getNombre() : null;
    }

}
