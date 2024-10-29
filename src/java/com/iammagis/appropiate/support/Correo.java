/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.support;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author sebastianarizmendy
 */
public class Correo extends Thread {

    //aqui va el correo
    static PropertiesAcces propertiesAcess = new PropertiesAcces();
    private String mensaje;
    private ArrayList<String> usuarios;
    private String subject;
    private boolean deleteAttach;
    private File attach = null;
    public static String template = "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
            + "<title>Appropiarte</title>"
            + "<style type=\"text/css\">"
            + "body,td,th {\tfont-family: \"Lucida Grande\", \"Lucida Sans Unicode\", sans-serif;\tfont-size: 15px;\tcolor: #999;}"
            + ".titulos {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #999;}"
            + ".titulosB {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;\tfont-weight: bold;}"
            + ".titulosC {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;}"
            + "body {\tbackground-color: #FFF;\tmargin-left: 0px;\tmargin-top: 0px;\tmargin-right: 0px;\tmargin-bottom: 0px;}"
            + ".ingresar {\tbackground-color:#8eae34;\tborder-color:#536060;\tborder-radius:20px;\tborder:1px solid #536060;\tdisplay:inline-block;\tcolor:#ffffff;\tfont-family:arial;\tfont-size:15px;\tfont-weight:bold;\tpadding:6px 24px;\ttext-decoration:none;\ttext-shadow:1px 1px 0px #528ecc;}"
            + ".ingresar:hover {\tbackground-color:#8eae34;\tborder-color:#ffffff;}"
            + ".ingresar:active {\tposition:relative;\ttop:1px;}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"
            + "  <tr>"
            + "    <td><img src=\"" + propertiesAcess.resourcesServer + "/img/mail.png\" width=\"600\" height=\"119\" alt=\"Appropiarte - Bienvenido\" /></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td align=\"center\" class=\"titulos\">Bienvenido a Appropiarte</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td width=\"43\" valign=\"bottom\">Hola</td>"
            + "    <td width=\"517\" valign=\"bottom\" class=\"titulosB\">$nombre</td>"
            + "  </tr>"
            + "   "
            + "</table>"
            + "</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:20px; padding-left:20px; padding-right:20px;\">"
            + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td colspan=\"2\" valign=\"bottom\" class=\"titulosB\">Tus datos de inscripción son:</td>"
            + "    </tr>"
            + "  <tr>"
            + "    <td align=\"right\" valign=\"bottom\" style=\" padding-top:10px; padding-right:10px;\">* Nombre de Usuario:</td>"
            + "    <td valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\"> $usuario</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td align=\"right\" valign=\"bottom\" style=\" padding-top:10px; padding-right:10px;\"> * Contraseña:</td>"
            + "    <td valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\">$password</td>"
            + "  </tr>"
            + "   "
            + "  <tr>"
            + "    <td width=\"33%\" align=\"right\" valign=\"bottom\" style=\" padding-top:10px; padding-right:10px;\">* Email inscrito:</td>"
            + "    <td width=\"67%\" valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\">$email</td>"
            + "    </tr>"
            + "</table>"
            + "    </td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding-left:20px; padding-right:20px;\" align=\"center\" class=\"titulos\">Ingresa a este link para acceder al sistema.</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:10px; padding-left:20px; padding-right:20px; padding-top:10px;\" align=\"center\"><a href=\"" + propertiesAcess.resourcesServer + "\" class=\"ingresar\">Ingresar</a></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"> </td>"
            + "  </tr></table>"
            + "</body>"
            + "</html>";
    
    public static String templateApp = "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
            + "<title>Appropiarte</title>"
            + "<style type=\"text/css\">"
            + "body,td,th {\tfont-family: \"Lucida Grande\", \"Lucida Sans Unicode\", sans-serif;\tfont-size: 15px;\tcolor: #999;}"
            + ".titulos {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #999;}"
            + ".titulosB {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;\tfont-weight: bold;}"
            + ".titulosC {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;}"
            + "body {\tbackground-color: #FFF;\tmargin-left: 0px;\tmargin-top: 0px;\tmargin-right: 0px;\tmargin-bottom: 0px;}"
            + ".ingresar {\tbackground-color:#8eae34;\tborder-color:#536060;\tborder-radius:20px;\tborder:1px solid #536060;\tdisplay:inline-block;\tcolor:#ffffff;\tfont-family:arial;\tfont-size:15px;\tfont-weight:bold;\tpadding:6px 24px;\ttext-decoration:none;\ttext-shadow:1px 1px 0px #528ecc;}"
            + ".ingresar:hover {\tbackground-color:#8eae34;\tborder-color:#ffffff;}"
            + ".ingresar:active {\tposition:relative;\ttop:1px;}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"
            + "  <tr>"
            + "    <td><img src=\"" + propertiesAcess.resourcesServer + "/img/mail.png\" width=\"600\" height=\"119\" alt=\"Appropiarte - Bienvenido\" /></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td align=\"center\" class=\"titulos\">Bienvenido a Appropiarte</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td width=\"43\" valign=\"bottom\">Hola</td>"
            + "    <td width=\"517\" valign=\"bottom\" class=\"titulosB\">$nombre</td>"
            + "  </tr>"
            + "   "
            + "</table>"
            + "</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:20px; padding-left:20px; padding-right:20px;\">"
            + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td colspan=\"2\" valign=\"bottom\" class=\"titulosB\">Tus datos de inscripción son:</td>"
            + "    </tr>"
            + "  <tr>"
            + "    <td align=\"right\" valign=\"bottom\" style=\" padding-top:10px; padding-right:10px;\">* Nombre de Usuario:</td>"
            + "    <td valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\"> $usuario</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td align=\"right\" valign=\"bottom\" style=\" padding-top:10px; padding-right:10px;\"> * Contraseña:</td>"
            + "    <td valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\">$password</td>"
            + "  </tr>"
            + "   "
            + "  <tr>"
            + "    <td width=\"33%\" align=\"right\" valign=\"bottom\" style=\" padding-top:10px; padding-right:10px;\">* Email inscrito:</td>"
            + "    <td width=\"67%\" valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\">$email</td>"
            + "    </tr>"
            + "</table>"
            + "    </td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding-left:20px; padding-right:20px;\" align=\"center\" class=\"titulos\">Ingresa a este link para acceder al sistema.</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:10px; padding-left:20px; padding-right:20px; padding-top:10px;\" align=\"center\"><a href=\"" + propertiesAcess.resourcesServer + "\" class=\"ingresar\">Ingresar</a></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"> </td>"
            + "  </tr></table>"
            + "</body>"
            + "</html>";
    
    public static String templatePQRS = "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
            + "<title>Appropiarte</title>"
            + "<style type=\"text/css\">"
            + "body,td,th {\tfont-family: \"Lucida Grande\", \"Lucida Sans Unicode\", sans-serif;\tfont-size: 15px;\tcolor: #999;}"
            + ".titulos {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #999;}"
            + ".titulosB {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;\tfont-weight: bold;}"
            + ".titulosC {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;}"
            + "body {\tbackground-color: #FFF;\tmargin-left: 0px;\tmargin-top: 0px;\tmargin-right: 0px;\tmargin-bottom: 0px;}"
            + ".ingresar {\tbackground-color:#8eae34;\tborder-color:#536060;\tborder-radius:20px;\tborder:1px solid #536060;\tdisplay:inline-block;\tcolor:#ffffff;\tfont-family:arial;\tfont-size:15px;\tfont-weight:bold;\tpadding:6px 24px;\ttext-decoration:none;\ttext-shadow:1px 1px 0px #528ecc;}"
            + ".ingresar:hover {\tbackground-color:#8eae34;\tborder-color:#ffffff;}"
            + ".ingresar:active {\tposition:relative;\ttop:1px;}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"
            + "  <tr>"
            + "    <td><img src=\"" + propertiesAcess.resourcesServer + "/img/mail.png\" width=\"600\" height=\"119\" alt=\"Appropiarte - PQRS\" /></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td align=\"center\" class=\"titulos\">Nuevo PQRS</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td width=\"43\" valign=\"bottom\">Solicitud de</td>"
            + "    <td width=\"517\" valign=\"bottom\" class=\"titulosB\">$nombre</td>"
            + "  </tr>"
            + "   "
            + "</table>"
            + "</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:20px; padding-left:20px; padding-right:20px;\">"
            + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td colspan=\"2\" valign=\"bottom\" class=\"titulosB\">El nuevo PQRS:</td>"
            + "    </tr>"
            + "  <tr>"
            + "    <td colspan=\"2\" valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\"> $pqrs</td>"
            + "  </tr>"  
            + "</table>"
            + "    </td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding-left:20px; padding-right:20px;\" align=\"center\" class=\"titulos\">Ingresa a este link para acceder al sistema.</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:10px; padding-left:20px; padding-right:20px; padding-top:10px;\" align=\"center\"><a href=\"" + propertiesAcess.resourcesServer + "\" class=\"ingresar\">Ingresar</a></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"> </td>"
            + "  </tr></table>"
            + "</body>"
            + "</html>";
    
    public static String templateActividad = "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
            + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
            + "<title>Appropiarte</title>"
            + "<style type=\"text/css\">"
            + "body,td,th {\tfont-family: \"Lucida Grande\", \"Lucida Sans Unicode\", sans-serif;\tfont-size: 15px;\tcolor: #999;}"
            + ".titulos {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #999;}"
            + ".titulosB {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;\tfont-weight: bold;}"
            + ".titulosC {\tfont-family: \"Lucida Sans Unicode\", \"Lucida Grande\", sans-serif;\tfont-size: 18px;\tcolor: #666;}"
            + "body {\tbackground-color: #FFF;\tmargin-left: 0px;\tmargin-top: 0px;\tmargin-right: 0px;\tmargin-bottom: 0px;}"
            + ".ingresar {\tbackground-color:#8eae34;\tborder-color:#536060;\tborder-radius:20px;\tborder:1px solid #536060;\tdisplay:inline-block;\tcolor:#ffffff;\tfont-family:arial;\tfont-size:15px;\tfont-weight:bold;\tpadding:6px 24px;\ttext-decoration:none;\ttext-shadow:1px 1px 0px #528ecc;}"
            + ".ingresar:hover {\tbackground-color:#8eae34;\tborder-color:#ffffff;}"
            + ".ingresar:active {\tposition:relative;\ttop:1px;}"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">"
            + "  <tr>"
            + "    <td><img src=\"" + propertiesAcess.resourcesServer + "/img/mail.png\" width=\"600\" height=\"119\" alt=\"Appropiarte - ACtividad\" /></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td align=\"center\" class=\"titulos\">Nueva actividad</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td width=\"43\" valign=\"bottom\">Nueva actividad asignada: </td>"
            + "    <td width=\"517\" valign=\"bottom\" class=\"titulosB\">$nombre</td>"
            + "  </tr>"
            + "   "
            + "</table>"
            + "</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:20px; padding-left:20px; padding-right:20px;\">"
            + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
            + "  <tr>"
            + "    <td colspan=\"2\" valign=\"bottom\" class=\"titulosB\">$descripcion</td>"
            + "    </tr>"
            + "  <tr>"
            + "    <td colspan=\"2\" valign=\"bottom\" style=\" padding-top:10px;\" class=\"titulosC\"> $pqrs</td>"
            + "  </tr>"  
            + "</table>"
            + "    </td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding-left:20px; padding-right:20px;\" align=\"center\" class=\"titulos\">Ingresa a este link para acceder al sistema.</td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\" padding-bottom:10px; padding-left:20px; padding-right:20px; padding-top:10px;\" align=\"center\"><a href=\"" + propertiesAcess.resourcesServer + "\" class=\"ingresar\">Ingresar</a></td>"
            + "  </tr>"
            + "  <tr>"
            + "    <td style=\"padding:20px;\"> </td>"
            + "  </tr></table>"
            + "</body>"
            + "</html>";

     
    public boolean reporte;
    public int idHistorico;
    private String context;

    @Override
    public void run() {
        try {
            enviarAlertas();

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public Correo(String subject, String mensaje, ArrayList<String> usuarios) {
        this.mensaje = mensaje;
        this.usuarios = usuarios;
        this.subject = subject;
    }

    private void enviarAlertas()
            throws MessagingException {

        System.out.println("enviando mail");
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", propertiesAcess.SMTP_HOST_PORT);
        props.put("mail.smtps.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        for (int i = 0; i < usuarios.size(); i++) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuarios.get(i)));
        }
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(mensaje, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        if (attach != null) {
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attach);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attach.getName());
            multipart.addBodyPart(messageBodyPart);
        }
        message.setContent(multipart);
        transport.connect(propertiesAcess.SMTP_HOST_NAME, Integer.parseInt(propertiesAcess.SMTP_HOST_PORT), propertiesAcess.SMTP_AUTH_USER,
                propertiesAcess.SMTP_AUTH_PWD);
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
        if (attach != null && deleteAttach) {
            attach.delete();
        }
        System.out.println("Mensajes enviados");

    }

    public static void main(String[] args) {
        ArrayList<String> mail = new ArrayList<>();
        mail.add("sebasariz@iammagis.com");
        Correo correo = new Correo("hola", "que mas", mail);
        try {
            correo.enviarAlertas();
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
