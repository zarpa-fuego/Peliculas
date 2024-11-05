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
import java.time.LocalDate;
import java.util.Set;


@Entity
public class Venta {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    @Column(nullable = false)
    private LocalDate fechaVenta;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal netoTotal;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal igv;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal montoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_id", nullable = false)
    private Cliente dni;

    @OneToMany(mappedBy = "venta")
    private Set<VentaDetallada> ventaVentaDetalladas;

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(final Integer idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(final LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getNetoTotal() {
        return netoTotal;
    }

    public void setNetoTotal(final BigDecimal netoTotal) {
        this.netoTotal = netoTotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(final BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(final BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Cliente getDni() {
        return dni;
    }

    public void setDni(final Cliente dni) {
        this.dni = dni;
    }

    public Set<VentaDetallada> getVentaVentaDetalladas() {
        return ventaVentaDetalladas;
    }

    public void setVentaVentaDetalladas(final Set<VentaDetallada> ventaVentaDetalladas) {
        this.ventaVentaDetalladas = ventaVentaDetalladas;
    }

}
