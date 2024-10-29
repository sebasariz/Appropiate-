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
@Table(name = "entidad_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadHasUsuario.findAll", query = "SELECT e FROM EntidadHasUsuario e"),
    @NamedQuery(name = "EntidadHasUsuario.findByEntidadIdentidad", query = "SELECT e FROM EntidadHasUsuario e WHERE e.entidadHasUsuarioPK.entidadIdentidad = :entidadIdentidad"),
    @NamedQuery(name = "EntidadHasUsuario.findByUsuarioIdusuario", query = "SELECT e FROM EntidadHasUsuario e WHERE e.entidadHasUsuarioPK.usuarioIdusuario = :usuarioIdusuario")})
public class EntidadHasUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntidadHasUsuarioPK entidadHasUsuarioPK;

    public EntidadHasUsuario() {
    }

    public EntidadHasUsuario(EntidadHasUsuarioPK entidadHasUsuarioPK) {
        this.entidadHasUsuarioPK = entidadHasUsuarioPK;
    }

    public EntidadHasUsuario(int entidadIdentidad, int usuarioIdusuario) {
        this.entidadHasUsuarioPK = new EntidadHasUsuarioPK(entidadIdentidad, usuarioIdusuario);
    }

    public EntidadHasUsuarioPK getEntidadHasUsuarioPK() {
        return entidadHasUsuarioPK;
    }

    public void setEntidadHasUsuarioPK(EntidadHasUsuarioPK entidadHasUsuarioPK) {
        this.entidadHasUsuarioPK = entidadHasUsuarioPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entidadHasUsuarioPK != null ? entidadHasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadHasUsuario)) {
            return false;
        }
        EntidadHasUsuario other = (EntidadHasUsuario) object;
        if ((this.entidadHasUsuarioPK == null && other.entidadHasUsuarioPK != null) || (this.entidadHasUsuarioPK != null && !this.entidadHasUsuarioPK.equals(other.entidadHasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.EntidadHasUsuario[ entidadHasUsuarioPK=" + entidadHasUsuarioPK + " ]";
    }
    
}
