/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author sebasariz
 */
@Entity
@Table(name = "factura_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaTemplate.findAll", query = "SELECT f FROM FacturaTemplate f"),
    @NamedQuery(name = "FacturaTemplate.findByIdfacturaTemplate", query = "SELECT f FROM FacturaTemplate f WHERE f.idfacturaTemplate = :idfacturaTemplate"),
    @NamedQuery(name = "FacturaTemplate.findByNombre", query = "SELECT f FROM FacturaTemplate f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "FacturaTemplate.findByFechaCreacion", query = "SELECT f FROM FacturaTemplate f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FacturaTemplate.findByFechaModificado", query = "SELECT f FROM FacturaTemplate f WHERE f.fechaModificado = :fechaModificado"),
    @NamedQuery(name = "FacturaTemplate.findByFechaActualizacion", query = "SELECT f FROM FacturaTemplate f WHERE f.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "FacturaTemplate.findByEstado", query = "SELECT f FROM FacturaTemplate f WHERE f.estado = :estado")})
public class FacturaTemplate extends ActionForm implements Serializable {

    @Lob
    @Column(name = "campos")
    private String campos;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfactura_template")
    private Integer idfacturaTemplate;
    @Lob
    @Column(name = "template")
    private String template;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_creacion")
    private BigInteger fechaCreacion;
    @Column(name = "fecha_modificado")
    private BigInteger fechaModificado;
    @Column(name = "fecha_actualizacion")
    private BigInteger fechaActualizacion;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaTemplateIdfacturaTemplate")
    private Collection<Factura> facturaCollection;

    public FacturaTemplate() {
    }

    public FacturaTemplate(Integer idfacturaTemplate) {
        this.idfacturaTemplate = idfacturaTemplate;
    }

    public Integer getIdfacturaTemplate() {
        return idfacturaTemplate;
    }

    public void setIdfacturaTemplate(Integer idfacturaTemplate) {
        this.idfacturaTemplate = idfacturaTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(BigInteger fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getFechaModificado() {
        return fechaModificado;
    }

    public void setFechaModificado(BigInteger fechaModificado) {
        this.fechaModificado = fechaModificado;
    }

    public BigInteger getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(BigInteger fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfacturaTemplate != null ? idfacturaTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaTemplate)) {
            return false;
        }
        FacturaTemplate other = (FacturaTemplate) object;
        if ((this.idfacturaTemplate == null && other.idfacturaTemplate != null) || (this.idfacturaTemplate != null && !this.idfacturaTemplate.equals(other.idfacturaTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.FacturaTemplate[ idfacturaTemplate=" + idfacturaTemplate + " ]";
    }

    public String getCampos() {
        return campos;
    }

    public void setCampos(String campos) {
        this.campos = campos;
    }
    
}
