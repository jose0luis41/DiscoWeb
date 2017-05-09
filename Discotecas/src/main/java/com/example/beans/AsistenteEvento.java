/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.beans;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenteEvento.findAll", query = "SELECT a FROM AsistenteEvento a")
    , @NamedQuery(name = "AsistenteEvento.findByIdAsistenteEvento", query = "SELECT a FROM AsistenteEvento a WHERE a.idAsistenteEvento = :idAsistenteEvento")})
public class AsistenteEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idAsistenteEvento;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asistenteEventoidAsistenteEvento")
    private List<Reserva> reservaList;
    @JoinColumn(name = "Asistente_idAsistente", referencedColumnName = "idAsistente", nullable = false)
    @ManyToOne(optional = false)
    private Asistente asistenteidAsistente;
    @JoinColumn(name = "Evento_idEvento", referencedColumnName = "idEvento", nullable = false)
    @ManyToOne(optional = false)
    private Evento eventoidEvento;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asistenteEventoidAsistenteEvento")
    private List<Entrada> entradaList;

    public AsistenteEvento() {
    }

    public AsistenteEvento(Integer idAsistenteEvento) {
        this.idAsistenteEvento = idAsistenteEvento;
    }

    public Integer getIdAsistenteEvento() {
        return idAsistenteEvento;
    }

    public void setIdAsistenteEvento(Integer idAsistenteEvento) {
        this.idAsistenteEvento = idAsistenteEvento;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    public Asistente getAsistenteidAsistente() {
        return asistenteidAsistente;
    }

    public void setAsistenteidAsistente(Asistente asistenteidAsistente) {
        this.asistenteidAsistente = asistenteidAsistente;
    }

    public Evento getEventoidEvento() {
        return eventoidEvento;
    }

    public void setEventoidEvento(Evento eventoidEvento) {
        this.eventoidEvento = eventoidEvento;
    }

    @XmlTransient
    public List<Entrada> getEntradaList() {
        return entradaList;
    }

    public void setEntradaList(List<Entrada> entradaList) {
        this.entradaList = entradaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsistenteEvento != null ? idAsistenteEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenteEvento)) {
            return false;
        }
        AsistenteEvento other = (AsistenteEvento) object;
        if ((this.idAsistenteEvento == null && other.idAsistenteEvento != null) || (this.idAsistenteEvento != null && !this.idAsistenteEvento.equals(other.idAsistenteEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.AsistenteEvento[ idAsistenteEvento=" + idAsistenteEvento + " ]";
    }

}
