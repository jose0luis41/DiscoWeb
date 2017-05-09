/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.beans;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @NamedQuery(name = "Asistente.findAll", query = "SELECT a FROM Asistente a")
    , @NamedQuery(name = "Asistente.findByIdAsistente", query = "SELECT a FROM Asistente a WHERE a.idAsistente = :idAsistente")
    , @NamedQuery(name = "Asistente.findByCedula", query = "SELECT a FROM Asistente a WHERE a.cedula = :cedula")
    , @NamedQuery(name = "Asistente.findByCorreo", query = "SELECT a FROM Asistente a WHERE a.correo = :correo")
    , @NamedQuery(name = "Asistente.findByNombre", query = "SELECT a FROM Asistente a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Asistente.findByTelefono", query = "SELECT a FROM Asistente a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Asistente.findByContrasena", query = "SELECT a FROM Asistente a WHERE a.contrasena = :contrasena")
    , @NamedQuery(name = "Asistente.findByFechaNacimiento", query = "SELECT a FROM Asistente a WHERE a.fechaNacimiento = :fechaNacimiento")})
public class Asistente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idAsistente;
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
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String contrasena;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asistenteidAsistente")
    private List<AsistenteEvento> asistenteEventoList;

    public Asistente() {
    }

    public Asistente(Integer idAsistente) {
        this.idAsistente = idAsistente;
    }

    public Asistente(Integer idAsistente, String cedula, String correo, String nombre, String telefono, String contrasena, Date fechaNacimiento) {
        this.idAsistente = idAsistente;
        this.cedula = cedula;
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(Integer idAsistente) {
        this.idAsistente = idAsistente;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @XmlTransient
    public List<AsistenteEvento> getAsistenteEventoList() {
        return asistenteEventoList;
    }

    public void setAsistenteEventoList(List<AsistenteEvento> asistenteEventoList) {
        this.asistenteEventoList = asistenteEventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsistente != null ? idAsistente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistente)) {
            return false;
        }
        Asistente other = (Asistente) object;
        if ((this.idAsistente == null && other.idAsistente != null) || (this.idAsistente != null && !this.idAsistente.equals(other.idAsistente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Asistente[ idAsistente=" + idAsistente + " ]";
    }
    
}
