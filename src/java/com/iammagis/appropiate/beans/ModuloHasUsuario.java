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
@Table(name = "modulo_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModuloHasUsuario.findAll", query = "SELECT m FROM ModuloHasUsuario m"),
    @NamedQuery(name = "ModuloHasUsuario.findByModuloIdmodulo", query = "SELECT m FROM ModuloHasUsuario m WHERE m.moduloHasUsuarioPK.moduloIdmodulo = :moduloIdmodulo"),
    @NamedQuery(name = "ModuloHasUsuario.findByUsuarioIdusuario", query = "SELECT m FROM ModuloHasUsuario m WHERE m.moduloHasUsuarioPK.usuarioIdusuario = :usuarioIdusuario")})
public class ModuloHasUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ModuloHasUsuarioPK moduloHasUsuarioPK;
    @JoinColumn(name = "modulo_idmodulo", referencedColumnName = "idmodulo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modulo modulo;

    public ModuloHasUsuario() {
    }

    public ModuloHasUsuario(ModuloHasUsuarioPK moduloHasUsuarioPK) {
        this.moduloHasUsuarioPK = moduloHasUsuarioPK;
    }

    public ModuloHasUsuario(int moduloIdmodulo, int usuarioIdusuario) {
        this.moduloHasUsuarioPK = new ModuloHasUsuarioPK(moduloIdmodulo, usuarioIdusuario);
    }

    public ModuloHasUsuarioPK getModuloHasUsuarioPK() {
        return moduloHasUsuarioPK;
    }

    public void setModuloHasUsuarioPK(ModuloHasUsuarioPK moduloHasUsuarioPK) {
        this.moduloHasUsuarioPK = moduloHasUsuarioPK;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduloHasUsuarioPK != null ? moduloHasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModuloHasUsuario)) {
            return false;
        }
        ModuloHasUsuario other = (ModuloHasUsuario) object;
        if ((this.moduloHasUsuarioPK == null && other.moduloHasUsuarioPK != null) || (this.moduloHasUsuarioPK != null && !this.moduloHasUsuarioPK.equals(other.moduloHasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.ModuloHasUsuario[ moduloHasUsuarioPK=" + moduloHasUsuarioPK + " ]";
    }
    
}
