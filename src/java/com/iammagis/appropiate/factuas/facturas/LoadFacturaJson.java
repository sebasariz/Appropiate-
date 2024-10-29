/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.factuas.facturas;

import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.FacturaTemplate;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.FacturaJpaController;
import com.iammagis.appropiate.jpa.FacturaTemplateJpaController;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class LoadFacturaJson extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            int id = Integer.parseInt(request.getParameter("id"));

            FacturaJpaController facturaJpaController = new FacturaJpaController(manager);
            Factura factura = facturaJpaController.findFactura(id);

            jSONObject.put("id", factura.getIdfactura());
            jSONObject.put("idUsuario", factura.getUsuarioIdusuario().getIdusuario());
            jSONObject.put("idTemplate", factura.getFacturaTemplateIdfacturaTemplate().getIdfacturaTemplate());
            jSONObject.put("fecha", factura.getFecha().longValue());
            jSONObject.put("referencia", factura.getReferencia());
            jSONObject.put("valor", factura.getValor());
            jSONObject.put("grid", new JSONArray(factura.getConceptos()));

        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.appropiate.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
