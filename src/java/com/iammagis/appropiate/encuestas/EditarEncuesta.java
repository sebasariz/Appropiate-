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
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.GrupoInteresJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PreguntaJpaController;
import com.iammagis.appropiate.jpa.RespuestaJpaController;
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
public class EditarEncuesta extends org.apache.struts.action.Action {

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
            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            RespuestaJpaController respuestaJpaController = new RespuestaJpaController(manager);
            PreguntaJpaController preguntaJpaController = new PreguntaJpaController(manager);
            Encuesta encuesta = (Encuesta) form;
            Encuesta encuestaAnterior = encuestaJpaController.findEncuesta(encuesta.getIdencuesta());
            Entidad entidad = encuestaAnterior.getEntidadIdentidad();

            GrupoInteresJpaController grupoInteresJpaController = new GrupoInteresJpaController(manager);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);

            ArrayList<Usuario> usuarios = new ArrayList<>();
            String idpara = encuesta.getIdparaString();
            encuestaAnterior.setIdparaString(idpara);
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
                    encuestaAnterior.setImagen(fname);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("ERROR-------" + e);
            }
            encuestaAnterior.setEstado(1);
            encuestaAnterior = encuestaJpaController.edit(encuestaAnterior);

            String preguntasString = encuesta.getCamposJson();
            JSONArray jSONArrayPreguntas = new JSONArray(preguntasString);
            ArrayList<Pregunta> preguntas = new ArrayList<>(encuestaAnterior.getPreguntaCollection());

            for (int i = 0; i < jSONArrayPreguntas.length(); i++) {
                JSONArray jSONObjectPregunta = jSONArrayPreguntas.getJSONArray(i);
                System.out.println("json: " + jSONObjectPregunta);
                for (int w = 0; w < preguntas.size(); w++) {
                    Pregunta pregunta = preguntas.get(w);
                    if (pregunta.getNombre().equalsIgnoreCase(jSONObjectPregunta.getString(0))) {
                        System.out.println("eliminando");
                        preguntas.remove(w);
                        jSONArrayPreguntas.remove(i);
                        i--;
                    }
                }
            }
            System.out.println("preguntas: " + preguntas.size());
            System.out.println("las que quedan se eliminan");
            System.out.println("las que faltan en el json se crean: " + jSONArrayPreguntas);

            for (Pregunta pregunta : preguntas) {
                ArrayList<Respuesta> respuestas = new ArrayList<>(pregunta.getRespuestaCollection());
                for (Respuesta respuesta : respuestas) {
                    respuestaJpaController.destroy(respuesta.getIdrespuesta());
                }
                preguntaJpaController.destroy(pregunta.getIdpregunta());
            }

            for (int i = 0; i < jSONArrayPreguntas.length(); i++) {
                JSONArray jSONObjectPregunta = jSONArrayPreguntas.getJSONArray(i);
                Pregunta pregunta = new Pregunta();
                pregunta.setNombre(jSONObjectPregunta.getString(0));
                if (!jSONObjectPregunta.getString(2).equals("")) {
                    pregunta.setEscala(jSONObjectPregunta.getString(2));
                } else {
                    pregunta.setEscala("");
                }
                String tipo = jSONObjectPregunta.getString(1);
                int tipoInt = 0;
                if (tipo.equalsIgnoreCase("Si/No")) {
                    tipoInt = 1;
                } else if (tipo.equalsIgnoreCase("Abierta")) {
                    tipoInt = 2;
                } else if (tipo.equalsIgnoreCase("Escala")) {
                    tipoInt = 3;
                }

                pregunta.setTipo(tipoInt);
                pregunta.setEncuestaIdencuesta(encuesta);
                preguntaJpaController.create(pregunta);
            }

            EncuestaHasUsuarioJpaController encuestaHasUsuarioJpaController = new EncuestaHasUsuarioJpaController(manager);
            ArrayList<String> tokensArray = new ArrayList<>();
            for (Usuario usuarioEntidad : usuarios) {
                if (usuarioEntidad.getDevicesTokens() != null) {
                    String devicesTokens = usuarioEntidad.getDevicesTokens();
                    String[] tokens = devicesTokens.split(",");
                    System.out.println(Arrays.toString(tokens));
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
            for(String tokens: tokensArray){
                System.out.println("token_ "+tokens);
            }
            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "Nueva encuesta: " + encuesta.getNombre(), 1/*encuesta*/);

            //cargamos los usuarios
            entidad = (Entidad) session.getAttribute("entidadGlobal");
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            ArrayList<Encuesta> encuestas = new ArrayList<>(entidad.getEncuestaCollection());
            jSONObject.put("grid", GetDynamicTable.getEncuestas(encuestas));

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Editar encuesta: " + encuesta.getNombre());
            logUsuario.setTipo(10);
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
