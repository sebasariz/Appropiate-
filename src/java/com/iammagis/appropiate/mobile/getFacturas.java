/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.PropertiesAcces;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class getFacturas extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static PropertiesAcces propertiesAcces = new PropertiesAcces();

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("ISO-8859-15");
        response.setCharacterEncoding("ISO-8859-15");
        JSONObject jSONObject = new JSONObject();

        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        String token = request.getParameter("token");
        System.out.println("token: " + token);
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);

        if (usuario != null) {
            JSONArray jSONArrayFacturas = new JSONArray();
            ArrayList<Factura> facturas = new ArrayList<>(usuario.getFacturaCollection());
            System.out.println("facturas: "+facturas.size());
            for(Factura factura:facturas){
                JSONObject jSONObjectFactura = new JSONObject();
                jSONObjectFactura.put("id", factura.getIdfactura());
                jSONObjectFactura.put("fecha", factura.getFecha().longValue());
                jSONObjectFactura.put("valor", factura.getValor());
                jSONObjectFactura.put("conceptos", factura.getConceptos());
                jSONObjectFactura.put("referencia", factura.getReferencia());
                jSONObjectFactura.put("estado", factura.getEstado());
                jSONObjectFactura.put("valor_pagado", factura.getValorPagado());
                jSONArrayFacturas.put(jSONObjectFactura);
            } 

            jSONObject.put("facturas", jSONArrayFacturas);

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Ingresando a facturas.");
            logUsuario.setTipo(30);
            logUsuarioJpaController.create(logUsuario);
        } else {
            jSONObject.put("error", 400);
            jSONObject.put("msg", "Error token");
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
