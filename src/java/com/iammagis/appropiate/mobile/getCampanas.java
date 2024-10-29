/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Archivo;
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.PropertiesAcces;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
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
 * @author sebastianarizmendy
 */
public class getCampanas extends org.apache.struts.action.Action {

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

        request.setCharacterEncoding("ISO-8859-15");
        response.setCharacterEncoding("ISO-8859-15");
        JSONObject jSONObject = new JSONObject();

        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        String token = request.getParameter("token");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);
        if (usuario != null) {
            JSONArray arrayEventos = new JSONArray();
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());
            for (Entidad entidad : entidads) {
                ArrayList<CampanaComunicados> campanaComunicadoses = new ArrayList<>(entidad.getCampanaComunicadosCollection());
                for (CampanaComunicados campanaComunicado : campanaComunicadoses) {
                    JSONObject jSONObjectCampanas = new JSONObject();
                    jSONObjectCampanas.put("id", campanaComunicado.getIdcampana());
                    jSONObjectCampanas.put("nombre", campanaComunicado.getNombre());
                    jSONObjectCampanas.put("fecha", campanaComunicado.getFecha().longValue());
                    jSONObjectCampanas.put("descripcion", campanaComunicado.getInformacion());
                    jSONObjectCampanas.put("archivos", campanaComunicado.getArchivoCollection().size());
                    jSONObjectCampanas.put("comentarios", campanaComunicado.getComentarioCollection().size());
                    arrayEventos.put(jSONObjectCampanas);
                }
            }
            jSONObject.put("campanas", arrayEventos);

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Ingresando a observar las campañas.");
            logUsuario.setTipo(26);
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
