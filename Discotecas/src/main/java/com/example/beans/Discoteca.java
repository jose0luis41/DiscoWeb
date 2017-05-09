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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Discoteca.findAll", query = "SELECT d FROM Discoteca d")
    , @NamedQuery(name = "Discoteca.findByIdDiscoteca", query = "SELECT d FROM Discoteca d WHERE d.idDiscoteca = :idDiscoteca")
    , @NamedQuery(name = "Discoteca.findByNombre", query = "SELECT d FROM Discoteca d WHERE d.nombre = :nombre")})
public class Discoteca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idDiscoteca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String nombre;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discotecaidDiscoteca")
    private List<Adminstrador> adminstradorList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discotecaidDiscoteca")
    private List<Evento> eventoList;

    public Discoteca() {
    }

    public Discoteca(Integer idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public Discoteca(Integer idDiscoteca, String nombre) {
        this.idDiscoteca = idDiscoteca;
        this.nombre = nombre;
    }

    public Integer getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(Integer idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Adminstrador> getAdminstradorList() {
        return adminstradorList;
    }

    public void setAdminstradorList(List<Adminstrador> adminstradorList) {
        this.adminstradorList = adminstradorList;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDiscoteca != null ? idDiscoteca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discoteca)) {
            return false;
        }
        Discoteca other = (Discoteca) object;
        if ((this.idDiscoteca == null && other.idDiscoteca != null) || (this.idDiscoteca != null && !this.idDiscoteca.equals(other.idDiscoteca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Discoteca[ idDiscoteca=" + idDiscoteca + " ]";
    }

}
