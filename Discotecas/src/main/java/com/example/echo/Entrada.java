/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.echo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@Table(name = "Entrada", catalog = "discotecas", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entrada.findAll", query = "SELECT e FROM Entrada e")
    , @NamedQuery(name = "Entrada.findByIdEntrada", query = "SELECT e FROM Entrada e WHERE e.idEntrada = :idEntrada")
    , @NamedQuery(name = "Entrada.findByPrecio", query = "SELECT e FROM Entrada e WHERE e.precio = :precio")
    , @NamedQuery(name = "Entrada.findByFechaCompra", query = "SELECT e FROM Entrada e WHERE e.fechaCompra = :fechaCompra")
    , @NamedQuery(name = "Entrada.findByFechaVencimiento", query = "SELECT e FROM Entrada e WHERE e.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "Entrada.findByCodigoQR", query = "SELECT e FROM Entrada e WHERE e.codigoQR = :codigoQR")})
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEntrada", nullable = false)
    private Integer idEntrada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCompra", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaVencimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigoQR", nullable = false, length = 100)
    private String codigoQR;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario;

    public Entrada() {
    }

    public Entrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Entrada(Integer idEntrada,Usuario usuarioId, BigDecimal precio, Date fechaCompra, Date fechaVencimiento, String codigoQR) {
        this.usuarioidUsuario = usuarioId;
        this.idEntrada = idEntrada;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoQR = codigoQR;
    }

    public Integer getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public Usuario getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(Usuario usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntrada != null ? idEntrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrada)) {
            return false;
        }
        Entrada other = (Entrada) object;
        if ((this.idEntrada == null && other.idEntrada != null) || (this.idEntrada != null && !this.idEntrada.equals(other.idEntrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.echo.Entrada[ idEntrada=" + idEntrada + " ]";
    }
    
}