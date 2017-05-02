/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.echo;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joseluissacanamboy
 */
@Entity
@Table(name = "TipoReporte", catalog = "discotecas", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoReporte.findAll", query = "SELECT t FROM TipoReporte t")
    , @NamedQuery(name = "TipoReporte.findByIdTipoReporte", query = "SELECT t FROM TipoReporte t WHERE t.idTipoReporte = :idTipoReporte")
    , @NamedQuery(name = "TipoReporte.findByTipo", query = "SELECT t FROM TipoReporte t WHERE t.tipo = :tipo")})
public class TipoReporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoReporte", nullable = false)
    private Integer idTipoReporte;
    @Size(max = 45)
    @Column(name = "tipo", length = 45)
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoReporteidTipoReporte")
    private List<Reporte> reporteList;

    public TipoReporte() {
    }

    public TipoReporte(Integer idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public Integer getIdTipoReporte() {
        return idTipoReporte;
    }

    public void setIdTipoReporte(Integer idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Reporte> getReporteList() {
        return reporteList;
    }

    public void setReporteList(List<Reporte> reporteList) {
        this.reporteList = reporteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoReporte != null ? idTipoReporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoReporte)) {
            return false;
        }
        TipoReporte other = (TipoReporte) object;
        if ((this.idTipoReporte == null && other.idTipoReporte != null) || (this.idTipoReporte != null && !this.idTipoReporte.equals(other.idTipoReporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.echo.TipoReporte[ idTipoReporte=" + idTipoReporte + " ]";
    }
    
}
