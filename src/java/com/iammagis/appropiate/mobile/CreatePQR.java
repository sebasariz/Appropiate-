/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
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
public class CreatePQR extends org.apache.struts.action.Action {

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
        String token = request.getParameter("token");
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            String solicitud = request.getParameter("solicitud");

            String tipo = request.getParameter("tipo");

            Pqr pqr = new Pqr();
            pqr.setEstado(1);
            pqr.setFecha(BigInteger.valueOf(System.currentTimeMillis()));

            int tipoInt = 1;
            if (tipo.equals("Petición")) {
                tipoInt = 1;
            } else if (tipo.equals("Petición")) {
                tipoInt = 2;
            } else if (tipo.equals("Queja")) {
                tipoInt = 3;
            } else if (tipo.equals("Reclamo")) {
                tipoInt = 4;
            } else if (tipo.equals("Solicitud")) {
                tipoInt = 5;
            }
            pqr.setTipo(tipoInt);
            pqr.setUsuarioIdusuario(usuario);

            System.out.println("solicitud: " + solicitud);
            System.out.println("tipo: " + tipo);
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());
            pqr.setEntidadIdentidad(entidads.get(0));
            pqr.setPqr(solicitud);

            PqrJpaController pqrJpaController = new PqrJpaController(manager);
            pqrJpaController.create(pqr);

            //mandamos el correo
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            Entidad entidad = entidadJpaController.findEntidad(entidads.get(0).getIdentidad());
            System.out.println("entidad: " + entidad.getNombre());
            ArrayList<Usuario> usuarios = new ArrayList<>(entidad.getUsuarioCollection());
            System.out.println("usuarios: " + usuarios.size());
            ArrayList<String> correos = new ArrayList<>();
            for (Usuario usuarioAdmin : usuarios) {
                System.out.println("usuario: " + usuarioAdmin.getNombre());
                if (usuarioAdmin.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 2) {
                    System.out.println("correo: " + usuarioAdmin.getCorreo());
                    correos.add(usuarioAdmin.getCorreo());
                }
            }
            String template = Correo.templatePQRS;
            template = template.replaceAll("$nombre", usuario.getNombre() + " " + usuario.getApellidos());
            template = template.replaceAll("$pqrs", solicitud);
            Correo correo = new Correo("Nuevo PQRS - Appropiarte", template, correos);
            correo.start();

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Nuevo P.Q.R.S");
            logUsuario.setTipo(21);
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
