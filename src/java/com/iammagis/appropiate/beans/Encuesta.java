/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encuesta.findAll", query = "SELECT e FROM Encuesta e")
    , @NamedQuery(name = "Encuesta.findByIdencuesta", query = "SELECT e FROM Encuesta e WHERE e.idencuesta = :idencuesta")
    , @NamedQuery(name = "Encuesta.findByNombre", query = "SELECT e FROM Encuesta e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Encuesta.findByEstado", query = "SELECT e FROM Encuesta e WHERE e.estado = :estado")})
public class Encuesta extends ActionForm implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuesta")
    private Collection<EncuestaHasUsuario> encuestaHasUsuarioCollection;

    @JoinTable(name = "encuesta_has_grupo_interes", joinColumns = {
        @JoinColumn(name = "encuesta_idencuesta", referencedColumnName = "idencuesta")}, inverseJoinColumns = {
        @JoinColumn(name = "grupo_interes_idgrupo_interes", referencedColumnName = "idgrupo_interes")})
    @ManyToMany
    private Collection<GrupoInteres> grupoInteresCollection;

     
 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idencuesta")
    private Integer idencuesta;
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @OneToMany(mappedBy = "encuestaIdencuesta")
    private Collection<Comentario> comentarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encuestaIdencuesta")
    private Collection<Pregunta> preguntaCollection;
    @Transient
    FormFile foto;
    @Transient
    String camposJson;
    @Transient
    int idEntidad;
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
    
    

    public String getCamposJson() throws UnsupportedEncodingException {
        return new String(camposJson.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setCamposJson(String camposJson) {
        this.camposJson = camposJson;
    }
    
    

    public FormFile getFoto() {
        return foto;
    }

    public void setFoto(FormFile foto) {
        this.foto = foto;
    }
    
    

    public Encuesta() {
    }

    public Encuesta(Integer idencuesta) {
        this.idencuesta = idencuesta;
    }

    public Integer getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(Integer idencuesta) {
        this.idencuesta = idencuesta;
    }

    public String getNombre() throws UnsupportedEncodingException {
        return new String(nombre.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() throws UnsupportedEncodingException {
        return new String(imagen.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    @XmlTransient
    public Collection<Pregunta> getPreguntaCollection() {
        return preguntaCollection;
    }

    public void setPreguntaCollection(Collection<Pregunta> preguntaCollection) {
        this.preguntaCollection = preguntaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idencuesta != null ? idencuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encuesta)) {
            return false;
        }
        Encuesta other = (Encuesta) object;
        if ((this.idencuesta == null && other.idencuesta != null) || (this.idencuesta != null && !this.idencuesta.equals(other.idencuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Encuesta[ idencuesta=" + idencuesta + " ]";
    }
 
    @XmlTransient
    public Collection<GrupoInteres> getGrupoInteresCollection() {
        return grupoInteresCollection;
    }

    public void setGrupoInteresCollection(Collection<GrupoInteres> grupoInteresCollection) {
        this.grupoInteresCollection = grupoInteresCollection;
    }

    @XmlTransient
    public Collection<EncuestaHasUsuario> getEncuestaHasUsuarioCollection() {
        return encuestaHasUsuarioCollection;
    }

    public void setEncuestaHasUsuarioCollection(Collection<EncuestaHasUsuario> encuestaHasUsuarioCollection) {
        this.encuestaHasUsuarioCollection = encuestaHasUsuarioCollection;
    }
 
}
