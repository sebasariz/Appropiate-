/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
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
 * @author sebastianarizmendy
 */
public class LoginMobile extends org.apache.struts.action.Action {

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
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = new Usuario();
        usuario.setCorreo(user);
        usuario.setPass(pass);
        JSONObject jSONObject = new JSONObject();
        usuario = usuarioJpaController.login(usuario);
        if (usuario != null) {
            //login 
            String token = UUID.randomUUID().toString();
            usuario.setSessionToken(token);
            jSONObject.put("nombre", usuario.getNombre());
            jSONObject.put("apellidos", usuario.getApellidos());
            jSONObject.put("token", token);
            jSONObject.put("correo", usuario.getCorreo());
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());
            System.out.println("entidads.get(0): " + entidads.get(0).getNombre() + " pasarela: " + entidads.get(0).getPasarela());
            if (!entidads.isEmpty() && entidads.get(0).getPasarela() != null) {
                jSONObject.put("facturacion", entidads.get(0).getPasarela());
                jSONObject.put("tokens", entidads.get(0).getCamposPasarela());
            } else {
                jSONObject.put("facturacion", 0);
                jSONObject.put("tokens", "{}");
            }
            if (!entidads.isEmpty() && entidads.get(0).getReservas() != null) {
                jSONObject.put("reservas", entidads.get(0).getReservas());
            } else {
                jSONObject.put("reservas", 0);
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
            //no login
            jSONObject.put("error", "Error de usuario o clave");
        }
//        System.out.println("user: " + user + " pass:" + pass);

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
