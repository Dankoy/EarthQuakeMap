package demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/*
 * Visualizes life expectancy in different countries. 
 * 
 * It loads the country shapes from a GeoJSON file via a data reader, and loads the population density values from
 * another CSV file.
 */

public class LifeExpectancy extends PApplet {

	private static final long serialVersionUID = 1L;

	private UnfoldingMap map;
	
	public Map<String, Float> lifeExpByCountry;
	public List<Feature> countries;
	public List<Marker> countryMarkers;

	public void setup() {
		
		size(800, 600, P2D);
		
		map = new UnfoldingMap(this, 50 ,50, 700, 500, new OpenStreetMap.OpenStreetMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// Load lifeExpectancy data
		lifeExpByCountry = loadLifeExpFromCSV("LifeExpectancyWorldBank.csv");
		
		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		
		// Country markers are shaded according to life expectancy
		shadeCountries();	
	}
	
	public void draw() {
		background(10);
		map.draw();
	}
	
	// Method to load life expectancy data from file
	private Map<String, Float> loadLifeExpFromCSV(String fileName) {
		
		Map<String, Float> lifeExp = new HashMap<String, Float>();
		String rows[] = loadStrings(fileName); 		// Reads country name and population density value from CSV row
		
		for(String row: rows) {
			String columns[] = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				float value = Float.parseFloat(columns[5]);
				lifeExp.put(columns[4], value);
			}
		}	
		return lifeExp;
	}
	
	// Method color each country based on life expectancy
	// Red-orange indicates low (near 40)
	// Blue indicates high (near 100)
	private void shadeCountries() {
	
		for (Marker marker: countryMarkers) {
			String countryID = marker.getId();
			
			if(lifeExpByCountry.containsKey(countryID)) {
				float lifeExp = lifeExpByCountry.get(countryID);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);		// Encode value as brightness (values range: 40-90)
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
	}
	
	public static void main(String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"EarthQuakeMap"});
	}

}
