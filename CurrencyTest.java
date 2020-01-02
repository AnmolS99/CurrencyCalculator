package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	
	Map<String, Double> rates = new HashMap<>();
	CurrencyRelations cr1 = new CurrencyRelations(1, rates);
	CurrencyCalculator cc1 = new CurrencyCalculator();
	CurrencyIO cIO = new CurrencyIO();
	
	@Before
	public void setUp() throws Exception {
		cr1.addRate("NOK", 1);
		cr1.addRate("SEK", 0.9);
		cr1.addRate("GBP", 10);
		
	}
	@Test
	public void testFilbehandlingRiktig() {
		cIO.skriveTilFil("test1", cr1.getRates());
		assertEquals(cr1.getRates(), cIO.lesingFraFil("test1"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddRateWithCharacters() {
		cr1.addRate("NOK12", 9.03);
		fail("Kursen kan ikke inneholde ulovlige tegn");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveNonExistingRate() {
		cr1.removeRate("EUR");
		fail("Kan ikke fjerne valuta som ikke er med i 'rates'");
	}
	
	@Test
	public void testCurrencyCalculator() {
		assertEquals(5, cc1.converter(50.0, "NOK", "GBP", cr1), 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConvertToAndFromSame() {
		cc1.converter(100.0, "SEK", "SEK", cr1);
		fail("Skal ikke være mulig å konvertere til samme valuta");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConvertNegativeAmount() {
		cc1.converter(-50.0, "NOK", "SEK", cr1);
		fail("Skal ikke kunne konvertere negative beløp");
	}
}
