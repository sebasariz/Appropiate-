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
 * @author sebastianarizmendy
 */
@Embeddable
public class CampanaComunicadosHasUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "campana_comunicados_idcampana")
    private int campanaComunicadosIdcampana;
    @Basic(optional = false)
    @Column(name = "usuario_idusuario")
    private int usuarioIdusuario;

    public CampanaComunicadosHasUsuarioPK() {
    }

    public CampanaComunicadosHasUsuarioPK(int campanaComunicadosIdcampana, int usuarioIdusuario) {
        this.campanaComunicadosIdcampana = campanaComunicadosIdcampana;
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public int getCampanaComunicadosIdcampana() {
        return campanaComunicadosIdcampana;
    }

    public void setCampanaComunicadosIdcampana(int campanaComunicadosIdcampana) {
        this.campanaComunicadosIdcampana = campanaComunicadosIdcampana;
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
        hash += (int) campanaComunicadosIdcampana;
        hash += (int) usuarioIdusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CampanaComunicadosHasUsuarioPK)) {
            return false;
        }
        CampanaComunicadosHasUsuarioPK other = (CampanaComunicadosHasUsuarioPK) object;
        if (this.campanaComunicadosIdcampana != other.campanaComunicadosIdcampana) {
            return false;
        }
        if (this.usuarioIdusuario != other.usuarioIdusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.CampanaComunicadosHasUsuarioPK[ campanaComunicadosIdcampana=" + campanaComunicadosIdcampana + ", usuarioIdusuario=" + usuarioIdusuario + " ]";
    }
    
}
