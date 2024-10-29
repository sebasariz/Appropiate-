/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
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
 * @author sebastianarizmendy
 */
@Entity
@Table(name = "evento_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoHasUsuario.findAll", query = "SELECT e FROM EventoHasUsuario e")
    , @NamedQuery(name = "EventoHasUsuario.findByEventoIdevento", query = "SELECT e FROM EventoHasUsuario e WHERE e.eventoHasUsuarioPK.eventoIdevento = :eventoIdevento")
    , @NamedQuery(name = "EventoHasUsuario.findByUsuarioIdusuario", query = "SELECT e FROM EventoHasUsuario e WHERE e.eventoHasUsuarioPK.usuarioIdusuario = :usuarioIdusuario")
    , @NamedQuery(name = "EventoHasUsuario.findByFecha", query = "SELECT e FROM EventoHasUsuario e WHERE e.fecha = :fecha")})
public class EventoHasUsuario implements Serializable {

    @Column(name = "respuesta")
    private Integer respuesta;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EventoHasUsuarioPK eventoHasUsuarioPK;
    @Column(name = "fecha")
    private BigInteger fecha;
    @JoinColumn(name = "evento_idevento", referencedColumnName = "idevento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evento evento;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public EventoHasUsuario() {
    }

    public EventoHasUsuario(EventoHasUsuarioPK eventoHasUsuarioPK) {
        this.eventoHasUsuarioPK = eventoHasUsuarioPK;
    }

    public EventoHasUsuario(int eventoIdevento, int usuarioIdusuario) {
        this.eventoHasUsuarioPK = new EventoHasUsuarioPK(eventoIdevento, usuarioIdusuario);
    }

    public EventoHasUsuarioPK getEventoHasUsuarioPK() {
        return eventoHasUsuarioPK;
    }

    public void setEventoHasUsuarioPK(EventoHasUsuarioPK eventoHasUsuarioPK) {
        this.eventoHasUsuarioPK = eventoHasUsuarioPK;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventoHasUsuarioPK != null ? eventoHasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoHasUsuario)) {
            return false;
        }
        EventoHasUsuario other = (EventoHasUsuario) object;
        if ((this.eventoHasUsuarioPK == null && other.eventoHasUsuarioPK != null) || (this.eventoHasUsuarioPK != null && !this.eventoHasUsuarioPK.equals(other.eventoHasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.EventoHasUsuario[ eventoHasUsuarioPK=" + eventoHasUsuarioPK + " ]";
    }

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }
    
}
