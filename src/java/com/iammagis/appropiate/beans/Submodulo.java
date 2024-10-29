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
@Table(name = "submodulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Submodulo.findAll", query = "SELECT s FROM Submodulo s")
    , @NamedQuery(name = "Submodulo.findByIdsubmodulo", query = "SELECT s FROM Submodulo s WHERE s.idsubmodulo = :idsubmodulo")
    , @NamedQuery(name = "Submodulo.findByNombre", query = "SELECT s FROM Submodulo s WHERE s.nombre = :nombre")})
public class Submodulo implements Serializable {
 
    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submodulo")
    private Collection<SubmoduloHasUsuario> submoduloHasUsuarioCollection;

    @Column(name = "accion")
    private String accion;
    @Column(name = "ico")
    private String ico;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubmodulo")
    private Integer idsubmodulo;
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "submodulo_has_usuario", joinColumns = {
        @JoinColumn(name = "submodulo_idsubmodulo", referencedColumnName = "idsubmodulo")}, inverseJoinColumns = {
        @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "modulo_idmodulo", referencedColumnName = "idmodulo")
    @ManyToOne(optional = false)
    private Modulo moduloIdmodulo;

    public Submodulo() {
    }

    public Submodulo(Integer idsubmodulo) {
        this.idsubmodulo = idsubmodulo;
    }

    public Integer getIdsubmodulo() {
        return idsubmodulo;
    }

    public void setIdsubmodulo(Integer idsubmodulo) {
        this.idsubmodulo = idsubmodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Modulo getModuloIdmodulo() {
        return moduloIdmodulo;
    }

    public void setModuloIdmodulo(Modulo moduloIdmodulo) {
        this.moduloIdmodulo = moduloIdmodulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsubmodulo != null ? idsubmodulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Submodulo)) {
            return false;
        }
        Submodulo other = (Submodulo) object;
        if ((this.idsubmodulo == null && other.idsubmodulo != null) || (this.idsubmodulo != null && !this.idsubmodulo.equals(other.idsubmodulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Submodulo[ idsubmodulo=" + idsubmodulo + " ]";
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    @XmlTransient
    public Collection<SubmoduloHasUsuario> getSubmoduloHasUsuarioCollection() {
        return submoduloHasUsuarioCollection;
    }

    public void setSubmoduloHasUsuarioCollection(Collection<SubmoduloHasUsuario> submoduloHasUsuarioCollection) {
        this.submoduloHasUsuarioCollection = submoduloHasUsuarioCollection;
    }
 
}
