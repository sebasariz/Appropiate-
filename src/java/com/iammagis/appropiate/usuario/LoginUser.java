/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.usuario;

import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.FacturaJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.jpa.ReservaJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicMenu;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class LoginUser extends org.apache.struts.action.Action {

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

        Usuario usuario = (Usuario) form;
        HttpSession session = request.getSession();
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        usuario = usuarioJpaController.login(usuario);
        ActionErrors errores = new ActionErrors();
        if (usuario == null) {
            errores.add("register", new ActionMessage("erros.noPasswordNoUser"));
            saveErrors(request, errores);
            return mapping.findForward("fail");
        } else {
            session.setAttribute("usuario", usuario);
            //setting menu 
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            String content = "";

            Entidad entidad = null;

            ArrayList<CampanaComunicados> campanaComunicadoses = new ArrayList<>();
            ArrayList<Evento> eventos = new ArrayList<>();
            ArrayList<Encuesta> encuestas = new ArrayList<>();
            ArrayList<LogUsuario> logUsuarios = null;
            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            PqrJpaController pqrJpaController = new PqrJpaController(manager);
            ReservaJpaController reservaJpaController = new ReservaJpaController(manager);
            FacturaJpaController facturaJpaController = new FacturaJpaController(manager);
            JSONArray jSONArray = new JSONArray();
            int pqrsActivos = 0;
            int reservasPendientes = 0;
            int pagosPendientes = 0;

            JSONObject jSONObjectEncuesta = new JSONObject();
            JSONObject jSONObjectEvento = new JSONObject();

            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    content = "/contenedor/contentRoot.jsp";
                    session.setAttribute("entidades", entidadJpaController.findEntidadEntities());
                    if (!entidadJpaController.findEntidadEntities().isEmpty()) {
                        session.setAttribute("entidadGlobal", entidadJpaController.findEntidadEntities().get(0));
                        entidad = entidadJpaController.findEntidadEntities().get(0);

                        //cargamos los indicadores
                        pqrsActivos = pqrJpaController.getPQRSActivoByEntidad(entidad.getIdentidad()).size();
                        reservasPendientes = reservaJpaController.getReservasPendientes(entidad.getIdentidad()).size();
                        pagosPendientes = facturaJpaController.getFacturasPendientes(entidad.getIdentidad()).size();
                        //cargamos las graficas 
                        encuestas = new ArrayList<>(entidad.getEncuestaCollection());
                        eventos = new ArrayList<>(entidad.getEventoCollection());
                        campanaComunicadoses = new ArrayList<>(entidad.getCampanaComunicadosCollection());
                        //obtenemos las graficas iniciales
                        if (!encuestas.isEmpty()) {
                            jSONObjectEncuesta = GetDynamicTable.getGraficaEncuesta(encuestas.get(0));
                        }
                        if (!eventos.isEmpty()) {
                            jSONObjectEvento = GetDynamicTable.getGraficaEvento(eventos.get(0));
                        }
                    }

                    //cargamos la tabla de actividades
                    logUsuarios = logUsuarioJpaController.getLogsFromEntidad(entidad.getIdentidad());
//                    logUsuarios = logUsuarioJpaController.getLogsFromEntidadRoot();
                    jSONArray = GetDynamicTable.getLogsTable(logUsuarios);

                    break;
                case 2:
                    //Administrador 
                    session.setAttribute("entidades", usuario.getEntidadCollection());
                    if (!usuario.getEntidadCollection().isEmpty()) {
                        ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection()); 
                        session.setAttribute("entidadGlobal", entidads.get(0));
                        entidad = entidads.get(0);

                        //cargamos los indicadores
                        pqrsActivos = pqrJpaController.getPQRSActivoByEntidad(entidad.getIdentidad()).size();
                        reservasPendientes = reservaJpaController.getReservasPendientes(entidad.getIdentidad()).size();
                        pagosPendientes = facturaJpaController.getFacturasPendientes(entidad.getIdentidad()).size();
                        //cargamos las graficas 
                        encuestas = new ArrayList<>(entidad.getEncuestaCollection());
                        eventos = new ArrayList<>(entidad.getEventoCollection());
                        campanaComunicadoses = new ArrayList<>(entidad.getCampanaComunicadosCollection());

                        if (!encuestas.isEmpty()) {
                            jSONObjectEncuesta = GetDynamicTable.getGraficaEncuesta(encuestas.get(0));
                        }
                        if (!eventos.isEmpty()) {
                            jSONObjectEvento = GetDynamicTable.getGraficaEvento(eventos.get(0));
                        }
                    }
                    content = "/contenedor/contentAdministrador.jsp";

                    //cargamos la tabla de actividades
                    logUsuarios = logUsuarioJpaController.getLogsFromEntidad(entidad.getIdentidad());
                    jSONArray = GetDynamicTable.getLogsTable(logUsuarios);

                    break;
                case 3:
                    errores.add("register", new ActionMessage("erros.noPasswordNoUser"));
                    saveErrors(request, errores);
                    return mapping.findForward("fail");
                case 4:
                default:
                    break;
            }
            request.setAttribute("contenido", content);
            request.setAttribute("grid", jSONArray);
            //indicadores
            request.setAttribute("pqrsActivos", pqrsActivos);
            request.setAttribute("reservasPendientes", reservasPendientes);
            request.setAttribute("pagosPendientes", pagosPendientes);
            //graficas
            request.setAttribute("encuestas", encuestas);
            request.setAttribute("eventos", eventos);
            request.setAttribute("campanas", campanaComunicadoses);
            //charts
            if (jSONObjectEncuesta.has("array")) {
                request.setAttribute("char_encuesta", jSONObjectEncuesta.getJSONArray("array"));
            }
            if (jSONObjectEncuesta.has("envios")) {
                request.setAttribute("envios_encuesta", jSONObjectEncuesta.getInt("envios"));
            } else {
                request.setAttribute("envios_encuesta", 0);
            }
            if (jSONObjectEncuesta.has("aperturas")) {
                request.setAttribute("aperturas_encuesta", jSONObjectEncuesta.getInt("aperturas"));
            } else {
                request.setAttribute("aperturas_encuesta", 0);
            }

            if (jSONObjectEvento.has("array")) {
                request.setAttribute("char_evento", jSONObjectEvento.getJSONArray("array"));
            }
            if (jSONObjectEvento.has("envios")) {
                request.setAttribute("envios_evento", jSONObjectEvento.getInt("envios"));
            } else {
                request.setAttribute("envios_evento", 0);
            }
            if (jSONObjectEvento.has("aperturas")) {
                request.setAttribute("aperturas_evento", jSONObjectEvento.getInt("aperturas"));
            } else {
                request.setAttribute("aperturas_evento", 0);
            }

            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Ingresando a plataforma.");
            logUsuario.setTipo(36);
            logUsuario.setTipo(1);
            logUsuarioJpaController.create(logUsuario);

            String menu = GetDynamicMenu.getMenu2(usuario);
            session.setAttribute("menu", menu);
            return mapping.findForward(SUCCESS);

        }

    }
}
