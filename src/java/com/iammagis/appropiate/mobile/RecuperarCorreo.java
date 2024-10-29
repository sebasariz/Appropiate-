/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.Correo;
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
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class RecuperarCorreo extends org.apache.struts.action.Action {

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

        request.setCharacterEncoding("ISO-8859-15");
        response.setCharacterEncoding("ISO-8859-15");
        JSONObject jSONObject = new JSONObject();
        String emial = request.getParameter("email");
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = new Usuario();
        usuario.setCorreo(emial);
        usuario = usuarioJpaController.emailExist(usuario);
        if (usuario != null) {

            ArrayList<String> strings = new ArrayList<>();
            strings.add(usuario.getCorreo());
            String template = Correo.templateApp;
            template = template.replace("$nombre", usuario.getNombre() + " " + usuario.getApellidos());
            template = template.replace("$usuario", usuario.getCorreo());
            template = template.replace("$password", usuario.getPass());
            template = template.replace("$email", usuario.getCorreo());
            Correo correo = new Correo("Datos de ingreso a Appropiate", template, strings);
            correo.start();

            jSONObject.put("msg", "Sus datos de acceso fueron enviadas al correo electronico.");
        } else {
            jSONObject.put("msg", "El correo electronico no se encuentra registrado.");
        }

        LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
        LogUsuario logUsuario = new LogUsuario();
        logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
        logUsuario.setUsuarioIdusuario(usuario);
        logUsuario.setActividad("Recuperación de usuario y clave de aplicación móvil.");
        logUsuario.setTipo(25);
        logUsuarioJpaController.create(logUsuario);

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
