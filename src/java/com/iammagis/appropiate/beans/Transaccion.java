/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sebasariz
 */
@Entity
@Table(name = "transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByIdtransaccion", query = "SELECT t FROM Transaccion t WHERE t.idtransaccion = :idtransaccion"),
    @NamedQuery(name = "Transaccion.findByFecha", query = "SELECT t FROM Transaccion t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Transaccion.findByEstado", query = "SELECT t FROM Transaccion t WHERE t.estado = :estado"),
    @NamedQuery(name = "Transaccion.findByFechaRetorno", query = "SELECT t FROM Transaccion t WHERE t.fechaRetorno = :fechaRetorno")})
public class Transaccion implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransaccion")
    private Integer idtransaccion;
    @Column(name = "fecha")
    private BigInteger fecha;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_retorno")
    private BigInteger fechaRetorno;
    @JoinColumn(name = "factura_idfactura", referencedColumnName = "idfactura")
    @ManyToOne(optional = false)
    private Factura facturaIdfactura;

    public Transaccion() {
    }

    public Transaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Integer getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public BigInteger getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(BigInteger fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public Factura getFacturaIdfactura() {
        return facturaIdfactura;
    }

    public void setFacturaIdfactura(Factura facturaIdfactura) {
        this.facturaIdfactura = facturaIdfactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccion != null ? idtransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.idtransaccion == null && other.idtransaccion != null) || (this.idtransaccion != null && !this.idtransaccion.equals(other.idtransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Transaccion[ idtransaccion=" + idtransaccion + " ]";
    }
 
}
