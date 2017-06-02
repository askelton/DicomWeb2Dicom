/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siim;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alejandro Carrera
 */
public class DicomMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String, String>();
        //map.put("PatientName", "ABDOMINAL*");
        map.put("StudyInstanceUID", "1.2.124.113532.128.5.1.74.20020123.83838.579855");
        
        DicomWebToDicom.doDcmQr(map);
      }
    
}
