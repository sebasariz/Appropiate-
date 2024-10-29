/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.eventos;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.EventoHasUsuarioPK;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.EventoHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
import com.iammagis.appropiate.jpa.GrupoInteresJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.FCM;
import com.iammagis.appropiate.support.GetDynamicTable;
import com.iammagis.appropiate.support.PropertiesAcces;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class EditarEvento extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    PropertiesAcces propertiesAcces = new PropertiesAcces();

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
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            Evento evento = (Evento) form;
            EventoJpaController eventoJpaController = new EventoJpaController(manager);
            GrupoInteresJpaController grupoInteresJpaController = new GrupoInteresJpaController(manager);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);

            Evento eventoAnterior = eventoJpaController.findEvento(evento.getIdevento());
            eventoAnterior.setNombre(evento.getNombre());
            eventoAnterior.setInformacion(evento.getInformacion());
            eventoAnterior.setFecha(evento.getFecha());

            Entidad entidad = eventoAnterior.getEntidadIdentidad();

            FormFile cara = evento.getImagenform();
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
                    eventoAnterior.setImagen(fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }

            ArrayList<Usuario> usuarios = new ArrayList<>();
            String idpara = evento.getIdparaString();
            eventoAnterior.setIdparaString(idpara);
            JSONArray jSONArray = new JSONArray(idpara);
            for (int i = 0; i < jSONArray.length(); i++) {
                String token = jSONArray.getString(i);

                String[] ids = token.split("-");
                int tipo = Integer.parseInt(ids[1]);
                switch (tipo) {
                    case 0:
                        //0 todos
                        usuarios.addAll(entidad.getUsuarioCollection());
                        break;
                    case 1:
                        //1 grupos
                        int idGrupo = Integer.parseInt(ids[0]);
                        GrupoInteres grupoInteres = grupoInteresJpaController.findGrupoInteres(idGrupo);
                        usuarios.addAll(grupoInteres.getUsuarioCollection());
                        break;
                    case 2:
                        //2 usuarios
                        int idUsuario = Integer.parseInt(ids[0]);
                        Usuario usuarioFind = usuarioJpaController.findUsuario(idUsuario);
                        usuarios.add(usuarioFind);
                        break;
                    default:
                        break;
                }
            }
            eventoAnterior.setIdparaString(idpara);
            eventoJpaController.edit(eventoAnterior);
            //enviamos push
            //enviamos la notificacion a todos los que pertenezcan a esa entidad 
            EventoHasUsuarioJpaController eventoHasUsuarioJpaController = new EventoHasUsuarioJpaController(manager);
            ArrayList<String> tokensArray = new ArrayList<>();
            for (Usuario usuarioEntidad : usuarios) {
                if (usuarioEntidad.getDevicesTokens() != null) {
                    String devicesTokens = usuarioEntidad.getDevicesTokens();
                    String[] tokens = devicesTokens.split(",");
                    tokensArray.addAll(Arrays.asList(tokens));
                }

                EventoHasUsuario eventoHasUsuario = eventoHasUsuarioJpaController.findEventoHasUsuario(new EventoHasUsuarioPK(evento.getIdevento(), usuarioEntidad.getIdusuario()));
                System.out.println("eventoHasUsuario: " + eventoHasUsuario + " usuarioEntidad: " + usuarioEntidad.getNombre());
                if (eventoHasUsuario == null) {
                    eventoHasUsuario = new EventoHasUsuario();
                    eventoHasUsuario.setEvento(evento);
                    eventoHasUsuario.setUsuario(usuarioEntidad);
                    eventoHasUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
                    eventoHasUsuario.setRespuesta(0);
                    eventoHasUsuarioJpaController.create(eventoHasUsuario); 
                }

            }
            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "Nueva evento: " + evento.getNombre(), 2/*eventos*/);

            entidad = (Entidad) httpSession.getAttribute("entidadGlobal");
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());

            ArrayList<Evento> eventos = new ArrayList<>(entidad.getEventoCollection());
            jSONObject.put("grid", GetDynamicTable.getEventos(eventos));

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Creando evento: " + evento.getNombre());
            logUsuario.setTipo(13);
            logUsuarioJpaController.create(logUsuario);

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
