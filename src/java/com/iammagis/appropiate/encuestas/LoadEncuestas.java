/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.encuestas;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.beans.support.Para;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.TipoUsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.lang.reflect.Array;
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

/**
 *
 * @author sebastianarizmendy
 */
public class LoadEncuestas extends org.apache.struts.action.Action {

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

        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            Entidad entidad = (Entidad) httpSession.getAttribute("entidadGlobal");
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            ArrayList<Encuesta> encuestas = new ArrayList<>(entidad.getEncuestaCollection());
            request.setAttribute("grid", GetDynamicTable.getEncuestas(encuestas));
            String content = "/pages/encuestas/encuestas.jsp";
            request.setAttribute("contenido", content);

            ArrayList<Para> paras = new ArrayList<>();

            Para paraTodos = new Para();
            paraTodos.setNombre("Todos");
            paraTodos.setId("0-0");
            paras.add(paraTodos);
            //cargamos los grupos de interes

            ArrayList<GrupoInteres> grupoIntereses = new ArrayList<>(entidad.getGrupoInteresCollection());
            for (GrupoInteres grupoInteres : grupoIntereses) {
                Para para = new Para();
                para.setNombre(grupoInteres.getNombre());
                para.setId(grupoInteres.getIdgrupoInteres() + "-1");
                paras.add(para);
            }
            ArrayList<Usuario> usuarios = new ArrayList<>(entidad.getUsuarioCollection());
            for (Usuario usuarioEntidad : usuarios) {
                Para para = new Para();
                para.setNombre(usuarioEntidad.getNombre() + " " + usuarioEntidad.getApellidos());
                para.setId(usuarioEntidad.getIdusuario() + "-2");
                paras.add(para);
            } 
            request.setAttribute("paras", paras);

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
