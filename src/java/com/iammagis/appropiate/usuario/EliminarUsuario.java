/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.usuario;

import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Transaccion;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.CampanaComunicadosHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.ComentarioJpaController;
import com.iammagis.appropiate.jpa.EncuestaHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.EventoHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.FacturaJpaController;
import com.iammagis.appropiate.jpa.GrupoInteresJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.jpa.TransaccionJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class EliminarUsuario extends org.apache.struts.action.Action {

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
            int idUsuario = Integer.parseInt(request.getParameter("id"));
            Usuario usuarioRemove = usuarioJpaController.findUsuario(idUsuario);
            List<Pqr> pqrs = new ArrayList<>(usuarioRemove.getPqrCollection());
            PqrJpaController pqrJpaController = new PqrJpaController(manager);
            for (Pqr pqr : pqrs) {
                pqrJpaController.destroy(pqr.getIdpqr());
            }
            EventoHasUsuarioJpaController eventoHasUsuarioJpaController = new EventoHasUsuarioJpaController(manager);
            List<EventoHasUsuario> eventoHasUsuarios = new ArrayList<>(usuarioRemove.getEventoHasUsuarioCollection());
            for (EventoHasUsuario eventoHasUsuario : eventoHasUsuarios) {
                eventoHasUsuarioJpaController.destroy(eventoHasUsuario.getEventoHasUsuarioPK());
            }

            CampanaComunicadosHasUsuarioJpaController campanaComunicadosHasUsuarioJpaController = new CampanaComunicadosHasUsuarioJpaController(manager);
            List<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarios = new ArrayList<>(usuarioRemove.getCampanaComunicadosHasUsuarioCollection());
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuario : campanaComunicadosHasUsuarios) {
                campanaComunicadosHasUsuarioJpaController.destroy(campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK());
            }

            ComentarioJpaController comentarioJpaController = new ComentarioJpaController(manager);
            List<Comentario> comentarios = new ArrayList<>(usuarioRemove.getComentarioCollection());
            for (Comentario comentario : comentarios) {
                comentarioJpaController.destroy(comentario.getIdcomentarioPqr());
            }

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            List<LogUsuario> logUsuarios = new ArrayList<>(usuarioRemove.getLogUsuarioCollection());
            for (LogUsuario logUsuario : logUsuarios) {
                logUsuarioJpaController.destroy(logUsuario.getIdlogUsuario());
            }

            TransaccionJpaController transaccionJpaController = new TransaccionJpaController(manager);
            FacturaJpaController facturaJpaController = new FacturaJpaController(manager);
            ArrayList<Factura> facturas = new ArrayList<>(usuarioRemove.getFacturaCollection());
            for (Factura factura : facturas) {
                ArrayList<Transaccion> transaccions = new ArrayList<>(factura.getTransaccionCollection());
                for (Transaccion transaccion : transaccions) {
                    transaccionJpaController.destroy(transaccion.getIdtransaccion());
                }
                facturaJpaController.destroy(factura.getIdfactura());
            }

            ArrayList<EncuestaHasUsuario> encuestaHasUsuarios = new ArrayList<>(usuarioRemove.getEncuestaHasUsuarioCollection());
            System.out.println("encuestaHasUsuarios. " + encuestaHasUsuarios.size());
            EncuestaHasUsuarioJpaController encuestaHasUsuarioJpaController = new EncuestaHasUsuarioJpaController(manager);
            for (EncuestaHasUsuario encuestaHasUsuario : encuestaHasUsuarios) {
                encuestaHasUsuarioJpaController.destroy(encuestaHasUsuario.getEncuestaHasUsuarioPK());
            }

            usuarioJpaController.destroy(idUsuario);
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            ArrayList<Usuario> usuarios = new ArrayList<>();
            Entidad entidad = (Entidad) session.getAttribute("entidadGlobal");
            entidad=entidadJpaController.findEntidad(entidad.getIdentidad());
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
