/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.beans;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuario.findByCedula", query = "SELECT u FROM Usuario u WHERE u.cedula = :cedula")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Usuario.findByFechaNac", query = "SELECT u FROM Usuario u WHERE u.fechaNac = :fechaNac")
    , @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idUsuario;
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
    @JsonIgnore
    @JoinColumn(name = "Rol_idRol", referencedColumnName = "idRol", nullable = false)
    @ManyToOne(optional = false)
    private Rol rolidRol;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<Reserva> reservaList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<Reporte> reporteList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidUsuario")
    private List<Entrada> entradaList;

    public Usuario() {

        this.reservaList = new LinkedList<>();
        this.reporteList = new LinkedList<>();
        this.entradaList = new LinkedList<>();
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
        this.reservaList = new LinkedList<>();
        this.reporteList = new LinkedList<>();
        this.entradaList = new LinkedList<>();
    }

    public Usuario(Rol rol, Integer idUsuario, String cedula, String correo, String nombre, String telefono, Date fechaNac, String contrasena) {
        this.rolidRol = rol;
        this.idUsuario = idUsuario;
        this.cedula = cedula;
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.contrasena = contrasena;
        this.reservaList = new LinkedList<>();
        this.reporteList = new LinkedList<>();
        this.entradaList = new LinkedList<>();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public Rol getRolidRol() {
        return rolidRol;
    }

    public void setRolidRol(Rol rolidRol) {
        this.rolidRol = rolidRol;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @XmlTransient
    public List<Reporte> getReporteList() {
        return reporteList;
    }

    public void setReporteList(List<Reporte> reporteList) {
        this.reporteList = reporteList;
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
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Usuario[ idUsuario=" + idUsuario + " ]";
    }

}
