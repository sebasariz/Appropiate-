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
 * @author sebasariz
 */
@Entity
@Table(name = "encuesta_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncuestaHasUsuario.findAll", query = "SELECT e FROM EncuestaHasUsuario e"),
    @NamedQuery(name = "EncuestaHasUsuario.findByEncuestaIdencuesta", query = "SELECT e FROM EncuestaHasUsuario e WHERE e.encuestaHasUsuarioPK.encuestaIdencuesta = :encuestaIdencuesta"),
    @NamedQuery(name = "EncuestaHasUsuario.findByUsuarioIdusuario", query = "SELECT e FROM EncuestaHasUsuario e WHERE e.encuestaHasUsuarioPK.usuarioIdusuario = :usuarioIdusuario"),
    @NamedQuery(name = "EncuestaHasUsuario.findByFecha", query = "SELECT e FROM EncuestaHasUsuario e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "EncuestaHasUsuario.findByRespuesta", query = "SELECT e FROM EncuestaHasUsuario e WHERE e.respuesta = :respuesta")})
public class EncuestaHasUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncuestaHasUsuarioPK encuestaHasUsuarioPK;
    @Column(name = "fecha")
    private BigInteger fecha;
    @Column(name = "respuesta")
    private Integer respuesta;
    @JoinColumn(name = "encuesta_idencuesta", referencedColumnName = "idencuesta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Encuesta encuesta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public EncuestaHasUsuario() {
    }

    public EncuestaHasUsuario(EncuestaHasUsuarioPK encuestaHasUsuarioPK) {
        this.encuestaHasUsuarioPK = encuestaHasUsuarioPK;
    }

    public EncuestaHasUsuario(int encuestaIdencuesta, int usuarioIdusuario) {
        this.encuestaHasUsuarioPK = new EncuestaHasUsuarioPK(encuestaIdencuesta, usuarioIdusuario);
    }

    public EncuestaHasUsuarioPK getEncuestaHasUsuarioPK() {
        return encuestaHasUsuarioPK;
    }

    public void setEncuestaHasUsuarioPK(EncuestaHasUsuarioPK encuestaHasUsuarioPK) {
        this.encuestaHasUsuarioPK = encuestaHasUsuarioPK;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
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
        hash += (encuestaHasUsuarioPK != null ? encuestaHasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncuestaHasUsuario)) {
            return false;
        }
        EncuestaHasUsuario other = (EncuestaHasUsuario) object;
        if ((this.encuestaHasUsuarioPK == null && other.encuestaHasUsuarioPK != null) || (this.encuestaHasUsuarioPK != null && !this.encuestaHasUsuarioPK.equals(other.encuestaHasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.EncuestaHasUsuario[ encuestaHasUsuarioPK=" + encuestaHasUsuarioPK + " ]";
    }
    
}
