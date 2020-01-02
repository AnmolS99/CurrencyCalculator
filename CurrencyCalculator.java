package app;

public class CurrencyCalculator {
	
	private int amountIn;
	private String fromCurrency;
	private String toCurrency;
	private double kurs;
	

	public int getAmountIn() {
		return amountIn;
	}

	public void setAmountIn(int amountIn) {
		this.amountIn = amountIn;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	
	public double converter(Double amountIn, String fromCurrency, String toCurrency, CurrencyRelations currencyRelations) {
		if (fromCurrency.equals(toCurrency)) {
			throw new IllegalArgumentException("'Fra valuta' og 'Til valuta' kan ikke være like");
		}
		if (amountIn < 0) {
			throw new IllegalArgumentException("Kan ikke konvertere et negativt beløp");
		}
		kurs = currencyRelations.convertCurrency(fromCurrency, toCurrency);
		return (amountIn*kurs);
	}
	
	
	
}
