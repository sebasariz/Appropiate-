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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author sebasariz
 */
@Entity
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdfactura", query = "SELECT f FROM Factura f WHERE f.idfactura = :idfactura"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByValor", query = "SELECT f FROM Factura f WHERE f.valor = :valor")})
public class Factura extends ActionForm implements Serializable {

    @Column(name = "valor_pagado")
    private Double valorPagado;

    @Column(name = "estado")
    private Integer estado;

    @Lob
    @Column(name = "referencia")
    private String referencia;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfactura")
    private Integer idfactura;
    @Column(name = "fecha")
    private BigInteger fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Lob
    @Column(name = "conceptos")
    private String conceptos;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @JoinColumn(name = "factura_template_idfactura_template", referencedColumnName = "idfactura_template")
    @ManyToOne(optional = false)
    private FacturaTemplate facturaTemplateIdfacturaTemplate;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturaIdfactura")
    private Collection<Transaccion> transaccionCollection;
    @Transient
    int idUsuario;
    @Transient
    int idTemplate;

    public int getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(int idTemplate) {
        this.idTemplate = idTemplate;
    }
    
    

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    

    public Factura() {
    }

    public Factura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Integer getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getConceptos() {
        return conceptos;
    }

    public void setConceptos(String conceptos) {
        this.conceptos = conceptos;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    public FacturaTemplate getFacturaTemplateIdfacturaTemplate() {
        return facturaTemplateIdfacturaTemplate;
    }

    public void setFacturaTemplateIdfacturaTemplate(FacturaTemplate facturaTemplateIdfacturaTemplate) {
        this.facturaTemplateIdfacturaTemplate = facturaTemplateIdfacturaTemplate;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @XmlTransient
    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfactura != null ? idfactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idfactura == null && other.idfactura != null) || (this.idfactura != null && !this.idfactura.equals(other.idfactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Factura[ idfactura=" + idfactura + " ]";
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }
    
}
