/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author sebastianarizmendy
 */
@Entity
@Table(name = "evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findByIdevento", query = "SELECT e FROM Evento e WHERE e.idevento = :idevento")
    , @NamedQuery(name = "Evento.findByNombre", query = "SELECT e FROM Evento e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Evento.findByFecha", query = "SELECT e FROM Evento e WHERE e.fecha = :fecha")})
public class Evento extends ActionForm implements Serializable {
 
 
    @Column(name = "informacion")
    private String informacion;

    @Column(name = "estado")
    private Integer estado;

    @OneToMany(mappedBy = "eventoIdevento")
    private Collection<Comentario> comentarioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idevento")
    private Integer idevento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    private BigInteger fecha; 
    @Lob
    @Column(name = "imagen")
    private String imagen;
    @OneToMany(mappedBy = "eventoIdevento")
    private Collection<Archivo> archivoCollection;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private Collection<EventoHasUsuario> eventoHasUsuarioCollection;
    @Transient
    int idEntidad; 
    @Transient
    FormFile imagenform;
    @Column(name = "idparaString")
    String idparaString;

    public String getIdparaString() {
        return idparaString;
    }

    public void setIdparaString(String idparaString) {
        this.idparaString = idparaString;
    }
    
    
    
    
     public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public FormFile getImagenform() {
        return imagenform;
    }

    public void setImagenform(FormFile imagenform) {
        this.imagenform = imagenform;
    }
    
    public Evento() {
    }

    public Evento(Integer idevento) {
        this.idevento = idevento;
    }

    public Integer getIdevento() {
        return idevento;
    }

    public void setIdevento(Integer idevento) {
        this.idevento = idevento;
    }

    public String getNombre() throws UnsupportedEncodingException {
        return new String(nombre.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public String getImagen() throws UnsupportedEncodingException {
        return new String(imagen.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @XmlTransient
    public Collection<Archivo> getArchivoCollection() {
        return archivoCollection;
    }

    public void setArchivoCollection(Collection<Archivo> archivoCollection) {
        this.archivoCollection = archivoCollection;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    @XmlTransient
    public Collection<EventoHasUsuario> getEventoHasUsuarioCollection() {
        return eventoHasUsuarioCollection;
    }

    public void setEventoHasUsuarioCollection(Collection<EventoHasUsuario> eventoHasUsuarioCollection) {
        this.eventoHasUsuarioCollection = eventoHasUsuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idevento != null ? idevento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idevento == null && other.idevento != null) || (this.idevento != null && !this.idevento.equals(other.idevento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Evento[ idevento=" + idevento + " ]";
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getInformacion() throws UnsupportedEncodingException {
        return new String(informacion.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    } 
 
 
}
