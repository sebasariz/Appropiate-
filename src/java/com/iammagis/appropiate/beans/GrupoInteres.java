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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author sebasariz
 */
@Entity
@Table(name = "grupo_interes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoInteres.findAll", query = "SELECT g FROM GrupoInteres g"),
    @NamedQuery(name = "GrupoInteres.findByIdgrupoInteres", query = "SELECT g FROM GrupoInteres g WHERE g.idgrupoInteres = :idgrupoInteres"),
    @NamedQuery(name = "GrupoInteres.findByFecha", query = "SELECT g FROM GrupoInteres g WHERE g.fecha = :fecha")})
public class GrupoInteres extends ActionForm implements Serializable {

    @ManyToMany(mappedBy = "grupoInteresCollection")
    private Collection<Encuesta> encuestaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgrupo_interes")
    private Integer idgrupoInteres;
    @Lob
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    private BigInteger fecha;
    @JoinTable(name = "usuario_has_grupo_interes", joinColumns = {
        @JoinColumn(name = "grupo_interes_idgrupo_interes", referencedColumnName = "idgrupo_interes")}, inverseJoinColumns = {
        @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;

    public GrupoInteres() {
    }

    public GrupoInteres(Integer idgrupoInteres) {
        this.idgrupoInteres = idgrupoInteres;
    }

    public Integer getIdgrupoInteres() {
        return idgrupoInteres;
    }

    public void setIdgrupoInteres(Integer idgrupoInteres) {
        this.idgrupoInteres = idgrupoInteres;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgrupoInteres != null ? idgrupoInteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoInteres)) {
            return false;
        }
        GrupoInteres other = (GrupoInteres) object;
        if ((this.idgrupoInteres == null && other.idgrupoInteres != null) || (this.idgrupoInteres != null && !this.idgrupoInteres.equals(other.idgrupoInteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.GrupoInteres[ idgrupoInteres=" + idgrupoInteres + " ]";
    }

    @XmlTransient
    public Collection<Encuesta> getEncuestaCollection() {
        return encuestaCollection;
    }

    public void setEncuestaCollection(Collection<Encuesta> encuestaCollection) {
        this.encuestaCollection = encuestaCollection;
    }
    
}
