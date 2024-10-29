/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sebasariz
 */
@Entity
@Table(name = "usuario_has_grupo_interes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioHasGrupoInteres.findAll", query = "SELECT u FROM UsuarioHasGrupoInteres u"),
    @NamedQuery(name = "UsuarioHasGrupoInteres.findByUsuarioIdusuario", query = "SELECT u FROM UsuarioHasGrupoInteres u WHERE u.usuarioHasGrupoInteresPK.usuarioIdusuario = :usuarioIdusuario"),
    @NamedQuery(name = "UsuarioHasGrupoInteres.findByGrupoInteresIdgrupoInteres", query = "SELECT u FROM UsuarioHasGrupoInteres u WHERE u.usuarioHasGrupoInteresPK.grupoInteresIdgrupoInteres = :grupoInteresIdgrupoInteres")})
public class UsuarioHasGrupoInteres implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioHasGrupoInteresPK usuarioHasGrupoInteresPK;

    public UsuarioHasGrupoInteres() {
    }

    public UsuarioHasGrupoInteres(UsuarioHasGrupoInteresPK usuarioHasGrupoInteresPK) {
        this.usuarioHasGrupoInteresPK = usuarioHasGrupoInteresPK;
    }

    public UsuarioHasGrupoInteres(int usuarioIdusuario, int grupoInteresIdgrupoInteres) {
        this.usuarioHasGrupoInteresPK = new UsuarioHasGrupoInteresPK(usuarioIdusuario, grupoInteresIdgrupoInteres);
    }

    public UsuarioHasGrupoInteresPK getUsuarioHasGrupoInteresPK() {
        return usuarioHasGrupoInteresPK;
    }

    public void setUsuarioHasGrupoInteresPK(UsuarioHasGrupoInteresPK usuarioHasGrupoInteresPK) {
        this.usuarioHasGrupoInteresPK = usuarioHasGrupoInteresPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioHasGrupoInteresPK != null ? usuarioHasGrupoInteresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioHasGrupoInteres)) {
            return false;
        }
        UsuarioHasGrupoInteres other = (UsuarioHasGrupoInteres) object;
        if ((this.usuarioHasGrupoInteresPK == null && other.usuarioHasGrupoInteresPK != null) || (this.usuarioHasGrupoInteresPK != null && !this.usuarioHasGrupoInteresPK.equals(other.usuarioHasGrupoInteresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.UsuarioHasGrupoInteres[ usuarioHasGrupoInteresPK=" + usuarioHasGrupoInteresPK + " ]";
    }
    
}
