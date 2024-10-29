/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.support;

import com.iammagis.appropiate.beans.Modulo;
import com.iammagis.appropiate.beans.Submodulo;
import com.iammagis.appropiate.beans.Usuario;
import java.util.ArrayList;

/**
 *
 * @author sebastianarizmendy
 */
public class GetDynamicMenu {

    

    public static String getMenu2(Usuario usuario) {
        String menu = "";
        ArrayList<Submodulo> subModulos = new ArrayList<Submodulo>(usuario.getSubmoduloCollection());
        ArrayList<Modulo> modulos = new ArrayList<Modulo>(usuario.getModuloCollection());
        for (Modulo modulo : modulos) {
            //agregar el submodulo
            menu = menu + "<li class=\"has-submenu\">"
                    + "                        <a href=\"#" + modulo.getIdmodulo()+ "\" data-toggle=\"collapse\" aria-expanded=\"false\">"
                    + "                            <i class=\""+modulo.getIcon()+"\"></i> <span class=\"nav-text\">"+modulo.getNombre()+"</span>"
                    + "                        </a>"
                    + "                        <div class=\"sub-menu collapse secondary list-style-circle\" id=\"" + modulo.getIdmodulo()+ "\">"
                    + "<ul>";
            for (Submodulo subModulo : subModulos) {
                if (subModulo.getModuloIdmodulo().getIdmodulo() == modulo.getIdmodulo()) {
                    //agregamos el submodulo 
                    menu += "<li>"
                            + " <a href=\"" + subModulo.getAccion() + ".appropiate\" class=\"animsition-link\"\">"
                            + subModulo.getNombre()
                            + " </a>"
                            + "</li>";
                }
            }
            menu += " </ul>"
                    + "</div>"
                    + "</li>";
        }
        return menu;
    }

}
