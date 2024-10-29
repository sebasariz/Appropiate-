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
public class EntidadHasUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "entidad_identidad")
    private int entidadIdentidad;
    @Basic(optional = false)
    @Column(name = "usuario_idusuario")
    private int usuarioIdusuario;

    public EntidadHasUsuarioPK() {
    }

    public EntidadHasUsuarioPK(int entidadIdentidad, int usuarioIdusuario) {
        this.entidadIdentidad = entidadIdentidad;
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public int getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(int entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
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
        hash += (int) entidadIdentidad;
        hash += (int) usuarioIdusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadHasUsuarioPK)) {
            return false;
        }
        EntidadHasUsuarioPK other = (EntidadHasUsuarioPK) object;
        if (this.entidadIdentidad != other.entidadIdentidad) {
            return false;
        }
        if (this.usuarioIdusuario != other.usuarioIdusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.EntidadHasUsuarioPK[ entidadIdentidad=" + entidadIdentidad + ", usuarioIdusuario=" + usuarioIdusuario + " ]";
    }
    
}
