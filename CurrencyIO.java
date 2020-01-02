package app;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyIO implements IOInterface {
	
	public void skriveTilFil(String filename, Map<String, Double> map) {
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
	
	public Map<String, Double> lesingFraFil(String filename) {
		try {
			Scanner reader = new Scanner (new FileReader(filename));
			HashMap<String, Double> valutaKurser = new HashMap<>();
			while (reader.hasNext()) {
				String line = reader.nextLine();
				String[] liste =  line.split("\t");
				String valutaNavn = liste[0];
				Double kurs = Double.parseDouble(liste[1]);
				valutaKurser.put(valutaNavn, kurs);
			}
			reader.close();
			return valutaKurser;
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Klarer ikke å lese fra fil.");
		}
		return null;
	}
}
