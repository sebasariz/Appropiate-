/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.encuestas;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.GrupoInteresJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PreguntaJpaController;
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
public class CrearEncuestas extends org.apache.struts.action.Action {

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
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            GrupoInteresJpaController grupoInteresJpaController = new GrupoInteresJpaController(manager);
            Entidad entidad = (Entidad) session.getAttribute("entidadGlobal");
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());

            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            PreguntaJpaController preguntaJpaController = new PreguntaJpaController(manager);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Encuesta encuesta = (Encuesta) form;

            ArrayList<Usuario> usuarios = new ArrayList<>();
            String idpara = encuesta.getIdparaString();
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

            //guardamos el archivo de la enceusta
            FormFile cara = encuesta.getFoto();
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
                    encuesta.setImagen(fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }
            encuesta.setEntidadIdentidad(entidad);
            encuesta.setEstado(1);
            encuesta = encuestaJpaController.create(encuesta);

            String preguntasString = encuesta.getCamposJson();
            JSONArray jSONArrayPreguntas = new JSONArray(preguntasString);
            for (int i = 0; i < jSONArrayPreguntas.length(); i++) {
                JSONArray jSONObjectPregunta = jSONArrayPreguntas.getJSONArray(i);

                Pregunta pregunta = new Pregunta();
                pregunta.setNombre(jSONObjectPregunta.getString(0));
                if (!jSONObjectPregunta.getString(2).equals("")) {
                    pregunta.setEscala(jSONObjectPregunta.getString(2));
                } else {
                    pregunta.setEscala("");
                }
                int tipo = 0;
                if (jSONObjectPregunta.getString(1).equalsIgnoreCase("Si/No")) {
                    tipo = 1;
                } else if (jSONObjectPregunta.getString(1).equalsIgnoreCase("Abierta")) {
                    tipo = 2;
                } else if (jSONObjectPregunta.getString(1).equalsIgnoreCase("Escala")) {
                    tipo = 3;
                } else if (jSONObjectPregunta.getString(1).equalsIgnoreCase("Multiple")) {
                    tipo = 4;
                }
                pregunta.setTipo(tipo);
                pregunta.setEncuestaIdencuesta(encuesta);
                preguntaJpaController.create(pregunta);
            }

            //enviamos la notificacion a todos los que pertenezcan a esa entidad
            System.out.println("usuarios: " + usuarios.size());
            EncuestaHasUsuarioJpaController encuestaHasUsuarioJpaController = new EncuestaHasUsuarioJpaController(manager);
            ArrayList<String> tokensArray = new ArrayList<>();
            for (Usuario usuarioEntidad : usuarios) {
                if (usuarioEntidad.getDevicesTokens() != null) {
                    String devicesTokens = usuarioEntidad.getDevicesTokens();
                    String[] tokens = devicesTokens.split(",");
                    tokensArray.addAll(Arrays.asList(tokens));
                }
                EncuestaHasUsuario encuestaHasUsuario = encuestaHasUsuarioJpaController.findEncuestaHasUsuario(encuesta.getIdencuesta(), usuarioEntidad.getIdusuario());
                if (encuestaHasUsuario == null) {
                    encuestaHasUsuario = new EncuestaHasUsuario();
                    encuestaHasUsuario.setEncuesta(encuesta);
                    encuestaHasUsuario.setUsuario(usuarioEntidad);
                    encuestaHasUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
                    encuestaHasUsuario.setRespuesta(0);
                    encuestaHasUsuarioJpaController.create(encuestaHasUsuario);
                }
            }
            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "Nueva encuesta: " + encuesta.getNombre(), 1/*encuesta*/);
            //cargamos los usuarios 

            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            ArrayList<Encuesta> encuestas = new ArrayList<>(entidad.getEncuestaCollection());
            jSONObject.put("grid", GetDynamicTable.getEncuestas(encuestas));

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Creando encuesta: " + encuesta.getNombre());
            logUsuario.setTipo(9);
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
