/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siim;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.tool.dcmqr.DcmQR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alejandro Carrera
 */
public class DicomWebToDicom {
    
	private static final Logger LOG = LoggerFactory.getLogger(DcmQR.class);
    static private String AE = "DCM4CHEE";
    static private String IP = "localhost";

    public static String doDcmQr(Map<String, String> map){
    	String s = "";
    	
        try {
        	System.out.println("Pre");
        	DcmQR dcmqr = new DcmQR(AE);
        
        	System.out.println("PreStart");
        	dcmqr.start();
        	
        	dcmqr.setCalling(AE);
        	dcmqr.setLocalHost(IP);
        	dcmqr.setLocalPort(11112);
        	
        	System.out.println("PreOpts");
        	for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                dcmqr.addMatchingKey(Tag.toTagPath(key), value);
        	}
            
        	System.out.println(dcmqr.toString());
        	        
        	List<DicomObject> list = dcmqr.query();
        	s = list.toString();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
 
        return s;
        
      }

    public static String getAE() {
        return AE;
    }

    public static void setAE(String AE) {
        DicomWebToDicom.AE = AE;
    }
    
	public static String getIP() {
		return IP;
	}

	public static void setIP(String iP) {
		IP = iP;
	}
    
}
