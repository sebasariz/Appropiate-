/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.entidades;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
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
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class EditarEntidad extends org.apache.struts.action.Action {

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
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            Entidad entidad = (Entidad) form;
            Entidad entidadAnterior = entidadJpaController.findEntidad(entidad.getIdentidad());
            entidadAnterior.setNombre(entidad.getNombre());
            entidadAnterior.setCorreo(entidad.getCorreo());
            entidadAnterior.setContacto(entidad.getContacto());
            entidadAnterior.setDireccion(entidad.getDireccion());
            entidadAnterior.setNit(entidad.getNit());
            entidadAnterior.setReservas(entidad.getReservas());
            entidadAnterior.setTelefono(entidad.getTelefono());
            entidadAnterior.setPasarela(entidad.getPasarela());
            entidadAnterior.setCamposPasarela(entidad.getCamposPasarela());
            entidadJpaController.edit(entidadAnterior);
            //validar el email

//
//                //cargamos los usuarios 
            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    //obtenemos todos los usuarios por ser root
                    ArrayList<Entidad> entidads = new ArrayList<>(entidadJpaController.findEntidadEntities());
                    jSONObject = jSONObject.put("grid", GetDynamicTable.getEntidades(entidads));
                    break;
                case 2:
                    //Gestor
                    break;
                default:
                    break;
            }

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
