/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r")
    , @NamedQuery(name = "Respuesta.findByIdrespuesta", query = "SELECT r FROM Respuesta r WHERE r.idrespuesta = :idrespuesta")})
public class Respuesta implements Serializable {
 
 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrespuesta")
    private Integer idrespuesta;
    @Lob
    @Column(name = "respuesta")
    private String respuesta;
    @JoinColumn(name = "pregunta_idpregunta", referencedColumnName = "idpregunta")
    @ManyToOne(optional = false)
    private Pregunta preguntaIdpregunta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    public Respuesta() {
    }

    public Respuesta(Integer idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public Integer getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(Integer idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getRespuesta() throws UnsupportedEncodingException {
        return new String(respuesta.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Pregunta getPreguntaIdpregunta() {
        return preguntaIdpregunta;
    }

    public void setPreguntaIdpregunta(Pregunta preguntaIdpregunta) {
        this.preguntaIdpregunta = preguntaIdpregunta;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrespuesta != null ? idrespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.idrespuesta == null && other.idrespuesta != null) || (this.idrespuesta != null && !this.idrespuesta.equals(other.idrespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Respuesta[ idrespuesta=" + idrespuesta + " ]";
    }
 
 
}
