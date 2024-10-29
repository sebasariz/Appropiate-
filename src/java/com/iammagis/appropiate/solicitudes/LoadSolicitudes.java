/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.solicitudes;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.jpa.TipoUsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.json.JSONArray;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadSolicitudes extends org.apache.struts.action.Action {

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
        HttpSession httpSession = request.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        ActionErrors errores = new ActionErrors();
        JSONArray jSONObject = new JSONArray();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            PqrJpaController pqrJpaController = new PqrJpaController(manager);
            String content = "";
            ArrayList<Pqr> pqrs;
            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    //obtenemos todos los usuarios por ser root
                    pqrs = pqrJpaController.getPQRActivos();
                    jSONObject = GetDynamicTable.getPQRS(pqrs);
                    break;
                case 2:
                    //ADMINISTRADOR
                    pqrs = new ArrayList<>();
                    for (Entidad entidad : usuario.getEntidadCollection()) {
                        ArrayList<Pqr> pqrsEntidad = pqrJpaController.getPQRSActivoByEntidad(entidad.getIdentidad());
                        pqrs.addAll(pqrsEntidad);
                    }
                    jSONObject = GetDynamicTable.getPQRS(pqrs);
                    break;
                default:
                    break;
            }
            request.setAttribute("grid", jSONObject);
            content = "/pages/solicitudes/solicitudes.jsp";
            request.setAttribute("contenido", content);

            TipoUsuarioJpaController tipoUsuarioJpaController = new TipoUsuarioJpaController(manager);
            ArrayList<TipoUsuario> tipoUsuarios = new ArrayList<>(tipoUsuarioJpaController.findTipoUsuarioEntities());
            request.setAttribute("tipos", tipoUsuarios);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(SUCCESS);
    }
}
