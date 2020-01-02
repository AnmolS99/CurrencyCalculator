package app;

import java.util.Map;

public interface IOInterface {
	
	void skriveTilFil(String filnavn, Map<String, Double> map);
	
	Map<String, Double> lesingFraFil(String filnavn);
}
