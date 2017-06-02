/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    
    public static String doDcmQr(Map<String, String> map){
    	String s = "";
    	
        //dcmqr DCM4CHEE@ip_address:1112 -qDicomTag=Value
        StringBuilder sb = new StringBuilder();
        sb.append("dcmqr ");
        sb.append("-L SIIM ");
        sb.append(AE);
        sb.append("@"+IP+":11112 ");
        
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("-q"+key+"="+value+" ");
        }
        
        String cmd = sb.toString();
        
        System.out.println(cmd);
        
        try{
        // Get runtime
        Runtime rt = Runtime.getRuntime();
        // Start a new process: UNIX command ls
        Process process = rt.exec(cmd);
        
        // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output            
            System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read command errors
            System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        return s;
        
      }
    
}
