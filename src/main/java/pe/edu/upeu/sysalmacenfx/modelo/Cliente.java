package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class Cliente {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDni;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private Integer celular;

    @OneToMany(mappedBy = "dni")
    private Set<Venta> dniVentas;

    public Integer getIdDni() {
        return idDni;
    }

    public void setIdDni(final Integer idDni) {
        this.idDni = idDni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(final Integer celular) {
        this.celular = celular;
    }

    public Set<Venta> getDniVentas() {
        return dniVentas;
    }

    public void setDniVentas(final Set<Venta> dniVentas) {
        this.dniVentas = dniVentas;
    }

}
