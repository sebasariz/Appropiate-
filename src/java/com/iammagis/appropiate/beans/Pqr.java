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

/**
 *
 * @author sebastianarizmendy
 */
@Entity
@Table(name = "pqr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pqr.findAll", query = "SELECT p FROM Pqr p")
    , @NamedQuery(name = "Pqr.findByIdpqr", query = "SELECT p FROM Pqr p WHERE p.idpqr = :idpqr")
    , @NamedQuery(name = "Pqr.findByFecha", query = "SELECT p FROM Pqr p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Pqr.findByEstado", query = "SELECT p FROM Pqr p WHERE p.estado = :estado")})
public class Pqr implements Serializable {
 

     
    @Column(name = "estado")
    private Integer estado;

    @Column(name = "tipo")
    private Integer tipo;

    @OneToMany(mappedBy = "pqrIdpqr")
    private Collection<Comentario> comentarioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpqr")
    private Integer idpqr;
    @Column(name = "fecha")
    private BigInteger fecha;
    @Lob
    @Column(name = "pqr")
    private String pqr;
    @OneToMany(mappedBy = "pqrIdpqr")
    private Collection<Archivo> archivoCollection;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    public Pqr() {
    }

    public Pqr(Integer idpqr) {
        this.idpqr = idpqr;
    }

    public Integer getIdpqr() {
        return idpqr;
    }

    public void setIdpqr(Integer idpqr) {
        this.idpqr = idpqr;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public String getPqr()  {
        return pqr;
    }

    public void setPqr(String pqr) {
        this.pqr = pqr;
    }

 
    @XmlTransient
    public Collection<Archivo> getArchivoCollection() {
        return archivoCollection;
    }

    public void setArchivoCollection(Collection<Archivo> archivoCollection) {
        this.archivoCollection = archivoCollection;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpqr != null ? idpqr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pqr)) {
            return false;
        }
        Pqr other = (Pqr) object;
        if ((this.idpqr == null && other.idpqr != null) || (this.idpqr != null && !this.idpqr.equals(other.idpqr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Pqr[ idpqr=" + idpqr + " ]";
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
 
 
}
