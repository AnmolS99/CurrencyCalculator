package app;


import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CurrencyController {
	
	@FXML
	ChoiceBox<String> FromCurrency;
	@FXML
	ChoiceBox<String> ToCurrency;
	@FXML
	TextField AmountIn;
	@FXML
	Label AmountOut;
	@FXML
	Button Caculate;
	@FXML
	Button GetRates;
	@FXML 
	TextField AddCurrencyName;
	@FXML 
	TextField AddCurrencyValue;
	@FXML
	Button AddCurrencyButton;
	@FXML
	TextField RemoveCurrencyName;
	@FXML
	Button RemoveCurrencyButton;
	@FXML
	Label ErrorMessage;
	@FXML
	Button LiveCurrencyRates;
	@FXML
	Button Reset;

	

	private Map<String, Double> r1 = new HashMap<>();		
	private CurrencyRelations cr1 = new CurrencyRelations(1, r1);	
	private	CurrencyCalculator cc1 = new CurrencyCalculator();
	private CurrencyIO cIO = new CurrencyIO();
	private InternetValutaIO iIO = new InternetValutaIO(); 
	private double result;
	
	
	@FXML
	public void initialize() {
		loadToApp();
	}
		
	public void handleCalculate() {
		try {
			result = cc1.converter(Double.parseDouble(AmountIn.getText()), FromCurrency.getValue(), ToCurrency.getValue(), cr1);
			AmountOut.setText(String.valueOf(result) + " " + ToCurrency.getValue() + "\n1 " + FromCurrency.getValue() + " = " +
			cc1.converter(1.0, FromCurrency.getValue(), ToCurrency.getValue(), cr1) + " " + ToCurrency.getValue());
			ErrorMessage.setText("");
		} catch (NumberFormatException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nSkriv inn et tall som kurs");
		} catch (NullPointerException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nDu må velge 'Fra valuta' og 'Til valuta'");
		} catch (IllegalArgumentException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nUgyldig 'Fra valuta' og/eller 'Til valuta'");
		}
		
	}
	
	public void handleAddCurrency() {
		try {
			cr1.addRate(AddCurrencyName.getText(), Double.parseDouble(AddCurrencyValue.getText()));
			AddCurrencyName.setText("");
			AddCurrencyValue.setText("");
			ErrorMessage.setText("");
			updateCurrencies();
			saveToFile();
			}
		catch (NumberFormatException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nSkriv inn et tall som beløp");
		}
		catch (IllegalArgumentException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nUgyldig valutanavn");
		}
		catch (StringIndexOutOfBoundsException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nUgyldig valutanavn");
		}
	}
	
	public void handleRemoveCurrency() {
		try {
			cr1.removeRate(RemoveCurrencyName.getText());
			RemoveCurrencyName.setText("");
			updateCurrencies();
			saveToFile();
		} catch (IllegalArgumentException e) {
			ErrorMessage.setText("Error: " + e.getMessage() + "\nKan ikke fjerne denne valutaen");
		}
	}
	
	public void handleGetRates() {
		AmountOut.setText(cr1.toString());
	}
	
	public void updateCurrencies() {
		FromCurrency.setItems(FXCollections.observableArrayList(r1.keySet()));
		ToCurrency.setItems(FXCollections.observableArrayList(r1.keySet()));
	}
	
	public void saveToFile() {
		cIO.skriveTilFil("valuta.txt", r1);
		
	}
	
	public void loadToApp() {
		r1 = cIO.lesingFraFil("valuta.txt");
		cr1 = new CurrencyRelations(1, r1);	
		cc1 = new CurrencyCalculator();
		updateCurrencies();
		AddCurrencyButton.setDisable(false);
		RemoveCurrencyButton.setDisable(false);
		AddCurrencyName.setDisable(false);
		AddCurrencyValue.setDisable(false);
		RemoveCurrencyName.setDisable(false);
	}
	
	public void loadToAppFromInternet() {
		r1 = iIO.lesingFraFil();
		cr1 = new CurrencyRelations(1, r1);
		cc1 = new CurrencyCalculator();
		updateCurrencies();
		AddCurrencyButton.setDisable(true);
		RemoveCurrencyButton.setDisable(true);
		AddCurrencyName.setDisable(true);
		AddCurrencyValue.setDisable(true);
		RemoveCurrencyName.setDisable(true);
	}
	
	
}
