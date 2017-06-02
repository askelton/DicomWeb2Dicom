package com.siim;



import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class DICOMwebServlet
 *
 * @author Andrew Skelton
 */
@WebServlet("/DICOMwebServlet")
public class DICOMwebServlet extends HttpServlet {
	static Logger log = Logger.getLogger(DICOMwebServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DICOMwebServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		String requestedString = request.getQueryString();
		if(requestedString==null)
		{	
			response.getWriter().append("No Parameters defined, please add some constraints");
			return;
		}
		String[] pairs = requestedString.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
	        String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
	        if (key.toLowerCase().equals("includefield"))
	        {
	        	query_pairs.put(value, "");
	        }
        	else
        		query_pairs.put(key, value);
	    }
	    
	    String dcmqr = DicomWebToDicom.doDcmQr(query_pairs);
	    
		response.getWriter().append(dcmqr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
