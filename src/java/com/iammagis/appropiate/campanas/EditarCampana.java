/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.campanas;

import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuarioPK;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.CampanaComunicadosHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.CampanaComunicadosJpaController;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.GrupoInteresJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.FCM;
import com.iammagis.appropiate.support.GetDynamicTable;
import com.iammagis.appropiate.support.PropertiesAcces;
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
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class EditarCampana extends org.apache.struts.action.Action {

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
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            CampanaComunicados campanaComunicados = (CampanaComunicados) form;
            CampanaComunicadosJpaController campanaComunicadosJpaController = new CampanaComunicadosJpaController(manager);
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);

            CampanaComunicados campanaComunicadosAnterior = campanaComunicadosJpaController.findCampanaComunicados(campanaComunicados.getIdcampana());

            campanaComunicadosAnterior.setNombre(campanaComunicados.getNombre());
            campanaComunicadosAnterior.setInformacion(campanaComunicados.getInformacion());
            campanaComunicadosAnterior.setFecha(campanaComunicados.getFecha());
            Entidad entidad = campanaComunicadosAnterior.getEntidadIdentidad();
            GrupoInteresJpaController grupoInteresJpaController = new GrupoInteresJpaController(manager);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            ArrayList<Usuario> usuarios = new ArrayList<>();
            String idpara = campanaComunicados.getIdparaString();
            campanaComunicadosAnterior.setIdparaString(idpara);
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
            campanaComunicados = campanaComunicadosJpaController.edit(campanaComunicadosAnterior);

            CampanaComunicadosHasUsuarioJpaController campanaComunicadosHasUsuarioJpaController = new CampanaComunicadosHasUsuarioJpaController(manager);
            ArrayList<String> tokensArray = new ArrayList<>();
            for (Usuario usuarioEntidad : usuarios) {
                if (usuarioEntidad.getDevicesTokens() != null) {
                    String devicesTokens = usuarioEntidad.getDevicesTokens();
                    String[] tokens = devicesTokens.split(",");
                    tokensArray.addAll(Arrays.asList(tokens));
                }

                CampanaComunicadosHasUsuario campanaComunicadosHasUsuario = campanaComunicadosHasUsuarioJpaController.findCampanaComunicadosHasUsuario(new CampanaComunicadosHasUsuarioPK(campanaComunicadosAnterior.getIdcampana(), usuario.getIdusuario()));
                if (campanaComunicadosHasUsuario == null) {
                    campanaComunicadosHasUsuario = new CampanaComunicadosHasUsuario();
                    campanaComunicadosHasUsuario.setCampanaComunicados(campanaComunicados);
                    campanaComunicadosHasUsuario.setUsuario(usuarioEntidad);
                    campanaComunicadosHasUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
                    campanaComunicadosHasUsuario.setRespuesta(0);
                    campanaComunicadosHasUsuarioJpaController.create(campanaComunicadosHasUsuario);
                }
            }
            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "Nueva campaña: " + campanaComunicados.getNombre(), 3/*campañas*/);
            //cargamos los usuarios 
            entidad = (Entidad) session.getAttribute("entidadGlobal");
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            ArrayList<CampanaComunicados> campanaComunicadoses = new ArrayList<>(entidad.getCampanaComunicadosCollection());
            jSONObject.put("grid", GetDynamicTable.getCampanasComunicados(campanaComunicadoses));

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Editar campaña: " + campanaComunicados.getNombre());
            logUsuario.setTipo(7);
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
