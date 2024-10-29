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
public class EventoHasUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "evento_idevento")
    private int eventoIdevento;
    @Basic(optional = false)
    @Column(name = "usuario_idusuario")
    private int usuarioIdusuario;

    public EventoHasUsuarioPK() {
    }

    public EventoHasUsuarioPK(int eventoIdevento, int usuarioIdusuario) {
        this.eventoIdevento = eventoIdevento;
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public int getEventoIdevento() {
        return eventoIdevento;
    }

    public void setEventoIdevento(int eventoIdevento) {
        this.eventoIdevento = eventoIdevento;
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
        hash += (int) eventoIdevento;
        hash += (int) usuarioIdusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoHasUsuarioPK)) {
            return false;
        }
        EventoHasUsuarioPK other = (EventoHasUsuarioPK) object;
        if (this.eventoIdevento != other.eventoIdevento) {
            return false;
        }
        if (this.usuarioIdusuario != other.usuarioIdusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.EventoHasUsuarioPK[ eventoIdevento=" + eventoIdevento + ", usuarioIdusuario=" + usuarioIdusuario + " ]";
    }
    
}
