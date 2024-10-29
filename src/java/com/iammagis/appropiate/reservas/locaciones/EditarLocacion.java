/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.reservas.locaciones;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.LocacionJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class EditarLocacion extends org.apache.struts.action.Action {

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
            Locacion locacion = (Locacion) form;
            LocacionJpaController locacionJpaController = new LocacionJpaController(manager);

            Locacion locacionAnterior = locacionJpaController.findLocacion(locacion.getIdlocacion());

            locacionAnterior.setDescripcion(locacion.getDescripcion());
            locacionAnterior.setNombre(locacion.getNombre());
            locacionAnterior.setResponsable(locacion.getResponsable());

            FormFile cara = locacion.getImagenform();
            File foto = null;
            try {
                // get file from the bean
                String fname = cara.getFileName();
                if (fname.length() == 0) {
                    System.out.println("sin archivo");
                } else {
                    fname = fname.replace(" ", "");
                    fname = System.currentTimeMillis() + fname;
                    // save file in the app server 
                    foto = new File(getServlet().getServletContext().getRealPath("") + "/img/" + fname);
                    FileOutputStream fos = new FileOutputStream(foto);
                    fos.write(cara.getFileData());
                    fos.close();
                    locacionAnterior.setImagen(fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }
            locacionJpaController.edit(locacionAnterior);

            //cargamos las locaciones 
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            Entidad entidad = (Entidad) session.getAttribute("entidadGlobal");
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            ArrayList<Locacion> locacions =new ArrayList<>(entidad.getLocacionCollection()); 
            jSONObject.put("grid", GetDynamicTable.getLocaciones(locacions));
             

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
