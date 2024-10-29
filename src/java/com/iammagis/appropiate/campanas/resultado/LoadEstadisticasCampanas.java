/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.campanas.resultado;

import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.CampanaComunicadosJpaController;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.TipoUsuarioJpaController;
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
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadEstadisticasCampanas extends org.apache.struts.action.Action {

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
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            CampanaComunicadosJpaController  campanaComunicadosJpaController = new CampanaComunicadosJpaController(manager); 
            String content = "";
            ArrayList<CampanaComunicados> campanaComunicadoses;
            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    //obtenemos todos los usuarios por ser root
                    campanaComunicadoses = new ArrayList<>(campanaComunicadosJpaController.findCampanaComunicadosEntities());
                    request.setAttribute("campanas", campanaComunicadoses);

                    request.setAttribute("entidades", entidadJpaController.findEntidadEntities());
                    break;
                case 2:
                    //ADMINISTRADOR
                    campanaComunicadoses = new ArrayList<>();
                    for (Entidad entidad : usuario.getEntidadCollection()) {
                        campanaComunicadoses.addAll(entidad.getCampanaComunicadosCollection());
                    }
                    request.setAttribute("campanas", campanaComunicadoses);
                    request.setAttribute("entidades", usuario.getEntidadCollection());
                    break;
                default:
                    break;
            }
            request.setAttribute("grid", jSONObject);
            content = "/pages/campanas/resultado.jsp";
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
