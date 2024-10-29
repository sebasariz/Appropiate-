/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.beans;

import java.io.Serializable;
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
@Table(name = "archivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivo.findAll", query = "SELECT a FROM Archivo a")
    , @NamedQuery(name = "Archivo.findByIdarchivo", query = "SELECT a FROM Archivo a WHERE a.idarchivo = :idarchivo")})
public class Archivo implements Serializable {

    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "tipo_string")
    private String tipoString;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarchivo")
    private Integer idarchivo;
    @Lob
    @Column(name = "ruta")
    private String ruta;
    @JoinColumn(name = "campana_comunicados_idcampana", referencedColumnName = "idcampana")
    @ManyToOne
    private CampanaComunicados campanaComunicadosIdcampana;
    @JoinColumn(name = "evento_idevento", referencedColumnName = "idevento")
    @ManyToOne
    private Evento eventoIdevento;
    @JoinColumn(name = "pqr_idpqr", referencedColumnName = "idpqr")
    @ManyToOne
    private Pqr pqrIdpqr;

    public Archivo() {
    }

    public Archivo(Integer idarchivo) {
        this.idarchivo = idarchivo;
    }

    public Integer getIdarchivo() {
        return idarchivo;
    }

    public void setIdarchivo(Integer idarchivo) {
        this.idarchivo = idarchivo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public CampanaComunicados getCampanaComunicadosIdcampana() {
        return campanaComunicadosIdcampana;
    }

    public void setCampanaComunicadosIdcampana(CampanaComunicados campanaComunicadosIdcampana) {
        this.campanaComunicadosIdcampana = campanaComunicadosIdcampana;
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
        hash += (idarchivo != null ? idarchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Archivo)) {
            return false;
        }
        Archivo other = (Archivo) object;
        if ((this.idarchivo == null && other.idarchivo != null) || (this.idarchivo != null && !this.idarchivo.equals(other.idarchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iammagis.appropiate.beans.Archivo[ idarchivo=" + idarchivo + " ]";
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getTipoString() {
        return tipoString;
    }

    public void setTipoString(String tipoString) {
        this.tipoString = tipoString;
    }
    
}
