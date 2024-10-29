/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.support;

import com.iammagis.appropiate.beans.Actividad;
import com.iammagis.appropiate.beans.Archivo;
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.FacturaTemplate;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Reserva;
import com.iammagis.appropiate.beans.Transaccion;
import com.iammagis.appropiate.beans.Usuario;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class GetDynamicTable {

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    static DecimalFormat decimalFormat = new DecimalFormat("##0.00");

    public static JSONArray getUsersTable(ArrayList<Usuario> usuarios) throws JSONException, UnsupportedEncodingException {
        JSONArray datosCompletos = new JSONArray();
        for (Usuario usuario : usuarios) {
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            JSONArray data = new JSONArray();
            data.put(usuario.getNombre());
            data.put(usuario.getApellidos());
            data.put(usuario.getCorreo());
            data.put(usuario.getTipoUsuarioIdtipoUsuario().getNombre());
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());
            if (!entidads.isEmpty()) {
                String str = Arrays.toString(entidads.toArray());
                data.put(str);
            } else {
                data.put("N/A");
            }
            data.put("<td class=\"text-center\">"
                    + "<i class=\"fa fa-table fa-3x\" title=\"Grupos de interes\" onclick=\"LoadGruposDeInteresJson(" + usuario.getIdusuario() + ")\"></i>"
                    + "<i class=\"fa fa-edit fa-3x\" title=\"Editar usuario\" onclick=\"LoadUsuarioJson(" + usuario.getIdusuario() + ")\"></i>"
                    + "<i class=\"fa fa-remove fa-3x\" title=\"Eliminar usuario\" onclick=\"EliminarUsuario(" + usuario.getIdusuario() + ")\"></i>"
                    + "</td>");

            datosCompletos.put(data);
        }
        return datosCompletos;
    }

    public static JSONArray getEntidades(ArrayList<Entidad> entidads) throws JSONException, UnsupportedEncodingException {
        JSONArray datosCompletos = new JSONArray();
        for (Entidad entidad : entidads) {
            JSONArray data = new JSONArray();
            data.put(entidad.getNombre());
            data.put(entidad.getContacto());
            data.put(entidad.getDireccion());
            data.put(entidad.getTelefono());
            data.put(entidad.getNit());
            data.put(entidad.getIdentificador());

            data.put("<td class=\"text-center\">"
                    + "<i class=\"fa fa-edit fa-3x\" title=\"Editar usuario\" onclick=\"LoadEntidadJson(" + entidad.getIdentidad() + ")\"></i>"
                    + "<i class=\"fa fa-remove fa-3x\" title=\"Eliminar usuario\" onclick=\"EliminarEntidad(" + entidad.getIdentidad() + ")\"></i>"
                    + "</td>");

            datosCompletos.put(data);

        }
        return datosCompletos;
    }

    public static JSONArray getUsuariosAsignacionModulos(ArrayList<Usuario> usuarios) throws JSONException, UnsupportedEncodingException {
        JSONArray datosCompletos = new JSONArray();
        for (Usuario usuario : usuarios) {
            JSONArray data = new JSONArray();
            data.put(usuario.getNombre());
            data.put(usuario.getApellidos());
            data.put(usuario.getCorreo());
            data.put("<td class=\"text-center\">"
                    + "<i class=\"fa fa-edit fa-3x\" title=\"Editar usuario\" onclick=\"LoadSubmodulosUsuario(" + usuario.getIdusuario() + ")\"></i>"
                    + "</td>");
            datosCompletos.put(data);

        }
        return datosCompletos;
    }

    public static JSONArray getEventos(ArrayList<Evento> eventos) throws JSONException, UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (Evento evento : eventos) {
            JSONObject jSONObject = new JSONObject();
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            jSONObject.put("id", evento.getIdevento());
            jSONObject.put("nombre", evento.getNombre());
            jSONObject.put("fecha", simpleDateFormat.format(new Date(evento.getFecha().longValue())));
            if (evento.getEstado() == 1) {
                jSONObject.put("estado", "Activo");
            } else if (evento.getEstado() == 2) {
                jSONObject.put("estado", "Desactivado");
            }
            jSONObject.put("img", evento.getImagen());
            jSONObject.put("informacion", evento.getInformacion());

            //usuarios confirmados
            int numeroAsistentes = 0;
            int numeroPosibles = 0;
            ArrayList<EventoHasUsuario> eventoHasUsuarios = new ArrayList<>(evento.getEventoHasUsuarioCollection());
            for (EventoHasUsuario eventoHasUsuario : eventoHasUsuarios) {
                if (eventoHasUsuario.getRespuesta() == 1) {
                    numeroPosibles++;
                } else if (eventoHasUsuario.getRespuesta() == 2) {
                    numeroAsistentes++;
                }
            }
            jSONObject.put("numeroAsistentes", numeroAsistentes);
            jSONObject.put("numeroPosibles", numeroPosibles);

            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getCampanasComunicados(ArrayList<CampanaComunicados> campanaComunicadoses) throws JSONException, UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (CampanaComunicados campanaComunicados : campanaComunicadoses) {
            JSONObject jSONObject = new JSONObject();
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            jSONObject.put("id", campanaComunicados.getIdcampana());
            jSONObject.put("nombre", campanaComunicados.getNombre());
            jSONObject.put("fecha", simpleDateFormat.format(new Date(campanaComunicados.getFecha().longValue())));
            jSONObject.put("informacion", campanaComunicados.getInformacion());
            //archivos
            jSONObject.put("archivos", campanaComunicados.getArchivoCollection().size());
            int vistos = 0;
            vistos = campanaComunicados.getCampanaComunicadosHasUsuarioCollection().stream().filter((campanaComunicadosHasUsuario) -> (campanaComunicadosHasUsuario.getRespuesta() != 0)).map((_item) -> 1).reduce(vistos, Integer::sum);
            jSONObject.put("aperturas", vistos);

            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getEncuestas(ArrayList<Encuesta> encuestas) throws JSONException, UnsupportedEncodingException {
        JSONArray datosCompletos = new JSONArray();
        for (Encuesta encuesta : encuestas) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", encuesta.getIdencuesta());
            jSONObject.put("nombre", encuesta.getNombre());
            jSONObject.put("imagen", encuesta.getImagen());
            jSONObject.put("preguntas", encuesta.getPreguntaCollection().size());
            if (encuesta.getEstado() == 1) {
                jSONObject.put("estado", "Activo");
            } else if (encuesta.getEstado() == 2) {
                jSONObject.put("estado", "Inactivo");
            }
            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getPQRS(ArrayList<Pqr> pqrs) throws JSONException, UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (Pqr pqr : pqrs) {

            JSONObject jSONObject = new JSONObject();
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO 
            jSONObject.put("id", pqr.getIdpqr());
            jSONObject.put("fecha", simpleDateFormat.format(new Date(pqr.getFecha().longValue())));
            jSONObject.put("usuario", pqr.getUsuarioIdusuario().getNombre() + " " + pqr.getUsuarioIdusuario().getApellidos());
            String tipo = "";
            if (null != pqr.getTipo()) {
                switch (pqr.getTipo()) {
                    case 1:
                        tipo = "Petición";
                        break;
                    case 2:
                        tipo = "Queja";
                        break;
                    case 3:
                        tipo = "Reclamo";
                        break;
                    case 4:
                        tipo = "Solicitud";
                        break;
                    default:
                        break;
                }
            }
            jSONObject.put("tipo", tipo);
            if (null != pqr.getEstado()) {
                switch (pqr.getEstado()) {
                    case 1:
                        jSONObject.put("estado", "Espera");
                        break;
                    case 2:
                        jSONObject.put("estado", "Proceso");
                        break;
                    case 3:
                        jSONObject.put("estado", "Finalizado");
                        break;
                    default:
                        break;
                }
            }
            jSONObject.put("solicitud", pqr.getPqr());
            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static String getLast20(ArrayList<Pqr> pqrs) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String pqrsString = "";
        for (Pqr pqr : pqrs) {
            String tipo = "";
            if (null != pqr.getTipo()) {
                switch (pqr.getTipo()) {
                    case 1:
                        tipo = "Petición";
                        break;
                    case 2:
                        tipo = "Queja";
                        break;
                    case 3:
                        tipo = "Reclamo";
                        break;
                    default:
                        break;
                }
            }
            String estadoString = "";
            if (pqr.getEstado() == 1) {
                estadoString = "Activo";
            } else if (pqr.getEstado() == 2) {
                estadoString = "Archivado";
            }
            pqrsString += "<tr>"
                    + "<td><input type=\"checkbox\" onclick=\"loadPqrs(" + pqr.getIdpqr() + ")\"></td>"
                    + "<td>" + pqr.getUsuarioIdusuario().getNombre() + " " + pqr.getUsuarioIdusuario().getApellidos() + "</td>"
                    + "<td>" + simpleDateFormat.format(new Date(pqr.getFecha().longValue())) + "</td>"
                    + "<td>" + tipo + "</td>"
                    + "<td>" + estadoString + "</td>"
                    + "</tr>";
        }
        return pqrsString;

    }

    public static String getCampanasLogin(ArrayList<CampanaComunicados> campanaComunicadoses) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String campanasString = "";
        for (CampanaComunicados campanaComunicados : campanaComunicadoses) {

            campanasString += "<tr>"
                    + "<td><input type=\"checkbox\" onclick=\"loadCampana(" + campanaComunicados.getIdcampana() + ")\"></td>"
                    + "<td>" + campanaComunicados.getNombre() + "</td>"
                    + "<td>" + simpleDateFormat.format(new Date(campanaComunicados.getFecha().longValue())) + "</td>"
                    + "<td>0</td>"
                    + "</tr>";

        }
        return campanasString;
    }

    public static String getEventosLogin(ArrayList<Evento> eventos) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String EventosString = "";
        for (Evento evento : eventos) {

            EventosString += "<tr>"
                    + "<td><input type=\"checkbox\" onclick=\"loadEvento(" + evento.getIdevento() + ")\"></td>"
                    + "<td>" + evento.getNombre() + "</td>"
                    + "<td>" + simpleDateFormat.format(new Date(evento.getFecha().longValue())) + "</td>"
                    + "<td>0</td>"
                    + "</tr>";

        }
        return EventosString;
    }

    public static String getEncuestasLogin(ArrayList<Encuesta> encuestas) throws UnsupportedEncodingException {
        String EncuestaString = "";
        for (Encuesta encuesta : encuestas) {
            EncuestaString = "<tr>"
                    + "<td><input type=\"checkbox\"></td>"
                    + "<td>" + encuesta.getNombre() + "</td>"
                    + "<td>0</td>"
                    + "<td><a>Resumen</a></td>"
                    + "<td><a>Detalle</a></td>"
                    + "</tr>";
        }

        return EncuestaString;
    }

    public static JSONObject getResultadoEncuesta(ArrayList<Pregunta> preguntas) throws JSONException, UnsupportedEncodingException {
        JSONArray datosCompletos = new JSONArray();
        for (Pregunta pregunta : preguntas) {

            JSONObject jSONObject = new JSONObject();
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            JSONArray data = new JSONArray();
            data.put(pregunta.getNombre());
            if (null != pregunta.getTipo()) {
                switch (pregunta.getTipo()) {
                    case 1:
                        data.put("Si/No");
                        break;
                    case 2:
                        data.put("Abierta");
                        break;
                    case 3:
                        data.put("Escala");
                        break;
                    default:
                        break;
                }
            }
            data.put(pregunta.getRespuestaCollection().size() + "^javascript:LoadPersonasResultado(" + pregunta.getIdpregunta() + ")");
            data.put("Resumen^javascript:LoadResumenResultado(" + pregunta.getIdpregunta() + ")");
            data.put("Resultado detalle^javascript:LoadResultadoDetalle(" + pregunta.getIdpregunta() + ")");

            jSONObject.put("id", pregunta.getIdpregunta());
            jSONObject.put("data", data);
            datosCompletos.put(jSONObject);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("rows", datosCompletos);
        return jSONObject;
    }

    public static String getEventosResultado(ArrayList<Evento> eventos) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String json = "";
        for (Evento evento : eventos) {
            json += "<li class=\"gallery-item\" data-src=\"img/" + evento.getImagen() + "\" onclick=\"loadEvento(" + evento.getIdevento() + ")\">"
                    + "                <div class=\"thumbnail-inner\">"
                    + "                    <img style=\"min-width: 300px;min-height: 300px;\" src=\"img/" + evento.getImagen() + "\" alt=\"\" class=\"img-responsive\">"
                    + "                    <div class=\"thumbnail-caption\">"
                    + "                        <div class=\"caption-inner font-header\">"
                    + "                            <span class=\"thumbnail-name\">" + evento.getNombre() + "</span>"
                    + "                             <div class=\"font-12\">" + simpleDateFormat.format(new Date(evento.getFecha().longValue())) + "</div>"
                    + "                        </div>"
                    + "                    </div> "
                    + "                </div> "
                    + "            </li>";
        }
        return json;
    }

    public static String getEventoUsuarioTable(ArrayList<EventoHasUsuario> eventoHasUsuarios) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String table = "";
        for (EventoHasUsuario eventoHasUsuario : eventoHasUsuarios) {
            table += "<tr>"
                    + "<td>" + eventoHasUsuario.getUsuario().getNombre() + " " + eventoHasUsuario.getUsuario().getApellidos() + "</td>"
                    + "<td>" + simpleDateFormat.format(new Date(eventoHasUsuario.getFecha().longValue())) + "</td>"
                    + "</tr>";
        }

        return table;

    }

    public static String getComentariosTable(ArrayList<Comentario> comentarios) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String table = "";
        for (Comentario comentario : comentarios) {
            table += "<tr>"
                    + "<td>" + comentario.getUsuarioIdusuario().getNombre() + " " + comentario.getUsuarioIdusuario().getApellidos() + "</td>"
                    + "<td>" + simpleDateFormat.format(new Date(comentario.getFecha().longValue())) + "</td>"
                    + "<td>" + comentario.getComentario() + "</td>"
                    + "</tr>";
        }

        return table;
    }

    public static String getArchivosTable(ArrayList<Archivo> archivos) {
        String table = "";
        table = archivos.stream().map((archivo) -> "<tr>"
                + "<td> <a href='" + archivo.getRuta() + "'>Descargar</a></td>"
                + "</tr>").reduce(table, String::concat);

        return table;
    }

    public static JSONObject getResultadoCampanas(ArrayList<CampanaComunicadosHasUsuario> arrayList) throws JSONException, UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuario : arrayList) {

            JSONObject jSONObject = new JSONObject();
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            JSONArray data = new JSONArray();
            data.put(campanaComunicadosHasUsuario.getUsuario().getNombre() + " " + campanaComunicadosHasUsuario.getUsuario().getApellidos());
            data.put(simpleDateFormat.format(new Date(campanaComunicadosHasUsuario.getFecha().longValue())));

            jSONObject.put("id", campanaComunicadosHasUsuario.getUsuario().getIdusuario());
            jSONObject.put("data", data);
            datosCompletos.put(jSONObject);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("rows", datosCompletos);
        return jSONObject;
    }

    public static JSONArray getLocaciones(ArrayList<Locacion> locacions) throws JSONException {
        JSONArray datosCompletos = new JSONArray();
        for (Locacion locacion : locacions) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", locacion.getIdlocacion());
            jSONObject.put("nombre", locacion.getNombre());
            jSONObject.put("imagen", locacion.getImagen());
            jSONObject.put("descripcion", locacion.getDescripcion());
            jSONObject.put("responsable", locacion.getResponsable());
            jSONObject.put("reservas", locacion.getReservaCollection().size());
            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getReservas(ArrayList<Reserva> reservas) throws UnsupportedEncodingException, JSONException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (Reserva reserva : reservas) {
            SimpleDateFormat simpleDateFormatGMT = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            simpleDateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT-5"));

            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", reserva.getIdreserva());
            jSONObject.put("idLocacion", reserva.getLocacionIdlocacion().getIdlocacion());
            jSONObject.put("idUsuario", reserva.getUsuarioIdusuario().getIdusuario());
            jSONObject.put("start", simpleDateFormatGMT.format(new Date(reserva.getFecha().longValue())));
            jSONObject.put("title", reserva.getLocacionIdlocacion().getNombre() + "( " + reserva.getUsuarioIdusuario().getNombre() + " " + reserva.getUsuarioIdusuario().getApellidos() + " )");
            jSONObject.put("estado", reserva.getEstado());
            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getActividades(ArrayList<Actividad> actividads) throws JSONException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (Actividad actividad : actividads) {
            SimpleDateFormat simpleDateFormatGMT = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            simpleDateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT-5"));

            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", actividad.getIdactividad());
            jSONObject.put("actividad", actividad.getActividad());
            jSONObject.put("responsable", actividad.getResponsable());
            jSONObject.put("start", simpleDateFormatGMT.format(new Date(actividad.getFecha().longValue())));
            jSONObject.put("title", actividad.getActividad() + "( " + actividad.getResponsable() + " )");
            jSONObject.put("fecha", simpleDateFormatGMT.format(new Date(actividad.getFecha().longValue())));
            jSONObject.put("correo", actividad.getCorreoResponsable());
            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getTableroActividades(ArrayList<Actividad> actividads) throws JSONException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (Actividad actividad : actividads) {
            SimpleDateFormat simpleDateFormatGMT = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            simpleDateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT-5"));

            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", actividad.getIdactividad());
            if (actividad.getActividad().length() > 30) {
                jSONObject.put("actividad", actividad.getActividad().substring(0, 30));
            } else {
                jSONObject.put("actividad", actividad.getActividad());
            }
            jSONObject.put("responsable", actividad.getResponsable());
            jSONObject.put("start", simpleDateFormatGMT.format(new Date(actividad.getFecha().longValue())));
            jSONObject.put("title", actividad.getActividad() + "( " + actividad.getResponsable() + " )");
            jSONObject.put("fecha", simpleDateFormatGMT.format(new Date(actividad.getFecha().longValue())));
            jSONObject.put("correo", actividad.getCorreoResponsable());
            datosCompletos.put(jSONObject);
        }
        return datosCompletos;
    }

    public static JSONArray getFacturaTemplate(ArrayList<FacturaTemplate> facturaTemplates) {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (FacturaTemplate facturaTemplate : facturaTemplates) {
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            JSONArray data = new JSONArray();
            data.put(facturaTemplate.getNombre());
            data.put(simpleDateFormat.format(new Date(facturaTemplate.getFechaCreacion().longValue())));
            data.put(simpleDateFormat.format(new Date(facturaTemplate.getFechaModificado().longValue())));
            if (facturaTemplate.getFechaActualizacion() != null) {
                data.put(simpleDateFormat.format(new Date(facturaTemplate.getFechaActualizacion().longValue())));
            } else {
                data.put(0);
            }
            data.put("<td class=\"text-center\">"
                    + "<i class=\"fa fa-file fa-3x\" title=\"Previsualizar\" onclick=\"PrevisualizarPlantilla(" + facturaTemplate.getIdfacturaTemplate() + ")\"></i>"
                    + "<i class=\"fa fa-edit fa-3x\" title=\"Editar plantilla\" onclick=\"LoadPlantillaJson(" + facturaTemplate.getIdfacturaTemplate() + ")\"></i>"
                    + "<i class=\"fa fa-remove fa-3x\" title=\"Eliminar plantilla\" onclick=\"EliminarPlantilla(" + facturaTemplate.getIdfacturaTemplate() + ")\"></i>"
                    + "</td>");

            datosCompletos.put(data);
        }
        return datosCompletos;
    }

    public static JSONArray getFacturas(ArrayList<Factura> facturas) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (Factura factura : facturas) {
            //AQUI SE ARMA LA TABLA DONDE SE MUESTRA LA RESPECTIVA INFORMACION DE EL  USUARIO
            JSONArray data = new JSONArray();
            data.put(factura.getUsuarioIdusuario().getNombre() + " " + factura.getUsuarioIdusuario().getApellidos() + " (" + factura.getUsuarioIdusuario().getCorreo() + ")");
            data.put(factura.getReferencia());
            data.put(simpleDateFormat.format(new Date(factura.getFecha().longValue())));
            data.put(decimalFormat.format(factura.getValor()));
            data.put(decimalFormat.format(factura.getValorPagado()));

            if (null != factura.getEstado()) {
                switch (factura.getEstado()) {
                    case 1:
                        //pendiente
                        data.put("<td class=\"text-center\"><label class=\"label label-info\">Pendiente de pago</label></td>");
                        break;
                    case 2:
                        //pagado
                        data.put("<td class=\"text-center\"><label class=\"label label-success\">Papgado</label></td>");
                        break;
                    case 3:
                        //comprobando pago
                        data.put("<td class=\"text-center\"><label class=\"label label-warning\">Comprobando pago</label></td>");
                        break;
                    case 4:
                        //rechazado
                        data.put("<td class=\"text-center\"><label class=\"label label-danger\">Rechazado</label></td>");
                        break;
                    default:
                        break;
                }
            }

            data.put("<td class=\"text-center\">"
                    + "<i class=\"fa fa-check fa-2x\" title=\"Marcar como paga\" onclick=\"MarcarPaga(" + factura.getIdfactura() + ")\"></i>"
                    + "<i class=\"fa fa-edit fa-2x\" title=\"Enviar recordatorio\" onclick=\"LoadFacturaJson(" + factura.getIdfactura() + ")\"></i>"
                    + "<i class=\"fa fa-repeat fa-2x\" title=\"Enviar recordatorio\" onclick=\"RecordarFactura(" + factura.getIdfactura() + ")\"></i>"
                    + "<i class=\"fa fa-remove fa-2x\" title=\"Eliminar factura\" onclick=\"EliminarFactura(" + factura.getIdfactura() + ")\"></i>"
                    + "</td>");

            datosCompletos.put(data);
        }
        return datosCompletos;
    }

    public static JSONArray getGruposDeInteres(ArrayList<GrupoInteres> grupoIntereses) {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (GrupoInteres grupoInteres : grupoIntereses) {
            JSONArray data = new JSONArray();
            data.put(grupoInteres.getNombre());
            data.put(simpleDateFormat.format(new Date(grupoInteres.getFecha().longValue())));
            data.put(grupoInteres.getUsuarioCollection().size());
            data.put("<td class=\"text-center\">"
                    + "<i class=\"fa fa-edit fa-3x\" title=\"Editar grupo\" onclick=\"LoadGrupoDeInteresJson(" + grupoInteres.getIdgrupoInteres() + ")\"></i>"
                    + "<i class=\"fa fa-remove fa-3x\" title=\"Eliminar grupo\" onclick=\"EliminarGrupoDeInteres(" + grupoInteres.getIdgrupoInteres() + ")\"></i>"
                    + "</td>");
            datosCompletos.put(data);
        }
        return datosCompletos;
    }

    public static JSONArray getGruposTable(ArrayList<GrupoInteres> grupoIntereses, ArrayList<GrupoInteres> grupoInteresesEntidad) {
        JSONArray datosCompletos = new JSONArray();
        for (GrupoInteres grupoInteresEntidad : grupoInteresesEntidad) {
            JSONArray data = new JSONArray();
            data.put(grupoInteresEntidad.getNombre());
            String checked = "";
            if (grupoIntereses.contains(grupoInteresEntidad)) {
                //checked
                checked = "checked";
            }
            data.put("<td class=\"text-center\">"
                    + "<input type=\"checkbox\" name=\"chk1\" " + checked + " id=\"chk\"  onclick=\"slect(this," + grupoInteresEntidad.getIdgrupoInteres() + ")\"/>"
                    + "</td>");
            datosCompletos.put(data);
        }
        return datosCompletos;
    }

    public static JSONArray getLogsTable(ArrayList<LogUsuario> logUsuarios) throws UnsupportedEncodingException {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        JSONArray datosCompletos = new JSONArray();
        for (LogUsuario logUsuario : logUsuarios) {
            JSONArray data = new JSONArray();
            data.put(logUsuario.getUsuarioIdusuario().getNombre() + " " + logUsuario.getUsuarioIdusuario().getApellidos());
            data.put(simpleDateFormat.format(new Date(logUsuario.getFecha().longValue())));
            data.put(logUsuario.getActividad());
            datosCompletos.put(data);
        }
        return datosCompletos;
    }

    public static JSONObject getGraficaEncuesta(Encuesta encuesta) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int vistos = 0;
        int novistos = 0;
        ArrayList<EncuestaHasUsuario> encuestaHasUsuarios = new ArrayList<>(encuesta.getEncuestaHasUsuarioCollection());
        for (EncuestaHasUsuario encuestaHasUsuario : encuestaHasUsuarios) {
            if (encuestaHasUsuario.getRespuesta() == null || encuestaHasUsuario.getRespuesta() == 0) {
                novistos++;
            } else {
                vistos++;
            }
        }
        JSONObject jSONObjectNoVisto = new JSONObject();
        jSONObjectNoVisto.put("label", "No vistos");
        jSONObjectNoVisto.put("data", novistos);
        JSONObject jSONObjectVist = new JSONObject();
        jSONObjectVist.put("label", "Visto");
        jSONObjectVist.put("data", vistos);
        jSONArray.put(jSONObjectNoVisto);
        jSONArray.put(jSONObjectVist);

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("array", jSONArray);
        jSONObject.put("envios", encuestaHasUsuarios.size());
        jSONObject.put("aperturas", vistos);

        return jSONObject;
    }

    public static JSONObject getGraficaEvento(Evento evento) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int vistos = 0;
        int novistos = 0;
        ArrayList<EventoHasUsuario> eventoHasUsuarios = new ArrayList<>(evento.getEventoHasUsuarioCollection());
        for (EventoHasUsuario eventoHasUsuario : eventoHasUsuarios) {
            if (eventoHasUsuario.getRespuesta() == 0) {
                novistos++;
            } else {
                vistos++;
            }
        }
        JSONObject jSONObjectNoVisto = new JSONObject();
        jSONObjectNoVisto.put("label", "No vistos");
        jSONObjectNoVisto.put("data", novistos);
        JSONObject jSONObjectVist = new JSONObject();
        jSONObjectVist.put("label", "Visto");
        jSONObjectVist.put("data", vistos);
        jSONArray.put(jSONObjectNoVisto);
        jSONArray.put(jSONObjectVist);

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("array", jSONArray);
        jSONObject.put("envios", eventoHasUsuarios.size());
        jSONObject.put("aperturas", vistos);

        return jSONObject;
    }

}
