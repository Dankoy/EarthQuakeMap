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
	
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	public void setup() {
		
		// Colors for markers on map
		int yellow = color(255, 255, 0); 	
		int green = color(0, 255, 0);
		int red = color(255, 0, 0);
		int grey = color(200, 200, 200);
		
		size(950, 600, P2D);
		
		if (offline) {
			map = new UnfoldingMap(this, 50, 50, 850, 500, new MBTilesMapProvider(mbTilesString));
			
		}
		else {
			map = new UnfoldingMap(this, 50, 50, 850, 500, new OpenStreetMap.OpenStreetMapProvider());
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
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "EarthQuakeMap"});
	}
	
}
