package app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

// Key fixer.io: c2517547854f2c089f40062b9186d251

	public class InternetValutaIO {

	    public static HashMap<String, Double> GetMapInternet() throws IOException { 

	        // Make a URL to the web page
	        URL url = new URL("http://data.fixer.io/api/latest?access_key=c2517547854f2c089f40062b9186d251");

	        // Get the input stream through URL Connection
	        URLConnection con = url.openConnection();
	        InputStream is =con.getInputStream();

	        // Once you have the Input Stream, it's just plain old Java IO stuff.

	        // For this case, since you are interested in getting plain-text web page
	        // I'll use a reader and output the text content to System.out.

	        // For binary content, it's better to directly read the bytes from stream and write
	        // to the target file.


	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        String line = null;
	        
	        JSONParser parser = new JSONParser();
	        
	        HashMap<String, Double> vk = new HashMap<>();

	        // read each line and write to System.out
		       
		     try {   
		        while ((line = br.readLine()) != null) {
		            
		        	System.out.println(line + "\n");
		            
					JSONObject a = (JSONObject) parser.parse(line);
					
					
					
					JSONObject kurser = (JSONObject) parser.parse(a.get("rates").toString());
					System.out.println();
					
					Set<?> t = kurser.keySet();
					
					
					Iterator<?> i2 = t.iterator();
					while (i2.hasNext()) {
						
						String key = (String) i2.next();
						Object value = Double.parseDouble(kurser.get(key).toString());
						vk.put(key, (1/(Double) value));
						System.out.println(key + " = "+ value);
						
					}
					
		        	
//		        	 for (Object o : a) {
//		                    JSONObject valutakurser = (JSONObject) o;
//	
//		                    Long id = (Long) valutakurser.get("NOK");
//		                    System.out.println("NOK : " + id);
//	
//		                    String title = (String) valutakurser.get("SEK");
//		                    System.out.println("SEK : " + title);
//	
//		                    System.out.println("\n");
//		                }
//		        	
		        }
		        return vk;
		     }
		     catch (ParseException e) {
		    	 e.printStackTrace();
		     }
			return null;
	        
	    }
	
	  public void skriveTilFil(String filename, HashMap<String, Double> map) {
		
	    try {
		PrintWriter writer = new PrintWriter(filename);
		for (String valutanavn : map.keySet()) {
			Double kurs = map.get(valutanavn);
			writer.println(valutanavn + "\t" + kurs);
		}
		writer.flush();
		writer.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Kan ikke legge til fil.");
		}
		
	}
		
	public HashMap<String, Double> lesingFraFil() {
		try {
			return GetMapInternet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		InternetValutaIO a1 = new InternetValutaIO();
		HashMap<String, Double> r = a1.lesingFraFil();
		System.out.println(r.get("NOK"));
		System.out.println(r.get("FJD"));
		System.out.println(r.get("EUR"));
		System.out.println(r.get("BTC"));
		
	}

}
