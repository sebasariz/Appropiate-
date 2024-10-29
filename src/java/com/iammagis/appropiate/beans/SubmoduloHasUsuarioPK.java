/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author sebasariz
 */
@Embeddable
public class SubmoduloHasUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "submodulo_idsubmodulo")
    private int submoduloIdsubmodulo;
    @Basic(optional = false)
    @Column(name = "usuario_idusuario")
    private int usuarioIdusuario;

    public SubmoduloHasUsuarioPK() {
    }

    public SubmoduloHasUsuarioPK(int submoduloIdsubmodulo, int usuarioIdusuario) {
        this.submoduloIdsubmodulo = submoduloIdsubmodulo;
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public int getSubmoduloIdsubmodulo() {
        return submoduloIdsubmodulo;
    }

    public void setSubmoduloIdsubmodulo(int submoduloIdsubmodulo) {
        this.submoduloIdsubmodulo = submoduloIdsubmodulo;
    }

    public int getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(int usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) submoduloIdsubmodulo;
        hash += (int) usuarioIdusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubmoduloHasUsuarioPK)) {
            return false;
        }
        SubmoduloHasUsuarioPK other = (SubmoduloHasUsuarioPK) object;
        if (this.submoduloIdsubmodulo != other.submoduloIdsubmodulo) {
            return false;
        }
        if (this.usuarioIdusuario != other.usuarioIdusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.SubmoduloHasUsuarioPK[ submoduloIdsubmodulo=" + submoduloIdsubmodulo + ", usuarioIdusuario=" + usuarioIdusuario + " ]";
    }
    
}
