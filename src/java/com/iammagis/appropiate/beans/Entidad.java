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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author sebasariz
 */
@Entity
@Table(name = "entidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e"),
    @NamedQuery(name = "Entidad.findByIdentidad", query = "SELECT e FROM Entidad e WHERE e.identidad = :identidad"),
    @NamedQuery(name = "Entidad.findByNombre", query = "SELECT e FROM Entidad e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Entidad.findByContacto", query = "SELECT e FROM Entidad e WHERE e.contacto = :contacto"),
    @NamedQuery(name = "Entidad.findByDireccion", query = "SELECT e FROM Entidad e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Entidad.findByLat", query = "SELECT e FROM Entidad e WHERE e.lat = :lat"),
    @NamedQuery(name = "Entidad.findByLng", query = "SELECT e FROM Entidad e WHERE e.lng = :lng"),
    @NamedQuery(name = "Entidad.findByNit", query = "SELECT e FROM Entidad e WHERE e.nit = :nit"),
    @NamedQuery(name = "Entidad.findByTelefono", query = "SELECT e FROM Entidad e WHERE e.telefono = :telefono")})
public class Entidad extends ActionForm implements Serializable {

    @Column(name = "reservas")
    private Integer reservas;

    @Column(name = "pasarela")
    private Integer pasarela;
    @Lob
    @Column(name = "campos_pasarela")
    private String camposPasarela;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identidad")
    private Integer identidad;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contacto")
    private String contacto;
    @Column(name = "direccion")
    private String direccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
    @Column(name = "nit")
    private String nit;
    @Column(name = "telefono")
    private String telefono;
    @Lob
    @Column(name = "identificador")
    private String identificador;
    @Lob
    @Column(name = "correo")
    private String correo;
    @Lob
    @Column(name = "modulos")
    private String modulos;
    @JoinTable(name = "entidad_has_usuario", joinColumns = {
        @JoinColumn(name = "entidad_identidad", referencedColumnName = "identidad")}, inverseJoinColumns = {
        @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<FacturaTemplate> facturaTemplateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<Locacion> locacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<Encuesta> encuestaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<CampanaComunicados> campanaComunicadosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<Actividad> actividadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<Pqr> pqrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<Evento> eventoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<Factura> facturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadIdentidad")
    private Collection<GrupoInteres> grupoInteresCollection;

    public Entidad() {
    }

    public Entidad(Integer identidad) {
        this.identidad = identidad;
    }

    public Integer getIdentidad() {
        return identidad;
    }

    public void setIdentidad(Integer identidad) {
        this.identidad = identidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        System.out.println("setting: " + nombre);
        this.nombre = nombre;
    }

    public String getContacto() throws UnsupportedEncodingException {
        return new String(contacto.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() throws UnsupportedEncodingException {
        return new String(direccion.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getCorreo() throws UnsupportedEncodingException {
        return new String(correo.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getModulos() {
        return modulos;
    }

    public void setModulos(String modulos) {
        this.modulos = modulos;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @XmlTransient
    public Collection<FacturaTemplate> getFacturaTemplateCollection() {
        return facturaTemplateCollection;
    }

    public void setFacturaTemplateCollection(Collection<FacturaTemplate> facturaTemplateCollection) {
        this.facturaTemplateCollection = facturaTemplateCollection;
    }

    @XmlTransient
    public Collection<Locacion> getLocacionCollection() {
        return locacionCollection;
    }

    public void setLocacionCollection(Collection<Locacion> locacionCollection) {
        this.locacionCollection = locacionCollection;
    }

    @XmlTransient
    public Collection<Encuesta> getEncuestaCollection() {
        return encuestaCollection;
    }

    public void setEncuestaCollection(Collection<Encuesta> encuestaCollection) {
        this.encuestaCollection = encuestaCollection;
    }

    @XmlTransient
    public Collection<CampanaComunicados> getCampanaComunicadosCollection() {
        return campanaComunicadosCollection;
    }

    public void setCampanaComunicadosCollection(Collection<CampanaComunicados> campanaComunicadosCollection) {
        this.campanaComunicadosCollection = campanaComunicadosCollection;
    }

    @XmlTransient
    public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    @XmlTransient
    public Collection<Pqr> getPqrCollection() {
        return pqrCollection;
    }

    public void setPqrCollection(Collection<Pqr> pqrCollection) {
        this.pqrCollection = pqrCollection;
    }

    @XmlTransient
    public Collection<Evento> getEventoCollection() {
        return eventoCollection;
    }

    public void setEventoCollection(Collection<Evento> eventoCollection) {
        this.eventoCollection = eventoCollection;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @XmlTransient
    public Collection<GrupoInteres> getGrupoInteresCollection() {
        return grupoInteresCollection;
    }

    public void setGrupoInteresCollection(Collection<GrupoInteres> grupoInteresCollection) {
        this.grupoInteresCollection = grupoInteresCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identidad != null ? identidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.identidad == null && other.identidad != null) || (this.identidad != null && !this.identidad.equals(other.identidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Integer getPasarela() {
        return pasarela;
    }

    public void setPasarela(Integer pasarela) {
        this.pasarela = pasarela;
    }

    public String getCamposPasarela() {
        return camposPasarela;
    }

    public void setCamposPasarela(String camposPasarela) {
        this.camposPasarela = camposPasarela;
    }

    public Integer getReservas() {
        return reservas;
    }

    public void setReservas(Integer reservas) {
        this.reservas = reservas;
    }

}
