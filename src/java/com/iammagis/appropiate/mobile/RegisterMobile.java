/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuarioPK;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.EncuestaHasUsuarioPK;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.EventoHasUsuarioPK;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.CampanaComunicadosHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EncuestaHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.EventoHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;
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
public class RegisterMobile extends org.apache.struts.action.Action {

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
        String user = request.getParameter("email");
        String token_empresa = request.getParameter("token_empresa");
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = new Usuario();
        usuario.setNombre(user);
        usuario.setApellidos(" ");
        usuario.setCorreo(user);
        JSONObject jSONObject = new JSONObject();
        usuario = usuarioJpaController.emailExist(usuario);

        EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
        Entidad entidad = entidadJpaController.getByToken(token_empresa);
        ArrayList<Entidad> entidads1 = new ArrayList<>();
        entidads1.add(entidad);
        if (usuario != null) {
            //login 
            String token = UUID.randomUUID().toString();
            usuario.setSessionToken(token);
            jSONObject.put("nombre", usuario.getNombre());
            jSONObject.put("apellidos", usuario.getApellidos());
            jSONObject.put("token", token);
            jSONObject.put("correo", usuario.getCorreo());
            usuario.setEntidadCollection(entidads1);
            usuarioJpaController.edit(usuario);
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());
            if (!entidads.isEmpty() && entidads.get(0).getPasarela() != null) {
                jSONObject.put("facturacion", entidads.get(0).getPasarela());
                jSONObject.put("tokens", entidads.get(0).getCamposPasarela());
            } else {
                jSONObject.put("facturacion", 0);
                jSONObject.put("tokens", "{}");
            }
            System.out.println("jSONObject: " + jSONObject);
            //modulos que tiene activos el adminsitrador

            usuarioJpaController.edit(usuario);

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Ingreso a aplicación móvil.");
            logUsuario.setTipo(24);
            logUsuarioJpaController.create(logUsuario);
        } else {
            //no existe lo registramos

            usuario = new Usuario();
            usuario.setTipoUsuarioIdtipoUsuario(new TipoUsuario(3));
            usuario.setEntidadCollection(entidads1);
            usuario.setNombre(user);
            usuario.setApellidos(" ");
            usuario.setCorreo(user);
            usuario.setPass(UUID.randomUUID().toString().substring(0, 4));
            String token = UUID.randomUUID().toString();
            usuario.setSessionToken(token);
            jSONObject.put("nombre", usuario.getNombre());
            jSONObject.put("apellidos", usuario.getApellidos());
            jSONObject.put("token", token);
            jSONObject.put("correo", usuario.getCorreo());
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());
            if (!entidads.isEmpty() && entidads.get(0).getPasarela() != null) {
                jSONObject.put("facturacion", entidads.get(0).getPasarela());
                jSONObject.put("tokens", entidads.get(0).getCamposPasarela());
            } else {
                jSONObject.put("facturacion", 0);
                jSONObject.put("tokens", "{}");
            }
            usuario = usuarioJpaController.create(usuario);
            //modulos que tiene activos el adminsitrador 

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Ingreso a aplicación móvil.");
            logUsuario.setTipo(24);
            logUsuarioJpaController.create(logUsuario);
        }

        //asignamos lso eventos  y notificaciones existentes
        EventoHasUsuarioJpaController eventoHasUsuarioJpaController = new EventoHasUsuarioJpaController(manager);
        ArrayList<Evento> eventos = new ArrayList<>(entidad.getEventoCollection());
        for (Evento evento : eventos) {
            //lo buscamos
            EventoHasUsuarioPK eventoHasUsuarioPK = new EventoHasUsuarioPK();
            eventoHasUsuarioPK.setEventoIdevento(evento.getIdevento());
            eventoHasUsuarioPK.setUsuarioIdusuario(usuario.getIdusuario());
            EventoHasUsuario eventoHasUsuario = eventoHasUsuarioJpaController.findEventoHasUsuario(eventoHasUsuarioPK);
            if (eventoHasUsuario == null) {
                eventoHasUsuario = new EventoHasUsuario();
                eventoHasUsuario.setRespuesta(0);
                eventoHasUsuario.setEvento(evento);
                eventoHasUsuario.setUsuario(usuario);
                eventoHasUsuarioJpaController.create(eventoHasUsuario);
            }
        }
        CampanaComunicadosHasUsuarioJpaController campanaComunicadosHasUsuarioJpaController = new CampanaComunicadosHasUsuarioJpaController(manager);
        ArrayList<CampanaComunicados> campanaComunicadoses = new ArrayList<>(entidad.getCampanaComunicadosCollection());
        for (CampanaComunicados campanaComunicados : campanaComunicadoses) {
            CampanaComunicadosHasUsuarioPK campanaComunicadosHasUsuarioPK = new CampanaComunicadosHasUsuarioPK();
            campanaComunicadosHasUsuarioPK.setCampanaComunicadosIdcampana(campanaComunicados.getIdcampana());
            campanaComunicadosHasUsuarioPK.setUsuarioIdusuario(usuario.getIdusuario());

            CampanaComunicadosHasUsuario campanaComunicadosHasUsuario = campanaComunicadosHasUsuarioJpaController.findCampanaComunicadosHasUsuario(campanaComunicadosHasUsuarioPK);
            if (campanaComunicadosHasUsuario == null) {
                campanaComunicadosHasUsuario = new CampanaComunicadosHasUsuario();
                campanaComunicadosHasUsuario.setCampanaComunicados(campanaComunicados);
                campanaComunicadosHasUsuario.setUsuario(usuario);
                campanaComunicadosHasUsuarioJpaController.create(campanaComunicadosHasUsuario);
            }

        }
        EncuestaHasUsuarioJpaController encuestaHasUsuarioJpaController = new EncuestaHasUsuarioJpaController(manager);
        ArrayList<Encuesta> encuestas = new ArrayList<>(entidad.getEncuestaCollection());
        for (Encuesta encuesta : encuestas) {
            EncuestaHasUsuarioPK encuestaHasUsuarioPK = new EncuestaHasUsuarioPK();
            encuestaHasUsuarioPK.setEncuestaIdencuesta(encuesta.getIdencuesta());
            encuestaHasUsuarioPK.setUsuarioIdusuario(usuario.getIdusuario());

            EncuestaHasUsuario encuestaHasUsuario = encuestaHasUsuarioJpaController.findEncuestaHasUsuario(encuestaHasUsuarioPK);
            if (encuestaHasUsuario == null) {
                encuestaHasUsuario = new EncuestaHasUsuario();
                encuestaHasUsuario.setEncuesta(encuesta);
                encuestaHasUsuario.setUsuario(usuario);
                encuestaHasUsuarioJpaController.create(encuestaHasUsuario);
            }
        }

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
