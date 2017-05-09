/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.beans;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento")
    , @NamedQuery(name = "Evento.findByNombre", query = "SELECT e FROM Evento e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Evento.findByFechaInicio", query = "SELECT e FROM Evento e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Evento.findByFechaFinal", query = "SELECT e FROM Evento e WHERE e.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "Evento.findByPrecio", query = "SELECT e FROM Evento e WHERE e.precio = :precio")
    , @NamedQuery(name = "Evento.findByMaxEntradas", query = "SELECT e FROM Evento e WHERE e.maxEntradas = :maxEntradas")
    , @NamedQuery(name = "Evento.findByMaxReservas", query = "SELECT e FROM Evento e WHERE e.maxReservas = :maxReservas")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idEvento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int maxEntradas;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int maxReservas;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventoidEvento")
    private List<AsistenteEvento> asistenteEventoList;
    @JoinColumn(name = "Discoteca_idDiscoteca", referencedColumnName = "idDiscoteca", nullable = false)
    @ManyToOne(optional = false)
    private Discoteca discotecaidDiscoteca;

    public Evento() {
    }

    public Evento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Evento(Integer idEvento, String nombre, Date fechaInicio, Date fechaFinal, BigDecimal precio, int maxEntradas, int maxReservas) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.precio = precio;
        this.maxEntradas = maxEntradas;
        this.maxReservas = maxReservas;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

    @XmlTransient
    public List<AsistenteEvento> getAsistenteEventoList() {
        return asistenteEventoList;
    }

    public void setAsistenteEventoList(List<AsistenteEvento> asistenteEventoList) {
        this.asistenteEventoList = asistenteEventoList;
    }

    public Discoteca getDiscotecaidDiscoteca() {
        return discotecaidDiscoteca;
    }

    public void setDiscotecaidDiscoteca(Discoteca discotecaidDiscoteca) {
        this.discotecaidDiscoteca = discotecaidDiscoteca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Evento[ idEvento=" + idEvento + " ]";
    }

}
