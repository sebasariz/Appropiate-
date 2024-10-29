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

/**
 *
 * @author sebastianarizmendy
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdusuario", query = "SELECT u FROM Usuario u WHERE u.idusuario = :idusuario")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByPass", query = "SELECT u FROM Usuario u WHERE u.pass = :pass")
    , @NamedQuery(name = "Usuario.login", query = "SELECT u FROM Usuario u WHERE u.correo = :correo AND u.pass = :pass") 
    , @NamedQuery(name = "Usuario.sessionToken", query = "SELECT u FROM Usuario u WHERE u.sessionToken = :sessionToken")
    , @NamedQuery(name = "Usuario.findByImagen", query = "SELECT u FROM Usuario u WHERE u.imagen = :imagen")})
public class Usuario extends ActionForm implements Serializable {
 

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private Collection<LogUsuario> logUsuarioCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<EncuestaHasUsuario> encuestaHasUsuarioCollection;

    

    @ManyToMany(mappedBy = "usuarioCollection")
    private Collection<GrupoInteres> grupoInteresCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private Collection<Factura> facturaCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private Collection<Reserva> reservaCollection;
 
    @Lob
    @Column(name = "devices_tokens")
    private String devicesTokens;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private Collection<Comentario> comentarioCollection;

    @Lob
    @Column(name = "sessionToken")
    private String sessionToken;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Integer idusuario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "pass")
    private String pass;
    @Column(name = "imagen")
    private String imagen;
    @ManyToMany(mappedBy = "usuarioCollection")
    private Collection<Modulo> moduloCollection;
    @ManyToMany(mappedBy = "usuarioCollection")
    private Collection<Submodulo> submoduloCollection;
    @ManyToMany(mappedBy = "usuarioCollection")
    private Collection<Entidad> entidadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private Collection<Pqr> pqrCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<EventoHasUsuario> eventoHasUsuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollection;
    @JoinColumn(name = "tipo_usuario_idtipo_usuario", referencedColumnName = "idtipo_usuario")
    @ManyToOne(optional = false)
    private TipoUsuario tipoUsuarioIdtipoUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioIdusuario")
    private Collection<Respuesta> respuestaCollection;
    @Transient
    String idEntidad;
    @Transient
    int idTipoUsuario;
    @Transient 
    String idEntidadString;
    
    
    

    public String getIdEntidadString() {
        return idEntidadString;
    }

    public void setIdEntidadString(String idEntidadString) {
        this.idEntidadString = idEntidadString;
    }
    
    

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Usuario() {
    }

    public Usuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() throws UnsupportedEncodingException {
        return new String(nombre.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() throws UnsupportedEncodingException {
        return new String(apellidos.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() throws UnsupportedEncodingException {
        return new String(correo.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() throws UnsupportedEncodingException {
        return new String(pass.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImagen() throws UnsupportedEncodingException {
        return new String(imagen.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @XmlTransient
    public Collection<Modulo> getModuloCollection() {
        return moduloCollection;
    }

    public void setModuloCollection(Collection<Modulo> moduloCollection) {
        this.moduloCollection = moduloCollection;
    }

    @XmlTransient
    public Collection<Submodulo> getSubmoduloCollection() {
        return submoduloCollection;
    }

    public void setSubmoduloCollection(Collection<Submodulo> submoduloCollection) {
        this.submoduloCollection = submoduloCollection;
    }

    @XmlTransient
    public Collection<Entidad> getEntidadCollection() {
        return entidadCollection;
    }

    public void setEntidadCollection(Collection<Entidad> entidadCollection) {
        this.entidadCollection = entidadCollection;
    }

    @XmlTransient
    public Collection<Pqr> getPqrCollection() {
        return pqrCollection;
    }

    public void setPqrCollection(Collection<Pqr> pqrCollection) {
        this.pqrCollection = pqrCollection;
    }

    @XmlTransient
    public Collection<EventoHasUsuario> getEventoHasUsuarioCollection() {
        return eventoHasUsuarioCollection;
    }

    public void setEventoHasUsuarioCollection(Collection<EventoHasUsuario> eventoHasUsuarioCollection) {
        this.eventoHasUsuarioCollection = eventoHasUsuarioCollection;
    }

    @XmlTransient
    public Collection<CampanaComunicadosHasUsuario> getCampanaComunicadosHasUsuarioCollection() {
        return campanaComunicadosHasUsuarioCollection;
    }

    public void setCampanaComunicadosHasUsuarioCollection(Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollection) {
        this.campanaComunicadosHasUsuarioCollection = campanaComunicadosHasUsuarioCollection;
    }

    public TipoUsuario getTipoUsuarioIdtipoUsuario() {
        return tipoUsuarioIdtipoUsuario;
    }

    public void setTipoUsuarioIdtipoUsuario(TipoUsuario tipoUsuarioIdtipoUsuario) {
        this.tipoUsuarioIdtipoUsuario = tipoUsuarioIdtipoUsuario;
    }

    @XmlTransient
    public Collection<Respuesta> getRespuestaCollection() {
        return respuestaCollection;
    }

    public void setRespuestaCollection(Collection<Respuesta> respuestaCollection) {
        this.respuestaCollection = respuestaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Usuario[ idusuario=" + idusuario + " ]";
    }

    public String getSessionToken() throws UnsupportedEncodingException {
        return new String(sessionToken.getBytes("ISO8859-1"), "UTF-8");
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    public String getDevicesTokens() throws UnsupportedEncodingException {
        return devicesTokens;
    }

    public void setDevicesTokens(String devicesTokens) {
        this.devicesTokens = devicesTokens;
    }
 

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
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
 

    @XmlTransient
    public Collection<EncuestaHasUsuario> getEncuestaHasUsuarioCollection() {
        return encuestaHasUsuarioCollection;
    }

    public void setEncuestaHasUsuarioCollection(Collection<EncuestaHasUsuario> encuestaHasUsuarioCollection) {
        this.encuestaHasUsuarioCollection = encuestaHasUsuarioCollection;
    }

    @XmlTransient
    public Collection<LogUsuario> getLogUsuarioCollection() {
        return logUsuarioCollection;
    }

    public void setLogUsuarioCollection(Collection<LogUsuario> logUsuarioCollection) {
        this.logUsuarioCollection = logUsuarioCollection;
    }
 
}
