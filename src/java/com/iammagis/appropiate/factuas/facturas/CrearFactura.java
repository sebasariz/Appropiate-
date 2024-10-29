/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.factuas.facturas;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.FacturaTemplate;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.FacturaJpaController;
import com.iammagis.appropiate.jpa.FacturaTemplateJpaController;
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
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class CrearFactura extends org.apache.struts.action.Action {

    PropertiesAcces propertiesAcces = new PropertiesAcces();
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
        HttpSession httpSession = request.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            Factura factura = (Factura) form;
            FacturaJpaController facturaJpaController = new FacturaJpaController(manager);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Entidad entidad = (Entidad) httpSession.getAttribute("entidadGlobal");
            factura.setEntidadIdentidad(entidad);
            Usuario usuarioFactura = usuarioJpaController.findUsuario(factura.getIdUsuario());
            factura.setUsuarioIdusuario(usuarioFactura);
            FacturaTemplateJpaController facturaTemplateJpaController = new FacturaTemplateJpaController(manager);
            FacturaTemplate facturaTemplate = facturaTemplateJpaController.findFacturaTemplate(factura.getIdTemplate());
            factura.setFacturaTemplateIdfacturaTemplate(facturaTemplate);
            factura.setEstado(1);
            factura.setValorPagado(0D);
            facturaJpaController.create(factura);

            //enviamos push
            //enviamos la notificacion a todos los que pertenezcan a esa entidad
            System.out.println("falta la notificacion movil y al mail");

//            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "Nueva evento: " + evento.getNombre(), 2/*eventos*/);
            //cargamos los usuarios  
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            entidad = entidadJpaController.findEntidad(entidad.getIdentidad());

            ArrayList<Factura> facturas = new ArrayList<>(entidad.getFacturaCollection());
            jSONObject.put("grid", GetDynamicTable.getFacturas(facturas));

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Creando factura: " + factura.getReferencia());
            logUsuario.setTipo(15);
            logUsuarioJpaController.create(logUsuario);

            ArrayList<String> tokensArray = new ArrayList<>();

            if (usuarioFactura.getDevicesTokens() != null) {
                String devicesTokens = usuarioFactura.getDevicesTokens();
                String[] tokens = devicesTokens.split(",");
                tokensArray.addAll(Arrays.asList(tokens));
            }
            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "Nueva factura: " + factura.getReferencia(), 3/*factura*/);
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
