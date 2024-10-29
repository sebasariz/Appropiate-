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
public class UsuarioHasGrupoInteresPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "usuario_idusuario")
    private int usuarioIdusuario;
    @Basic(optional = false)
    @Column(name = "grupo_interes_idgrupo_interes")
    private int grupoInteresIdgrupoInteres;

    public UsuarioHasGrupoInteresPK() {
    }

    public UsuarioHasGrupoInteresPK(int usuarioIdusuario, int grupoInteresIdgrupoInteres) {
        this.usuarioIdusuario = usuarioIdusuario;
        this.grupoInteresIdgrupoInteres = grupoInteresIdgrupoInteres;
    }

    public int getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(int usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public int getGrupoInteresIdgrupoInteres() {
        return grupoInteresIdgrupoInteres;
    }

    public void setGrupoInteresIdgrupoInteres(int grupoInteresIdgrupoInteres) {
        this.grupoInteresIdgrupoInteres = grupoInteresIdgrupoInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioIdusuario;
        hash += (int) grupoInteresIdgrupoInteres;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioHasGrupoInteresPK)) {
            return false;
        }
        UsuarioHasGrupoInteresPK other = (UsuarioHasGrupoInteresPK) object;
        if (this.usuarioIdusuario != other.usuarioIdusuario) {
            return false;
        }
        if (this.grupoInteresIdgrupoInteres != other.grupoInteresIdgrupoInteres) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.UsuarioHasGrupoInteresPK[ usuarioIdusuario=" + usuarioIdusuario + ", grupoInteresIdgrupoInteres=" + grupoInteresIdgrupoInteres + " ]";
    }
    
}
