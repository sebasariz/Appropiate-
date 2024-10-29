/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.encuestas.resultado;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
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
public class LoadResultadosEncuestas extends org.apache.struts.action.Action {

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
            ArrayList<Encuesta> encuestas;
            
            Entidad entidad = (Entidad) httpSession.getAttribute("entidadGlobal");
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            encuestas = new ArrayList<>(entidad.getEncuestaCollection());
            request.setAttribute("encuestas", encuestas); 
            
            request.setAttribute("grid", jSONObject);
            String content = "/pages/encuestas/resultado.jsp";
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
