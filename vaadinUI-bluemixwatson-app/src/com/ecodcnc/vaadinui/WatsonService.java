package com.ecodcnc.vaadinui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class WatsonService {

	private static final String targetURL = "http://bluemixwatson-backend-restservices.mybluemix.net/api/service/searchtips/";
	String response;
	public String getResponseFromBluemixWatson(String forminput) {
	//public static void main(String[] args) {
		
		
		//String forminput1 = "depression";
		// http://localhost:8080/RESTfulExample/json/product/post
		// public static void main(String[] args) {

		try {

			URL restServiceURL = new URL(targetURL);
			// URL url = new
			// URL("http://javaplays-restjava-vaadin.mybluemix.net/api/service/searchtips");
			HttpURLConnection conn = (HttpURLConnection) restServiceURL.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");

			String input = "{\"keyword\"" + ":"  + forminput + "}";
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			String output;
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("HTTP GET Request Failed with Error code :" + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				setResponse(output.toString());
				System.out.println(output);
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

			// }

		}
		return this.response;

	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	
}
