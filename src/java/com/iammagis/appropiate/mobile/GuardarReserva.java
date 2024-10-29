/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Reserva;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.LocacionJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PreguntaJpaController;
import com.iammagis.appropiate.jpa.ReservaJpaController;
import com.iammagis.appropiate.jpa.RespuestaJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class GuardarReserva extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");

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

        request.setCharacterEncoding("ISO-8859-15");
        response.setCharacterEncoding("ISO-8859-15");
        String token = request.getParameter("token");
        int idLocacion = Integer.parseInt(request.getParameter("idLocacion"));
        long fecha = Long.parseLong(request.getParameter("fecha"));
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            LocacionJpaController locacionJpaController = new LocacionJpaController(manager);
            Locacion locacion = locacionJpaController.findLocacion(idLocacion);

            Reserva reserva = new Reserva();
            reserva.setEstado(1);
            reserva.setFecha(BigInteger.valueOf(fecha));
            reserva.setLocacionIdlocacion(locacion);
            reserva.setUsuarioIdusuario(usuario);
            ReservaJpaController reservaJpaController = new ReservaJpaController(manager);
            reservaJpaController.create(reserva);

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Creando reserva: " + locacion.getNombre() + " para: " + simpleDateFormat.format(new Date(fecha)));
            logUsuario.setTipo(23);
            logUsuarioJpaController.create(logUsuario);
        } else {
            jSONObject.put("error", 400);
            jSONObject.put("msg", "Error token");
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
