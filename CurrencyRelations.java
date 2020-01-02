package app;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRelations {	// Her kan man ha ulike kursforhold for å kjøpe eller selge ulike valutaer.
	
	private double baserate = 1; //basisvalutakurs, her satt til 1 NOK. Kan også sette til 100 NOK.
	private Map<String, Double> rates = new HashMap<>();
	private String kurser = "";


	public Map<String, Double> getRates() {
		return rates;
	}

	public void addRate(String currency, double rate) {
		String[] str = currency.split("");
		for (int i = 0; i < str.length; i++) {
			if (!Character.isLetter((str[i].charAt(0)))) {
				throw new IllegalArgumentException("String can only contain letters");
			}
			
		}
		rates.put(currency, rate);
	}
	
	public void removeRate(String currency) {
		if (!rates.containsKey(currency)) {
			throw new IllegalArgumentException("Denne valutaen eksisterer ikke");
		}
		rates.remove(currency);
	}

	public double getBaserate() {
		return baserate;
	}
	
	public double convertCurrency(String fromCurrency, String toCurrency) {
		return (rates.get(fromCurrency))*(baserate/rates.get(toCurrency));	//Gjør om til NOK også ganger med toCurrency. 
				
	}
	
	public CurrencyRelations(double baserate, Map<String, Double> rates) {
		this.baserate = baserate;
		this.rates = rates;
	}
	

	@Override
	public String toString() {
		kurser = "";
		rates.keySet().forEach(key -> kurser += key + " = " + rates.get(key) + "\n");
		return kurser;
	}

	public static void main(String[] args) {
		Map<String, Double> test1 = new HashMap<>();
		Map<String, Double> test2 = new HashMap<>();
		CurrencyRelations a1 = new CurrencyRelations(1, test1);
		CurrencyRelations a2 = new CurrencyRelations(1, test2);
		a2.addRate("USD", 0.125);
		a1.addRate("SEK", 1.08);
		a1.addRate("GBP", 0.09);
		System.out.println(a1.getRates());
		System.out.println(a2.getRates());
		System.out.println(1/0.09);
		System.out.println(a1.convertCurrency("Britiske pund", "Svenske kroner"));
		
		
	}
}
