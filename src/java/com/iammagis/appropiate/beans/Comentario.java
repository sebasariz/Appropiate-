/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
@Table(name = "comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Comentario.findByIdcomentarioPqr", query = "SELECT c FROM Comentario c WHERE c.idcomentarioPqr = :idcomentarioPqr")
    , @NamedQuery(name = "Comentario.findByFecha", query = "SELECT c FROM Comentario c WHERE c.fecha = :fecha")})
public class Comentario implements Serializable {
 
   
 
 
    @JoinColumn(name = "actividad_idactividad", referencedColumnName = "idactividad")
    @ManyToOne
    private Actividad actividadIdactividad;

    
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomentario_pqr")
    private Integer idcomentarioPqr;
    @Column(name = "fecha")
    private BigInteger fecha;
    @Lob
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "campana_comunicados_idcampana", referencedColumnName = "idcampana")
    @ManyToOne
    private CampanaComunicados campanaComunicadosIdcampana;
    @JoinColumn(name = "encuesta_idencuesta", referencedColumnName = "idencuesta")
    @ManyToOne
    private Encuesta encuestaIdencuesta;
    @JoinColumn(name = "evento_idevento", referencedColumnName = "idevento")
    @ManyToOne
    private Evento eventoIdevento;
    @JoinColumn(name = "pqr_idpqr", referencedColumnName = "idpqr")
    @ManyToOne
    private Pqr pqrIdpqr;

    public Comentario() {
    }

    public Comentario(Integer idcomentarioPqr) {
        this.idcomentarioPqr = idcomentarioPqr;
    }

    public Integer getIdcomentarioPqr() {
        return idcomentarioPqr;
    }

    public void setIdcomentarioPqr(Integer idcomentarioPqr) {
        this.idcomentarioPqr = idcomentarioPqr;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public String getComentario() throws UnsupportedEncodingException {
        return new String(comentario.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public CampanaComunicados getCampanaComunicadosIdcampana() {
        return campanaComunicadosIdcampana;
    }

    public void setCampanaComunicadosIdcampana(CampanaComunicados campanaComunicadosIdcampana) {
        this.campanaComunicadosIdcampana = campanaComunicadosIdcampana;
    }

    public Encuesta getEncuestaIdencuesta() {
        return encuestaIdencuesta;
    }

    public void setEncuestaIdencuesta(Encuesta encuestaIdencuesta) {
        this.encuestaIdencuesta = encuestaIdencuesta;
    }

    public Evento getEventoIdevento() {
        return eventoIdevento;
    }

    public void setEventoIdevento(Evento eventoIdevento) {
        this.eventoIdevento = eventoIdevento;
    }

    public Pqr getPqrIdpqr() {
        return pqrIdpqr;
    }

    public void setPqrIdpqr(Pqr pqrIdpqr) {
        this.pqrIdpqr = pqrIdpqr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomentarioPqr != null ? idcomentarioPqr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.idcomentarioPqr == null && other.idcomentarioPqr != null) || (this.idcomentarioPqr != null && !this.idcomentarioPqr.equals(other.idcomentarioPqr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Comentario[ idcomentarioPqr=" + idcomentarioPqr + " ]";
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }
 
    public Actividad getActividadIdactividad() {
        return actividadIdactividad;
    }

    public void setActividadIdactividad(Actividad actividadIdactividad) {
        this.actividadIdactividad = actividadIdactividad;
    }
 
    
}
