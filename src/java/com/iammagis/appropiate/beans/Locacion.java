/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
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
 * @author sebasariz
 */
@Entity
@Table(name = "locacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locacion.findAll", query = "SELECT l FROM Locacion l"),
    @NamedQuery(name = "Locacion.findByIdlocacion", query = "SELECT l FROM Locacion l WHERE l.idlocacion = :idlocacion")})
public class Locacion extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlocacion")
    private Integer idlocacion;
    @Lob
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "imagen")
    private String imagen;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Column(name = "responsable")
    private String responsable;
    @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")
    @ManyToOne(optional = false)
    private Entidad entidadIdentidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locacionIdlocacion")
    private Collection<Reserva> reservaCollection;
    @Transient
    FormFile imagenform;

    public FormFile getImagenform() {
        return imagenform;
    }

    public void setImagenform(FormFile imagenform) {
        this.imagenform = imagenform;
    }
    
    
    public Locacion() {
    }

    public Locacion(Integer idlocacion) {
        this.idlocacion = idlocacion;
    }

    public Integer getIdlocacion() {
        return idlocacion;
    }

    public void setIdlocacion(Integer idlocacion) {
        this.idlocacion = idlocacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Entidad getEntidadIdentidad() {
        return entidadIdentidad;
    }

    public void setEntidadIdentidad(Entidad entidadIdentidad) {
        this.entidadIdentidad = entidadIdentidad;
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlocacion != null ? idlocacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locacion)) {
            return false;
        }
        Locacion other = (Locacion) object;
        if ((this.idlocacion == null && other.idlocacion != null) || (this.idlocacion != null && !this.idlocacion.equals(other.idlocacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Locacion[ idlocacion=" + idlocacion + " ]";
    }
    
}
