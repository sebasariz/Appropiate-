/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.reservas.lista;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.Reserva;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
import com.iammagis.appropiate.jpa.ReservaJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class LoadListaReservas extends org.apache.struts.action.Action {

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
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            Entidad entidad = (Entidad) httpSession.getAttribute("entidadGlobal");
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());

            ArrayList<Usuario> usuarios = new ArrayList<>(entidad.getUsuarioCollection());
            request.setAttribute("usuarios", usuarios);
            ArrayList<Locacion> locacions = new ArrayList<>(entidad.getLocacionCollection());
            request.setAttribute("locaciones", locacions);

            //falta cargas las reservas de este mes
            Calendar calendarInicial = Calendar.getInstance();
            calendarInicial.set(Calendar.DAY_OF_MONTH, 0);
            calendarInicial.set(Calendar.HOUR, 0);
            calendarInicial.set(Calendar.MINUTE, 0);
            calendarInicial.set(Calendar.SECOND, 0);
            calendarInicial.set(Calendar.MILLISECOND, 0);

            Calendar calendarFinal = Calendar.getInstance();
            calendarFinal.setTime(calendarInicial.getTime());
            calendarFinal.add(Calendar.MONTH, 1);

            //calcular en este periodo de tiempo y obtener todos los eventos
            ReservaJpaController reservaJpaController = new ReservaJpaController(manager);
            ArrayList<Reserva> reservas = reservaJpaController.getEventosFromFechaAndEntidad(calendarInicial.getTimeInMillis(), calendarFinal.getTimeInMillis(), entidad.getIdentidad());
            request.setAttribute("grid", GetDynamicTable.getReservas(reservas));

            String content = "/pages/reservas/listareservas.jsp";
            request.setAttribute("contenido", content);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(SUCCESS);
    }
}
