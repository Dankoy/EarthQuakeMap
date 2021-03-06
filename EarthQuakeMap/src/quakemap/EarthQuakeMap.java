package quakemap;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

public class EarthQuakeMap extends PApplet {

	private static final long serialVersionUID = 1L;
	
	private static final boolean offline = false;
	
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	private UnfoldingMap map;
	
	// Feed of earthquakes happened last week with magnitude 1.0+
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_week.atom";
	
	public void setup() {
		
		// Colors for markers on map
		int yellow = color(255, 255, 0); 	
		int green = color(0, 255, 0);
		int red = color(255, 0, 0);
		int grey = color(200, 200, 200);
		
		size(950, 600, P2D);
		
		if (offline) {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
			
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new OpenStreetMap.OpenStreetMapProvider());
		}
		
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		List<Marker> markers = new ArrayList<Marker>();
		
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
				
		for(PointFeature eq: earthquakes) {
			SimplePointMarker mark = createMarker(eq);
			
			Object magObj = eq.getProperty("magnitude");
	        float mag = Float.parseFloat(magObj.toString());
			
			if( mag < 1f) {
				mark.setColor(grey);
				mark.setRadius(5);
			}
			else if( mag >= 1f && mag < 2.5f) {
				mark.setColor(green);
				mark.setRadius(8);
			}
			else if( mag >= 2.5f && mag < 4.5f) {
				mark.setColor(yellow);
				mark.setRadius(10);
			}
			else {
				mark.setColor(red);
				mark.setRadius(15);
			}			
			markers.add(mark);
		}
/*		
		for (Marker mk: markers) {
			if( (float) mk.getProperty("magnitude") < 1f) {
				mk.setColor(grey);
			}
			else if( (float) mk.getProperty("magnitude") >= 1f && (float) mk.getProperty("magnitude") < 2.5f) {
				mk.setColor(green);
			}
			else if( (float) mk.getProperty("magnitude") >= 2.5f && (float) mk.getProperty("magnitude") < 4.5f) {
				mk.setColor(yellow);
			}
			else {
				mk.setColor(red);
			}
		}
*/		
		map.addMarkers(markers);
		
	}
	
	private SimplePointMarker createMarker(PointFeature feature)
	{		
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
		background(10);
		map.draw();
		addKey();
	}
	
	private void addKey() {
		
		// Earthquakes legend
		fill(255);
		rect(10, 50, 180, 200, 7);
		
		fill(0);
		textSize(20);
		text("The Legend", 35, 75);
		
		textSize(15);
		text("Magnitude:", 15, 100);
		
		fill(255, 0, 0);
		ellipse(50, 125, 30, 30);
		fill(0);
		textSize(12);
		text("4.5+", 70, 130);
		
		fill(255, 255, 0);
		ellipse(50, 160, 25, 25);
		fill(0);
		textSize(12);
		text("2.5+", 70, 165);
		
		fill(0, 255, 0);
		ellipse(50, 190, 20, 20);
		fill(0);
		textSize(12);
		text("1+", 70, 195);
		
		fill(200, 200, 200);
		ellipse(50, 215, 15, 15);
		fill(0);
		textSize(12);
		text("Below 1", 70, 220);
		
		
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "EarthQuakeMap"});
	}
	
}
