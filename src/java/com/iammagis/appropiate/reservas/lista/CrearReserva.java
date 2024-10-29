/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.reservas.lista;

import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.Reserva;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.LocacionJpaController;
import com.iammagis.appropiate.jpa.ReservaJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import java.io.PrintWriter;
import java.math.BigInteger;
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
 * @author sebasariz
 */
public class CrearReserva extends org.apache.struts.action.Action {

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

            int idLocacion = Integer.parseInt(request.getParameter("locacion"));
            int idUsuario = Integer.parseInt(request.getParameter("usuario"));
            long fecha = Long.parseLong(request.getParameter("start"));

            LocacionJpaController locacionJpaController = new LocacionJpaController(manager);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);

            Locacion locacion = locacionJpaController.findLocacion(idLocacion);
            Usuario usuarioReserva = usuarioJpaController.findUsuario(idUsuario);

            Reserva reserva = new Reserva();
            reserva.setFecha(BigInteger.valueOf(fecha));
            reserva.setLocacionIdlocacion(locacion);
            reserva.setUsuarioIdusuario(usuarioReserva);
            reserva.setEstado(1);

            ReservaJpaController reservaJpaController = new ReservaJpaController(manager);
            reserva = reservaJpaController.create(reserva);
            System.out.println("reserva.getIdreserva(): " + reserva.getIdreserva());
            jSONObject.put("id", reserva.getIdreserva());
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
