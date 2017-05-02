/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.echo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@Table(name = "Configuracion", catalog = "discotecas", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
    , @NamedQuery(name = "Configuracion.findByIdConfiguracion", query = "SELECT c FROM Configuracion c WHERE c.idConfiguracion = :idConfiguracion")
    , @NamedQuery(name = "Configuracion.findByMaxEntradas", query = "SELECT c FROM Configuracion c WHERE c.maxEntradas = :maxEntradas")
    , @NamedQuery(name = "Configuracion.findByMaxReservas", query = "SELECT c FROM Configuracion c WHERE c.maxReservas = :maxReservas")})
public class Configuracion implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precioEntrada", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioEntrada;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idConfiguracion", nullable = false)
    private Integer idConfiguracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxEntradas", nullable = false)
    private int maxEntradas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxReservas", nullable = false)
    private int maxReservas;

    public Configuracion() {
    }

    public Configuracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Configuracion(Integer idConfiguracion, int maxEntradas, int maxReservas) {
        this.idConfiguracion = idConfiguracion;
        this.maxEntradas = maxEntradas;
        this.maxReservas = maxReservas;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public int getMaxEntradas() {
        return maxEntradas;
    }

    public void setMaxEntradas(int maxEntradas) {
        this.maxEntradas = maxEntradas;
    }

    public int getMaxReservas() {
        return maxReservas;
    }

    public void setMaxReservas(int maxReservas) {
        this.maxReservas = maxReservas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.echo.Configuracion[ idConfiguracion=" + idConfiguracion + " ]";
    }

    public BigDecimal getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(BigDecimal precioEntrada) {
        this.precioEntrada = precioEntrada;
    }
    
}
