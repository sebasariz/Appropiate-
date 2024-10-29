/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.usuario;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Modulo;
import com.iammagis.appropiate.beans.Submodulo;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.ModuloJpaController;
import com.iammagis.appropiate.jpa.SubmoduloJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.Correo;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class EditarUsuario extends org.apache.struts.action.Action {

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
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Usuario usuarioRegister = (Usuario) form;
            System.out.println("usuarioRegister.getIdusuario(): " + usuarioRegister.getIdusuario());
            Usuario usuarioAnterior = usuarioJpaController.findUsuario(usuarioRegister.getIdusuario());
            usuarioAnterior.setTipoUsuarioIdtipoUsuario(new TipoUsuario(usuarioRegister.getIdTipoUsuario()));
            usuarioAnterior.setNombre(usuarioRegister.getNombre());
            usuarioAnterior.setApellidos(usuarioRegister.getApellidos());
            usuarioAnterior.setPass(usuarioRegister.getPass());

            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            if (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 1
                    && (usuarioAnterior.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 2
                    || usuarioAnterior.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 3)) {
                String entidadString = usuarioRegister.getIdEntidadString();
                JSONArray arrayEntidades = new JSONArray(entidadString);
                ArrayList<Entidad> entidads = new ArrayList<>();
                for (int i = 0; i < arrayEntidades.length(); i++) {
                    Entidad entidad = entidadJpaController.findEntidad(Integer.parseInt(arrayEntidades.getString(i)));
                    entidads.add(entidad);
                }
                usuarioAnterior.setEntidadCollection(entidads);
            }

            if (usuarioAnterior.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 2) {
                //Es el administrador

                //agregamos el modulo 3
                ArrayList<Modulo> modulos = new ArrayList<>();
                modulos.add(new Modulo(1));
                modulos.add(new Modulo(2));
                modulos.add(new Modulo(3));
                modulos.add(new Modulo(4));
                modulos.add(new Modulo(5));
                modulos.add(new Modulo(6));
                modulos.add(new Modulo(7));
                modulos.add(new Modulo(8));
                ArrayList<Submodulo> submodulos = new ArrayList<>();

                submodulos.add(new Submodulo(1));
                submodulos.add(new Submodulo(3));
                submodulos.add(new Submodulo(4));
                submodulos.add(new Submodulo(5));
                submodulos.add(new Submodulo(6));
                submodulos.add(new Submodulo(7));
                submodulos.add(new Submodulo(8));
                submodulos.add(new Submodulo(9));
                submodulos.add(new Submodulo(10));
                submodulos.add(new Submodulo(11));
                submodulos.add(new Submodulo(12));
                submodulos.add(new Submodulo(13));
                submodulos.add(new Submodulo(14));
                submodulos.add(new Submodulo(15));
                submodulos.add(new Submodulo(16));
                submodulos.add(new Submodulo(17));
                submodulos.add(new Submodulo(18)); 

                usuarioAnterior.setModuloCollection(modulos);
                usuarioAnterior.setSubmoduloCollection(submodulos);
            } else if (usuarioAnterior.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 1) {
                //es root asociamos todos los modulos por ahora  
                ModuloJpaController moduloJpaController = new ModuloJpaController(manager);
                SubmoduloJpaController subModuloJpaController = new SubmoduloJpaController(manager);
                usuarioAnterior.setModuloCollection(moduloJpaController.findModuloEntities());
                usuarioAnterior.setSubmoduloCollection(subModuloJpaController.findSubmoduloEntities());
                usuarioAnterior.setEntidadCollection(new ArrayList<>());
            }

            usuarioAnterior = usuarioJpaController.edit(usuarioAnterior);

            //envio de correo
            ArrayList<String> strings = new ArrayList<>();
            strings.add(usuarioRegister.getCorreo());
            String template = Correo.template;
            template = template.replace("$nombre", usuarioAnterior.getNombre() + " " + usuarioAnterior.getApellidos());
            template = template.replace("$usuario", usuarioAnterior.getCorreo());
            template = template.replace("$password", usuarioAnterior.getPass());
            template = template.replace("$email", usuario.getCorreo());
            Correo correo = new Correo("Bienvenido Appropiate", template, strings);
            correo.start();

//            //cargamos los usuarios 
            ArrayList<Usuario> usuarios = new ArrayList<>();
            Entidad entidad = (Entidad) session.getAttribute("entidadGlobal");
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    //obtenemos todos los usuarios por ser root 
                    usuarios = usuarioJpaController.getUsuariosByTipo(1);
                    usuarios.addAll(entidad.getUsuarioCollection());
                    jSONObject.put("grid", GetDynamicTable.getUsersTable(usuarios));
                    break;
                case 2:
                    //Gestor 
                    entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
                    usuarios.addAll(entidad.getUsuarioCollection());
                    jSONObject.put("grid", GetDynamicTable.getUsersTable(usuarios));
                    break;
                default:
                    break;
            }

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
