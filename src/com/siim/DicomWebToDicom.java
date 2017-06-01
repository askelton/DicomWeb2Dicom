/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siim;

import java.util.Map;

/**
 *
 * @author Alejandro Carrera
 */
public class DicomWebToDicom {
    
    static private String AE = "DCM4CHEE";
    static private String IP = "localhost";

    public static String getAE() {
        return AE;
    }

    public static void setAE(String AE) {
        DicomWebToDicom.AE = AE;
    }
    
    public static void doDcmQr(Map<String, String> map){
        
        //dcmqr DCM4CHEE@ip_address:1112 -qDicomTag=Value
        StringBuilder sb = new StringBuilder();
        sb.append("dcmqr ");
        sb.append(AE);
        sb.append("@"+IP+":11112 ");
        
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("-q"+key+"="+value+" ");
        }
        
        String cmd = sb.toString();
        
        System.out.println(cmd);
      }
    
}
