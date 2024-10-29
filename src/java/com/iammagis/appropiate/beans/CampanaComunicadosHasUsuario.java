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
 * @author sebastianarizmendy
 */
@Entity
@Table(name = "campana_comunicados_has_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CampanaComunicadosHasUsuario.findAll", query = "SELECT c FROM CampanaComunicadosHasUsuario c")
    , @NamedQuery(name = "CampanaComunicadosHasUsuario.findByCampanaComunicadosIdcampana", query = "SELECT c FROM CampanaComunicadosHasUsuario c WHERE c.campanaComunicadosHasUsuarioPK.campanaComunicadosIdcampana = :campanaComunicadosIdcampana")
    , @NamedQuery(name = "CampanaComunicadosHasUsuario.findByUsuarioIdusuario", query = "SELECT c FROM CampanaComunicadosHasUsuario c WHERE c.campanaComunicadosHasUsuarioPK.usuarioIdusuario = :usuarioIdusuario")
    , @NamedQuery(name = "CampanaComunicadosHasUsuario.findByFecha", query = "SELECT c FROM CampanaComunicadosHasUsuario c WHERE c.fecha = :fecha")})
public class CampanaComunicadosHasUsuario implements Serializable {

    @Column(name = "respuesta")
    private Integer respuesta;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CampanaComunicadosHasUsuarioPK campanaComunicadosHasUsuarioPK;
    @Column(name = "fecha")
    private BigInteger fecha;
    @JoinColumn(name = "campana_comunicados_idcampana", referencedColumnName = "idcampana", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CampanaComunicados campanaComunicados;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public CampanaComunicadosHasUsuario() {
    }

    public CampanaComunicadosHasUsuario(CampanaComunicadosHasUsuarioPK campanaComunicadosHasUsuarioPK) {
        this.campanaComunicadosHasUsuarioPK = campanaComunicadosHasUsuarioPK;
    }

    public CampanaComunicadosHasUsuario(int campanaComunicadosIdcampana, int usuarioIdusuario) {
        this.campanaComunicadosHasUsuarioPK = new CampanaComunicadosHasUsuarioPK(campanaComunicadosIdcampana, usuarioIdusuario);
    }

    public CampanaComunicadosHasUsuarioPK getCampanaComunicadosHasUsuarioPK() {
        return campanaComunicadosHasUsuarioPK;
    }

    public void setCampanaComunicadosHasUsuarioPK(CampanaComunicadosHasUsuarioPK campanaComunicadosHasUsuarioPK) {
        this.campanaComunicadosHasUsuarioPK = campanaComunicadosHasUsuarioPK;
    }

    public BigInteger getFecha() {
        return fecha;
    }

    public void setFecha(BigInteger fecha) {
        this.fecha = fecha;
    }

    public CampanaComunicados getCampanaComunicados() {
        return campanaComunicados;
    }

    public void setCampanaComunicados(CampanaComunicados campanaComunicados) {
        this.campanaComunicados = campanaComunicados;
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
        hash += (campanaComunicadosHasUsuarioPK != null ? campanaComunicadosHasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CampanaComunicadosHasUsuario)) {
            return false;
        }
        CampanaComunicadosHasUsuario other = (CampanaComunicadosHasUsuario) object;
        if ((this.campanaComunicadosHasUsuarioPK == null && other.campanaComunicadosHasUsuarioPK != null) || (this.campanaComunicadosHasUsuarioPK != null && !this.campanaComunicadosHasUsuarioPK.equals(other.campanaComunicadosHasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario[ campanaComunicadosHasUsuarioPK=" + campanaComunicadosHasUsuarioPK + " ]";
    }

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }
    
}
