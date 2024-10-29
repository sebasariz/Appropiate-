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

/**
 *
 * @author sebastianarizmendy
 */
@Entity
@Table(name = "campana_comunicados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CampanaComunicados.findAll", query = "SELECT c FROM CampanaComunicados c")
    , @NamedQuery(name = "CampanaComunicados.findByIdcampana", query = "SELECT c FROM CampanaComunicados c WHERE c.idcampana = :idcampana")
    , @NamedQuery(name = "CampanaComunicados.findByNombre", query = "SELECT c FROM CampanaComunicados c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CampanaComunicados.findByFecha", query = "SELECT c FROM CampanaComunicados c WHERE c.fecha = :fecha")})
public class CampanaComunicados extends ActionForm implements Serializable {

   

    @Lob
    @Column(name = "informacion")
    private String informacion;

    @OneToMany(mappedBy = "campanaComunicadosIdcampana")
    private Collection<Comentario> comentarioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcampana")
    private Integer idcampana;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    private BigInteger fecha;
    @OneToMany(mappedBy = "campanaComunicadosIdcampana")
    private Collection<Archivo> archivoCollection;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campanaComunicados")
    private Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollection;
    
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
    
    

    public CampanaComunicados() {
    }

    public CampanaComunicados(Integer idcampana) {
        this.idcampana = idcampana;
    }

    public Integer getIdcampana() {
        return idcampana;
    }

    public void setIdcampana(Integer idcampana) {
        this.idcampana = idcampana;
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
    public Collection<CampanaComunicadosHasUsuario> getCampanaComunicadosHasUsuarioCollection() {
        return campanaComunicadosHasUsuarioCollection;
    }

    public void setCampanaComunicadosHasUsuarioCollection(Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollection) {
        this.campanaComunicadosHasUsuarioCollection = campanaComunicadosHasUsuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcampana != null ? idcampana.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CampanaComunicados)) {
            return false;
        }
        CampanaComunicados other = (CampanaComunicados) object;
        if ((this.idcampana == null && other.idcampana != null) || (this.idcampana != null && !this.idcampana.equals(other.idcampana))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.CampanaComunicados[ idcampana=" + idcampana + " ]";
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    public String getInformacion() throws UnsupportedEncodingException {
        return new String(informacion.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
 
 
    
}
