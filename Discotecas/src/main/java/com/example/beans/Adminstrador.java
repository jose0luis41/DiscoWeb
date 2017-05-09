/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.beans;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo"})
    , @UniqueConstraint(columnNames = {"cedula"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adminstrador.findAll", query = "SELECT a FROM Adminstrador a")
    , @NamedQuery(name = "Adminstrador.findByCedula", query = "SELECT a FROM Adminstrador a WHERE a.cedula = :cedula")
    , @NamedQuery(name = "Adminstrador.findByCorreo", query = "SELECT a FROM Adminstrador a WHERE a.correo = :correo")
    , @NamedQuery(name = "Adminstrador.findByNombre", query = "SELECT a FROM Adminstrador a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Adminstrador.findByTelefono", query = "SELECT a FROM Adminstrador a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Adminstrador.findByFechaNac", query = "SELECT a FROM Adminstrador a WHERE a.fechaNac = :fechaNac")
    , @NamedQuery(name = "Adminstrador.findByContrasena", query = "SELECT a FROM Adminstrador a WHERE a.contrasena = :contrasena")
    , @NamedQuery(name = "Adminstrador.findByIdAdministrador", query = "SELECT a FROM Adminstrador a WHERE a.idAdministrador = :idAdministrador")})
public class Adminstrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String cedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String contrasena;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idAdministrador;
    @JoinColumn(name = "Discoteca_idDiscoteca", referencedColumnName = "idDiscoteca", nullable = false)
    @ManyToOne(optional = false)
    private Discoteca discotecaidDiscoteca;

    public Adminstrador() {
    }

    public Adminstrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Adminstrador(Integer idAdministrador, String cedula, String correo, String nombre, String telefono, Date fechaNac, String contrasena) {
        this.idAdministrador = idAdministrador;
        this.cedula = cedula;
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.contrasena = contrasena;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
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
        hash += (idAdministrador != null ? idAdministrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adminstrador)) {
            return false;
        }
        Adminstrador other = (Adminstrador) object;
        if ((this.idAdministrador == null && other.idAdministrador != null) || (this.idAdministrador != null && !this.idAdministrador.equals(other.idAdministrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Adminstrador[ idAdministrador=" + idAdministrador + " ]";
    }
    
}
