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
public class ModuloHasUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "modulo_idmodulo")
    private int moduloIdmodulo;
    @Basic(optional = false)
    @Column(name = "usuario_idusuario")
    private int usuarioIdusuario;

    public ModuloHasUsuarioPK() {
    }

    public ModuloHasUsuarioPK(int moduloIdmodulo, int usuarioIdusuario) {
        this.moduloIdmodulo = moduloIdmodulo;
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public int getModuloIdmodulo() {
        return moduloIdmodulo;
    }

    public void setModuloIdmodulo(int moduloIdmodulo) {
        this.moduloIdmodulo = moduloIdmodulo;
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
        hash += (int) moduloIdmodulo;
        hash += (int) usuarioIdusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModuloHasUsuarioPK)) {
            return false;
        }
        ModuloHasUsuarioPK other = (ModuloHasUsuarioPK) object;
        if (this.moduloIdmodulo != other.moduloIdmodulo) {
            return false;
        }
        if (this.usuarioIdusuario != other.usuarioIdusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.ModuloHasUsuarioPK[ moduloIdmodulo=" + moduloIdmodulo + ", usuarioIdusuario=" + usuarioIdusuario + " ]";
    }
    
}
