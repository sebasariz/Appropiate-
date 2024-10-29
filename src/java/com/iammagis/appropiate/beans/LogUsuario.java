/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
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
 * @author sebasariz
 
 * 1 login usuario
 * 2 log out usuario
 * 3 crear actividad
 * 4 editar actividad
 * 5 eliminar actividad
 * 6 crear campaña
 * 7 editar campaña
 * 8 eliminar campaña
 * 9 crear encuesta
 * 10 editar encuesta
 * 11 eliminar encuesta
 * 12 crear evento
 * 13 editar evento
 * 14 eliminar evento
 * 15 crear factura
 * 16 editar factura
 * 17 eliminar factura
 * 18 pagar factura
 * 19 crear comentario
 * 20 crear me interesa
 * 21 crear PQR
 * 22 crear reserva movil
 * 23 guardar respuestas
 * 24 login movile
 * 25 recuperar correo
 * 26 getCamapñas
 * 27 getComentarios
 * 28 getEncuestas
 * 29 getEventos
 * 30 obtener facturas
 * 31 cargar inicio
 * 32 obtener locaciones
 * 33 getPQRS
 * 34 getPreguntasEncuesta
 * 35 pagarFactura 
 * 36 getReservas
 * 37 getLocaciones
 * 38 remove reserva
 */



@Entity
@Table(name = "log_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogUsuario.findAll", query = "SELECT l FROM LogUsuario l"),
    @NamedQuery(name = "LogUsuario.findByIdlogUsuario", query = "SELECT l FROM LogUsuario l WHERE l.idlogUsuario = :idlogUsuario"),
    @NamedQuery(name = "LogUsuario.findByFecha", query = "SELECT l FROM LogUsuario l WHERE l.fecha = :fecha")})
public class LogUsuario implements Serializable {

    @Column(name = "tipo")
    private Integer tipo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlog_usuario")
    private Integer idlogUsuario;
    @Column(name = "fecha")
    private BigInteger fecha;
    @Lob
    @Column(name = "actividad")
    private String actividad;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    public LogUsuario() {
    }

    public LogUsuario(Integer idlogUsuario) {
        this.idlogUsuario = idlogUsuario;
    }

    public Integer getIdlogUsuario() {
        return idlogUsuario;
    }

    public void setIdlogUsuario(Integer idlogUsuario) {
        this.idlogUsuario = idlogUsuario;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
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
        hash += (idlogUsuario != null ? idlogUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogUsuario)) {
            return false;
        }
        LogUsuario other = (LogUsuario) object;
        if ((this.idlogUsuario == null && other.idlogUsuario != null) || (this.idlogUsuario != null && !this.idlogUsuario.equals(other.idlogUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.LogUsuario[ idlogUsuario=" + idlogUsuario + " ]";
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
}
