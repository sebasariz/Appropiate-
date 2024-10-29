/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.support.comparators;

import com.iammagis.appropiate.beans.Comentario;
import java.util.Comparator;

/**
 *
 * @author sebastianarizmendy
 */
public class ComentariosComparatorFecha implements Comparator<Comentario>{

    @Override
    public int compare(Comentario o1, Comentario o2) {
        if(o1.getFecha().longValue()<o2.getFecha().longValue()){
            return -1;
        }else{
            return 1;
        }
    }
    
}
