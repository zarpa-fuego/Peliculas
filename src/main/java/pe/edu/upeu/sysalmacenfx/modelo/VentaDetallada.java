package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;


@Entity
public class VentaDetallada {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVentadetallada;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal precioUnit;

    @Column(nullable = false)
    private Double porcenUtil;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal montoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelecula_id", nullable = false)
    private Pelicula pelecula;

    public Integer getIdVentadetallada() {
        return idVentadetallada;
    }

    public void setIdVentadetallada(final Integer idVentadetallada) {
        this.idVentadetallada = idVentadetallada;
    }

    public BigDecimal getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(final BigDecimal precioUnit) {
        this.precioUnit = precioUnit;
    }

    public Double getPorcenUtil() {
        return porcenUtil;
    }

    public void setPorcenUtil(final Double porcenUtil) {
        this.porcenUtil = porcenUtil;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(final Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(final BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(final Venta venta) {
        this.venta = venta;
    }

    public Pelicula getPelecula() {
        return pelecula;
    }

    public void setPelecula(final Pelicula pelecula) {
        this.pelecula = pelecula;
    }

}
