/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "submodulo_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubmoduloHasUsuario.findAll", query = "SELECT s FROM SubmoduloHasUsuario s"),
    @NamedQuery(name = "SubmoduloHasUsuario.findBySubmoduloIdsubmodulo", query = "SELECT s FROM SubmoduloHasUsuario s WHERE s.submoduloHasUsuarioPK.submoduloIdsubmodulo = :submoduloIdsubmodulo"),
    @NamedQuery(name = "SubmoduloHasUsuario.findByUsuarioIdusuario", query = "SELECT s FROM SubmoduloHasUsuario s WHERE s.submoduloHasUsuarioPK.usuarioIdusuario = :usuarioIdusuario")})
public class SubmoduloHasUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SubmoduloHasUsuarioPK submoduloHasUsuarioPK;
    @JoinColumn(name = "submodulo_idsubmodulo", referencedColumnName = "idsubmodulo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Submodulo submodulo;

    public SubmoduloHasUsuario() {
    }

    public SubmoduloHasUsuario(SubmoduloHasUsuarioPK submoduloHasUsuarioPK) {
        this.submoduloHasUsuarioPK = submoduloHasUsuarioPK;
    }

    public SubmoduloHasUsuario(int submoduloIdsubmodulo, int usuarioIdusuario) {
        this.submoduloHasUsuarioPK = new SubmoduloHasUsuarioPK(submoduloIdsubmodulo, usuarioIdusuario);
    }

    public SubmoduloHasUsuarioPK getSubmoduloHasUsuarioPK() {
        return submoduloHasUsuarioPK;
    }

    public void setSubmoduloHasUsuarioPK(SubmoduloHasUsuarioPK submoduloHasUsuarioPK) {
        this.submoduloHasUsuarioPK = submoduloHasUsuarioPK;
    }

    public Submodulo getSubmodulo() {
        return submodulo;
    }

    public void setSubmodulo(Submodulo submodulo) {
        this.submodulo = submodulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (submoduloHasUsuarioPK != null ? submoduloHasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubmoduloHasUsuario)) {
            return false;
        }
        SubmoduloHasUsuario other = (SubmoduloHasUsuario) object;
        if ((this.submoduloHasUsuarioPK == null && other.submoduloHasUsuarioPK != null) || (this.submoduloHasUsuarioPK != null && !this.submoduloHasUsuarioPK.equals(other.submoduloHasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.SubmoduloHasUsuario[ submoduloHasUsuarioPK=" + submoduloHasUsuarioPK + " ]";
    }
    
}
