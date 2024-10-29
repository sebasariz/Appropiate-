/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "modulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m")
    , @NamedQuery(name = "Modulo.findByIdmodulo", query = "SELECT m FROM Modulo m WHERE m.idmodulo = :idmodulo")
    , @NamedQuery(name = "Modulo.findByNombre", query = "SELECT m FROM Modulo m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Modulo.findByIcon", query = "SELECT m FROM Modulo m WHERE m.icon = :icon")})
public class Modulo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    private Collection<ModuloHasUsuario> moduloHasUsuarioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmodulo")
    private Integer idmodulo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "icon")
    private String icon;
    @JoinTable(name = "modulo_has_usuario", joinColumns = {
        @JoinColumn(name = "modulo_idmodulo", referencedColumnName = "idmodulo")}, inverseJoinColumns = {
        @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduloIdmodulo")
    private Collection<Submodulo> submoduloCollection;

    public Modulo() {
    }

    public Modulo(Integer idmodulo) {
        this.idmodulo = idmodulo;
    }

    public Integer getIdmodulo() {
        return idmodulo;
    }

    public void setIdmodulo(Integer idmodulo) {
        this.idmodulo = idmodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @XmlTransient
    public Collection<Submodulo> getSubmoduloCollection() {
        return submoduloCollection;
    }

    public void setSubmoduloCollection(Collection<Submodulo> submoduloCollection) {
        this.submoduloCollection = submoduloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodulo != null ? idmodulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.idmodulo == null && other.idmodulo != null) || (this.idmodulo != null && !this.idmodulo.equals(other.idmodulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Modulo[ idmodulo=" + idmodulo + " ]";
    }

    @XmlTransient
    public Collection<ModuloHasUsuario> getModuloHasUsuarioCollection() {
        return moduloHasUsuarioCollection;
    }

    public void setModuloHasUsuarioCollection(Collection<ModuloHasUsuario> moduloHasUsuarioCollection) {
        this.moduloHasUsuarioCollection = moduloHasUsuarioCollection;
    }
    
}
