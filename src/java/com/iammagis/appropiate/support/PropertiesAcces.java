/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.support;

import java.util.ResourceBundle;

/**
 *
 * @author sebastianarizmendy
 */
public class PropertiesAcces {

    //aqui va los para metros de el mensaje
    private static final String OPTION_FILE_NAME = "com/iammagis/appropiate/server";
    public String SMTP_HOST_NAME = "";
    public String SMTP_HOST_PORT = "";
    public String SMTP_AUTH_USER = "";
    public String SMTP_AUTH_PWD = "";
    public String resourcesServer = "";
    public String FCM_SERVER = "";

    public PropertiesAcces() {
        ResourceBundle pe = ResourceBundle.getBundle(OPTION_FILE_NAME);
        SMTP_HOST_NAME = pe.getString("SMTP_HOST_NAME").trim();
        SMTP_HOST_PORT = pe.getString("SMTP_HOST_PORT").trim();
        SMTP_AUTH_USER = pe.getString("SMTP_AUTH_USER").trim();
        SMTP_AUTH_PWD = pe.getString("SMTP_AUTH_PWD").trim();
        resourcesServer = pe.getString("resourcesServer").trim();
        FCM_SERVER = pe.getString("FCM_SERVER").trim();
    }

}
