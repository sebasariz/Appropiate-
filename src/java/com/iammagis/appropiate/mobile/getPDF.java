/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.jpa.FacturaJpaController;
import com.iammagis.appropiate.support.FacturaCreator;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class getPDF extends org.apache.struts.action.Action {

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
        
        int id= Integer.parseInt(request.getParameter("id"));
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        FacturaJpaController facturaJpaController = new FacturaJpaController(manager);
        Factura factura = facturaJpaController.findFactura(id);
        JSONObject jSONObject = FacturaCreator.generateFacturaPDF(factura, getServlet().getServletContext().getRealPath(""));
        return null;
    }
}
