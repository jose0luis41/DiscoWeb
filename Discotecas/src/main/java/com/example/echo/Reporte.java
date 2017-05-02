/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.echo;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@Table(name = "Reporte", catalog = "discotecas", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reporte.findAll", query = "SELECT r FROM Reporte r")
    , @NamedQuery(name = "Reporte.findByIdReporte", query = "SELECT r FROM Reporte r WHERE r.idReporte = :idReporte")
    , @NamedQuery(name = "Reporte.findByFecha", query = "SELECT r FROM Reporte r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Reporte.findByContenido", query = "SELECT r FROM Reporte r WHERE r.contenido = :contenido")})
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idReporte", nullable = false)
    private Integer idReporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 250)
    @Column(name = "contenido", length = 250)
    private String contenido;
    @JsonIgnore
    @JoinColumn(name = "TipoReporte_idTipoReporte", referencedColumnName = "idTipoReporte", nullable = false)
    @ManyToOne(optional = false)
    private TipoReporte tipoReporteidTipoReporte;
    @JsonIgnore
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioidUsuario;

    public Reporte() {
    }

    public Reporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public Reporte(TipoReporte tipoReporte, Usuario usuarioId, Integer idReporte, Date fecha, String contenido) {
        this.usuarioidUsuario = usuarioId;
        this.tipoReporteidTipoReporte = tipoReporte;
        this.idReporte = idReporte;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoReporte getTipoReporteidTipoReporte() {
        return tipoReporteidTipoReporte;
    }

    public void setTipoReporteidTipoReporte(TipoReporte tipoReporteidTipoReporte) {
        this.tipoReporteidTipoReporte = tipoReporteidTipoReporte;
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
        hash += (idReporte != null ? idReporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.idReporte == null && other.idReporte != null) || (this.idReporte != null && !this.idReporte.equals(other.idReporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.echo.Reporte[ idReporte=" + idReporte + " ]";
    }

}
