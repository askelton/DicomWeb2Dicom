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
import org.dcm4che2.net.ConfigurationException;
import org.dcm4che2.tool.dcmqr.DcmQR;
import org.dcm4che2.tool.dcmqr.DcmQR.QueryRetrieveLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alejandro Carrera
 */
public class DicomWebToDicom {
    
	static private String AE = "DCM4CHEE";
    static private String IP = "192.168.56.101";

    public static String doDcmQr(Map<String, String> map){
    	String s = "";
    	
        try {
        	System.out.println("Create DCMQR");
        	DcmQR dcmqr = new DcmQR(AE);
        
        	dcmqr.setCalling(AE);
        	dcmqr.setCalledAET(AE, true);
        	dcmqr.setRemoteHost(IP);
        	dcmqr.setRemotePort(11112);
        	dcmqr.configureTransferCapability(true);
        	dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.STUDY);
        	
        	System.out.println("Apply Options");
        	for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                dcmqr.addMatchingKey(Tag.toTagPath(key), value);
        	}
        	
        	dcmqr.start();
        	System.out.println("started");
        	dcmqr.open();
        	System.out.println("opened");
        	List<DicomObject> result = dcmqr.query();
        	System.out.println("Complete Query");
        	       	
        	System.out.println(result.size());
        	
        	s = result.toString();
        	System.out.println(s);
        	
        	dcmqr.close();
        	dcmqr.stop();			
		} catch (IOException | InterruptedException | ConfigurationException e) {
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
