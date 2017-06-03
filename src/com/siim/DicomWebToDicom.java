/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siim;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.net.ConfigurationException;
import org.dcm4che2.tool.dcm2xml.Dcm2Xml;
import org.dcm4che2.tool.dcmqr.DcmQR;

/**
 *
 * @author Alejandro Carrera
 */
public class DicomWebToDicom {

	static private String AE = "DCM4CHEE";
	static private String IP = "192.168.56.101";

	public static String doDcmQr(Map<String, String> map) {
		String s = "";

		try {
			DcmQR dcmqr = new DcmQR(AE);

			dcmqr.setCalling(AE);
			dcmqr.setCalledAET(AE, true);
			dcmqr.setRemoteHost(IP);
			dcmqr.setRemotePort(11112);
			dcmqr.configureTransferCapability(true);
			dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.STUDY);

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (value.length() == 0) {
					dcmqr.addReturnKey(new int[] { Tag.toTag(key) });
				} else if (key.equals("queryLevel")) {
					switch (value) {
					case "patient":
					case "PATIENT":
						dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.PATIENT);
						break;
					case "series":
					case "SERIES":
						dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.SERIES);
						break;
					case "image":
					case "IMAGE":
						dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.IMAGE);
						break;
					case "study":
					case "STUDY":
					default:
						dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.STUDY);
						break;

					}
				} else {
					dcmqr.addMatchingKey(Tag.toTagPath(key), value);
				}
			}

			dcmqr.start();
			dcmqr.open();
			List<DicomObject> result = dcmqr.query();
//			System.out.println("Complete Query");

			s = convert(result);
//			System.out.println("Convert Complete");

			dcmqr.close();
			dcmqr.stop();
		} catch (IOException | InterruptedException | ConfigurationException | TransformerConfigurationException e) {
			e.printStackTrace();
		}

		return s;

	}

	private static String convert(List<DicomObject> result) throws IOException, TransformerConfigurationException {
		String results = "";

		for (DicomObject dobj : result) {
			dobj.putString(Tag.TransferSyntaxUID, VR.UI, "1.2.840.10008.1.2");

			File file = new File("temp.dcm");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			DicomOutputStream dos = new DicomOutputStream(bos);
			dos.writeDicomFile(dobj);
			dos.close();

			File xml = new File("temp.xml");
			Dcm2Xml dcmX = new Dcm2Xml();
			dcmX.convert(file, xml);
			results = readFile(xml);
		}

		return results;

	}

	private static String readFile(File xmlFile) throws IOException {

		Reader fileReader = new FileReader(xmlFile);
		BufferedReader bufReader = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		while (line != null) {
			sb.append(line).append("\n");
			line = bufReader.readLine();
		}

		String xml2String = sb.toString();
		System.out.println(xml2String);

		bufReader.close();

		return xml2String;

	}

}
